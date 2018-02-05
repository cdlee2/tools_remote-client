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

import com.google.devtools.remoteexecution.v1test.Digest;
import java.io.IOException;
import java.io.OutputStream;

/** A cache for storing artifacts (input and output) as well as the output of running an action. */
public abstract class AbstractRemoteActionCache {
  protected final DigestUtil digestUtil;

  public AbstractRemoteActionCache(DigestUtil digestUtil) {
    this.digestUtil = digestUtil;
  }

  /** Downloads a single blob with the specified digest into an output stream. */
  public abstract void downloadBlob(Digest digest, OutputStream dest) throws IOException;
}
