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

import com.google.devtools.build.lib.remote.logging.RemoteExecutionLog.LogEntry;
import com.google.devtools.remoteexecution.v1test.ActionCacheGrpc;
import com.google.devtools.remoteexecution.v1test.ExecutionGrpc;
import com.google.rpc.Status;
import io.grpc.Status.Code;
import java.util.HashMap;

/** Accumulates summary stats for log entries. */
public class LogStats {
  enum ActionMode {
    UNKNOWN,
    CACHED,
    EXECUTED
  }

  private int num_calls;
  private int num_failed_calls;
  private final HashMap<String, ActionMode> actionModes = new HashMap<>();

  private static boolean isGetActionResultCall(LogEntry entry) {
    return entry
        .getMethodName()
        .equals(ActionCacheGrpc.getGetActionResultMethod().getFullMethodName());
  }

  private static boolean isExecuteCall(LogEntry entry) {
    return entry.getMethodName().equals(ExecutionGrpc.getExecuteMethod().getFullMethodName());
  }

  private static boolean matchCode(Status status, Code code) {
    return status.getCode() == code.value();
  }

  private static boolean isFailedCall(LogEntry entry) {
    if (isGetActionResultCall(entry)) {
      // Do not count cache misses as failures.
      return !matchCode(entry.getStatus(), Code.OK)
          && !matchCode(entry.getStatus(), Code.NOT_FOUND);
    }
    return !matchCode(entry.getStatus(), Code.OK);
  }

  /** Accumulates stats for the given entry. */
  public void record(LogEntry entry) {
    num_calls++;
    if (isFailedCall(entry)) {
      num_failed_calls++;
    }
    if (!entry.hasMetadata()) {
      return;
    }
    String hash = entry.getMetadata().getActionId();
    if (isExecuteCall(entry)) {
      actionModes.put(hash, ActionMode.EXECUTED);
    } else if (isGetActionResultCall(entry) && matchCode(entry.getStatus(), Code.OK)) {
      actionModes.put(hash, ActionMode.CACHED);
    } else if (!actionModes.containsKey(hash)) {
      actionModes.put(hash, ActionMode.UNKNOWN);
    }
  }

  /** Counts the number of actions accumulated with the given mode of execution. */
  private long countWithMode(ActionMode countedMode) {
    return actionModes.values().stream().filter(mode -> mode == countedMode).count();
  }

  /** Prints a summary of recorded log entries to standard output. */
  public void printSummary() {
    System.out.printf("%d of %d calls failed.\n", num_failed_calls, num_calls);
    System.out.printf(
        "Counted %d remote action(s) total (%d cached, %d executed, %d unknown).\n",
        actionModes.size(),
        countWithMode(ActionMode.CACHED),
        countWithMode(ActionMode.EXECUTED),
        countWithMode(ActionMode.UNKNOWN));
  }
}
