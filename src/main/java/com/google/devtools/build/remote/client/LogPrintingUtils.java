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

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.devtools.build.lib.remote.logging.RemoteExecutionLog.LogEntry;
import com.google.devtools.build.lib.remote.logging.RemoteExecutionLog.RpcCallDetails.DetailsCase;
import com.google.devtools.build.lib.remote.logging.RemoteExecutionLog.WatchDetails;
import com.google.devtools.build.remote.client.RemoteClientOptions.PrintLogCommand;
import com.google.devtools.remoteexecution.v1test.ExecuteResponse;
import com.google.longrunning.Operation;
import com.google.longrunning.Operation.ResultCase;
import com.google.watcher.v1.Change;
import com.google.watcher.v1.Change.State;
import com.google.watcher.v1.ChangeBatch;
import io.grpc.Status.Code;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

/** Methods for printing log files. */
public class LogPrintingUtils {
  private static final String DELIMETER =
      "---------------------------------------------------------\n";

  /**
   * Attempt to find and print ExecuteResponse from the details of a log entry for a Watch call. If
   * no Operation could be found in the Watch call responses, or an Operation was found but failed,
   * a failure message is printed.
   */
  private static void printExecuteResponse(WatchDetails watch) throws IOException {
    for (ChangeBatch cb : watch.getResponsesList()) {
      for (Change ch : cb.getChangesList()) {
        if (ch.getState() != State.EXISTS) {
          continue;
        }
        Operation o = ch.getData().unpack(Operation.class);
        if (o.getResultCase() == ResultCase.ERROR && o.getError().getCode() != Code.OK.value()) {
          System.out.printf("Error status in ChangeBatches: %s\n", o.getError().toString());
          return;
        } else if (o.getResultCase() == ResultCase.RESPONSE && o.getDone()) {
          System.out.println("ExecuteResponse extracted:");
          System.out.println(o.getResponse().unpack(ExecuteResponse.class).toString());
          return;
        }
      }
    }
    System.out.println("Could not find ExecuteResponse in Watch call details.");
  }

  /** Print an individual log entry. */
  private static void printLogEntry(LogEntry entry) throws IOException {
    System.out.println(entry.toString());
    if (entry.getDetails().getDetailsCase() == DetailsCase.WATCH) {
      System.out.println("\nAttempted to extract ExecuteResponse from Watch call responses:");
      printExecuteResponse(entry.getDetails().getWatch());
    }
  }

  /**
   * Returns true only if the log entry should printed according to the printlog command options.
   */
  private static boolean filterLogEntry(LogEntry entry, PrintLogCommand options) {
    if (options.failuresOnly && entry.getStatus().getCode() == Code.OK.value()) {
      return false;
    }
    if (!options.actionId.isEmpty()
        && (!entry.hasMetadata() || !entry.getMetadata().getActionId().equals(options.actionId))) {
      return false;
    }
    if (!options.method.isEmpty()) {
      Pattern pattern = Pattern.compile(options.method, Pattern.CASE_INSENSITIVE);
      if (!pattern.matcher(entry.getMethodName()).find()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Prints each entry out individually (ungrouped) and a message at the end for how many entries
   * were printed/skipped.
   */
  private static void printEntriesInOrder(LogStats stats, PrintLogCommand options)
      throws IOException {
    int num_printed = 0;
    int num_total = 0;
    try (InputStream in = new FileInputStream(options.file)) {
      LogEntry entry;
      while ((entry = LogEntry.parseDelimitedFrom(in)) != null) {
        num_total++;
        if (!filterLogEntry(entry, options)) {
          continue;
        }
        num_printed++;
        stats.record(entry);
        printLogEntry(entry);
        System.out.print(DELIMETER);
      }
    }
    System.out.printf("Matched %d out of %d log entries.\n", num_printed, num_total);
  }

  /**
   * Prints each entry in groups by action, and a message at the end for how many entries were
   * printed/skipped. If an entry does not have metadata to identify the action, is it skipped.
   *
   * <p>Groups of entries for actions are printed ordered by the position of the first log entry for
   * that action in the log. Entries for a specific action are printed in the order they appear in
   * the log.
   */
  private static void printEntriesGroupedByAction(LogStats stats, PrintLogCommand options)
      throws IOException {
    int num_printed = 0;
    int num_total = 0;
    Multimap<String, LogEntry> actionMap = LinkedListMultimap.create();
    try (InputStream in = new FileInputStream(options.file)) {
      LogEntry entry;
      while ((entry = LogEntry.parseDelimitedFrom(in)) != null) {
        num_total++;
        if (!filterLogEntry(entry, options) || !entry.hasMetadata()) {
          continue;
        }
        num_printed++;
        actionMap.put(entry.getMetadata().getActionId(), entry);
      }
    }
    for (String hash : actionMap.keySet()) {
      System.out.printf("Entries for action with hash '%s'\n", hash);
      System.out.print(DELIMETER);
      for (LogEntry entry : actionMap.get(hash)) {
        printLogEntry(entry);
        stats.record(entry);
        System.out.print(DELIMETER);
      }
    }
    System.out.printf("Printed %d out of %d log entries.\n", num_printed, num_total);
  }

  /** Print log entries to standard output according to the command line arguments given. */
  public static void printLog(PrintLogCommand options) throws IOException {
    LogStats stats = new LogStats();
    if (options.groupByAction) {
      printEntriesGroupedByAction(stats, options);
    } else {
      printEntriesInOrder(stats, options);
    }
    System.out.print(DELIMETER);
    System.out.println("Summary of printed entries:");
    stats.printSummary();
  }

  /** Print only stat summary for the log. */
  public static void printStatsOnly(File file) throws IOException {
    LogStats stats = new LogStats();
    try (InputStream in = new FileInputStream(file)) {
      LogEntry entry;
      while ((entry = LogEntry.parseDelimitedFrom(in)) != null) {
        stats.record(entry);
      }
    }
    System.out.println("Summary of entries:");
    stats.printSummary();
  }
}
