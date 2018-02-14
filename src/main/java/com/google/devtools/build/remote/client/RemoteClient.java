// Copyright 2018 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.devtools.build.remote.client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.google.common.hash.Hashing;
import com.google.devtools.remoteexecution.v1test.Digest;
import com.google.devtools.remoteexecution.v1test.Directory;
import com.google.devtools.remoteexecution.v1test.DirectoryNode;
import com.google.devtools.remoteexecution.v1test.FileNode;
import com.google.devtools.remoteexecution.v1test.RequestMetadata;
import com.google.devtools.remoteexecution.v1test.ToolDetails;
import com.google.devtools.remoteexecution.v1test.Tree;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/** A standalone client for interacting with remote caches in Bazel. */
public class RemoteClient {

  AbstractRemoteActionCache cache;
  DigestUtil digestUtil;

  private RemoteClient(AbstractRemoteActionCache cache) {
    this.cache = cache;
    this.digestUtil = cache.getDigestUtil();
  }

  // Prints the details (path and digest) of a DirectoryNode.
  private void printDirectoryNodeDetails(DirectoryNode directoryNode, Path directoryPath) {
    Digest digest = directoryNode.getDigest();
    System.out.printf(
        "%s [Directory digest: %s/%d]\n",
        directoryPath.toString(), digest.getHash(), digest.getSizeBytes());
  }

  // Prints the details (path and content digest) of a FileNode.
  private void printFileNodeDetails(FileNode fileNode, Path filePath) {
    Digest digest = fileNode.getDigest();
    System.out.printf(
        "%s [File content digest: %s/%d]\n",
        filePath.toString(), digest.getHash(), digest.getSizeBytes());
  }

  // List the files in a directory assuming the directory is at the given path. Returns the number
  // of files listed.
  private int listFileNodes(Path path, Directory dir, int limit) {
    int numFilesListed = 0;
    for (FileNode child : dir.getFilesList()) {
      if (numFilesListed >= limit) {
        System.out.println(" ... (too many files to list, some omitted)");
        break;
      }
      Path childPath = path.resolve(child.getName());
      printFileNodeDetails(child, childPath);
      numFilesListed++;
    }
    return numFilesListed;
  }

  // Recursively list directory files/subdirectories with digests. Returns the number of files
  // listed.
  private int listDirectory(Path path, Directory dir, Map<Digest, Directory> childrenMap, int limit)
      throws IOException, InterruptedException {
    // Try to list the files in this directory before listing the directories.
    int numFilesListed = listFileNodes(path, dir, limit);
    if (numFilesListed >= limit) {
      return numFilesListed;
    }
    for (DirectoryNode child : dir.getDirectoriesList()) {
      Path childPath = path.resolve(child.getName());
      printDirectoryNodeDetails(child, childPath);
      Digest childDigest = child.getDigest();
      Directory childDir = childrenMap.get(childDigest);
      numFilesListed += listDirectory(childPath, childDir, childrenMap, limit - numFilesListed);
      if (numFilesListed >= limit) {
        return numFilesListed;
      }
    }
    return numFilesListed;
  }

  // Recursively list directory files/subdirectories with digests given a Tree of the directory.
  private void listTree(Path path, Tree tree, int limit) throws IOException, InterruptedException {
    Map<Digest, Directory> childrenMap = new HashMap<>();
    for (Directory child : tree.getChildrenList()) {
      childrenMap.put(digestUtil.compute(child), child);
    }
    listDirectory(path, tree.getRoot(), childrenMap, limit);
  }

  public static void main(String[] args) throws Exception {
    AuthAndTLSOptions authAndTlsOptions = new AuthAndTLSOptions();
    RemoteOptions remoteOptions = new RemoteOptions();
    RemoteClientOptions remoteClientOptions = new RemoteClientOptions();

    JCommander optionsParser =
        JCommander.newBuilder()
            .addObject(authAndTlsOptions)
            .addObject(remoteOptions)
            .addObject(remoteClientOptions)
            .build();

    try {
      optionsParser.parse(args);
    } catch (ParameterException e) {
      System.err.println("Unable to parse options: " + e.getLocalizedMessage());
      optionsParser.usage();
      System.exit(1);
    }

    try {
      validateClientOptions(remoteClientOptions);
    } catch (IllegalArgumentException e) {
      System.err.println(e.getLocalizedMessage());
      optionsParser.usage();
      System.exit(1);
    }

    if (remoteClientOptions.help) {
      optionsParser.usage();
      return;
    }

    DigestUtil digestUtil = new DigestUtil(Hashing.sha256());
    AbstractRemoteActionCache cache;

    if (GrpcRemoteCache.isRemoteCacheOptions(remoteOptions)) {
      cache = new GrpcRemoteCache(remoteOptions, authAndTlsOptions, digestUtil);
      RequestMetadata metadata =
          RequestMetadata.newBuilder()
              .setToolDetails(ToolDetails.newBuilder().setToolName("remote_client"))
              .build();
      TracingMetadataUtils.contextWithMetadata(metadata).attach();
    } else {
      throw new UnsupportedOperationException("Only gRPC remote cache supported currently.");
    }

    RemoteClient client = new RemoteClient(cache);

    if (remoteClientOptions.listDirectory != null) {
      Tree tree = cache.getTree(remoteClientOptions.listDirectory);
      client.listTree(Paths.get(""), tree, remoteClientOptions.listLimit);
      return;
    }

    if (remoteClientOptions.downloadDirectoryDigest != null) {
      cache.downloadDirectory(
          remoteClientOptions.downloadDirectoryPath, remoteClientOptions.downloadDirectoryDigest);
      return;
    }

    if (remoteClientOptions.digest != null) {
      OutputStream output;
      if (remoteClientOptions.output != null) {
        File file = new File(remoteClientOptions.output);
        output = new FileOutputStream(file);

        if (!file.exists()) {
          file.createNewFile();
        }
      } else {
        output = System.out;
      }

      try {
        cache.downloadBlob(remoteClientOptions.digest, output);
      } catch (CacheNotFoundException e) {
        System.err.println("Error: " + e);
      } finally {
        output.close();
      }
      return;
    }
  }

  private static void validateClientOptions(RemoteClientOptions options)
      throws IllegalArgumentException {
    int numOperations =
        boolToInt(options.digest != null)
            + boolToInt(options.listDirectory != null)
            + boolToInt(options.downloadDirectoryDigest != null)
            + boolToInt(options.help);
    if (numOperations == 0) {
      throw new IllegalArgumentException(
          "An operation for the client to perform must be specified.");
    } else if (numOperations > 1) {
      throw new IllegalArgumentException(
          "Only one operation can be specified to be performed by the client.");
    }
  }

  private static int boolToInt(boolean bool) {
    return bool ? 1 : 0;
  }
}
