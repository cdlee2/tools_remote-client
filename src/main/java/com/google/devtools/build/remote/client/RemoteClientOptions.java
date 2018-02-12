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

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.PathConverter;
import com.google.devtools.remoteexecution.v1test.Digest;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Options for operation of a remote client. */
@Parameters(separators = "=")
public final class RemoteClientOptions {
  @Parameter(names = "--help", description = "This message.", help = true)
  public boolean help;

  @Parameter(
    names = "--digest",
    converter = DigestConverter.class,
    description = "A blob digest to download in the format hex_hash/size_bytes."
  )
  public Digest digest = null;

  @Parameter(
    names = "--output",
    description =
        "If specified, a path to download the blob into. "
            + "Otherwise, contents will be printed to stdout."
  )
  public String output = null;

  @Parameter(
    names = "--list_directory",
    converter = DigestConverter.class,
    description =
        "A directory digest in the format hex_hash/size_bytes. The directory file contents "
            + "will be listed recursively."
  )
  public Digest listDirectory = null;

  @Parameter(
    names = "--list_limit",
    description =
        "A directory digest in the format hex_hash/size_bytes. The directory file contents "
            + "will be listed recursively."
  )
  public int listLimit = 100;

  @Parameter(
    names = "--download_directory_digest",
    converter = DigestConverter.class,
    description =
        "A directory digest in the format hex_hash/size_bytes. The directory file contents "
            + "will be downloaded recursively."
  )
  public Digest downloadDirectoryDigest = null;

  @Parameter(
    names = "--download_directory_path",
    converter = PathConverter.class,
    description =
        "Specifies what directory to download the directory contents to. By default, the current "
            + "path will be used."
  )
  public Path downloadDirectoryPath = Paths.get("");

  /** Converter for hex_hash/size_bytes string to a Digest object. */
  public static class DigestConverter implements IStringConverter<Digest> {
    @Override
    public Digest convert(String input) {
      int slash = input.indexOf('/');
      if (slash < 0) {
        throw new ParameterException("'" + input + "' is not as hex_hash/size_bytes");
      }
      try {
        long size = Long.parseLong(input.substring(slash + 1));
        return DigestUtil.buildDigest(input.substring(0, slash), size);
      } catch (NumberFormatException e) {
        throw new ParameterException("'" + input + "' is not a hex_hash/size_bytes: " + e);
      }
    }
  }
}
