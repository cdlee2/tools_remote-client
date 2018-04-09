package com.google.devtools.build.remote.client;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.escape.CharEscaperBuilder;
import com.google.common.escape.Escaper;
import com.google.devtools.remoteexecution.v1test.Action;
import com.google.devtools.remoteexecution.v1test.Command;
import com.google.devtools.remoteexecution.v1test.Command.EnvironmentVariable;
import com.google.devtools.remoteexecution.v1test.Platform;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class DockerUtils {
  private static final Joiner SPACE_JOINER = Joiner.on(' ');
  private static final Escaper STRONGQUOTE_ESCAPER =
      new CharEscaperBuilder().addEscape('\'', "'\\''").toEscaper();
  private static final CharMatcher SAFECHAR_MATCHER =
  private static final String CONTAINER_IMAGE_ENTRY_NAME = "container-image";
  private static final String DOCKER_IMAGE_PREFIX = "docker://";
  // Checks Action for docker container definition. If no docker container specified, returns
  // null. Otherwise returns docker container name from the parameters.
  private static @Nullable String dockerContainer(Action action) {
    String result = null;
    for (Platform.Property property : action.getPlatform().getPropertiesList()) {
      if (property.getName().equals(CONTAINER_IMAGE_ENTRY_NAME)) {
        if (result != null) {
          // Multiple container name entries
          throw new IllegalArgumentException(
              String.format(
                  "Multiple entries for %s in action.Platform", CONTAINER_IMAGE_ENTRY_NAME));
        }
        result = property.getValue();
        if (!result.startsWith(DOCKER_IMAGE_PREFIX)) {
          throw new IllegalArgumentException(
              String.format(
                  "%s: Docker images must be stored in gcr.io with an image spec in the form "
                      + "'docker://gcr.io/{IMAGE_NAME}'",
                  CONTAINER_IMAGE_ENTRY_NAME));
        }
        result = result.substring(DOCKER_IMAGE_PREFIX.length());
      }
    }
    return result;
  }

  public static String getDockerCommand(Action action, Command command, String workingPath) {
    String container = dockerContainer(action);
    if (container == null) {
      throw new IllegalArgumentException("No docker image specified in given Action.");
    }
    List<String> commandElements = new ArrayList<>();
    commandElements.add("docker");
    commandElements.add("run");

    String dockerPathString = workingPath + "-docker";
    commandElements.add("-v");
    commandElements.add(workingPath + ":" + dockerPathString);
    commandElements.add("-w");
    commandElements.add(dockerPathString);

    for (EnvironmentVariable var : command.getEnvironmentVariablesList()) {
      commandElements.add("-e");
      commandElements.add(var.getName() + "=" + var.getValue());
    }

    commandElements.add(container);
    commandElements.addAll(command.getArgumentsList());

    return getCommandLine(commandElements);
  }

  // Given an argument array, output the corresponding bash command to run the command with these
  // arguments.
  private static String getCommandLine(List<String> args) {
    List<String> escapedArgs =
        args.stream()
            .map(arg -> escapeBash(arg))
            .collect(Collectors.toList());
    return SPACE_JOINER.join(escapedArgs);
  }

  // Escape an argument so that it can passed as a single argument in bash command line. Unless the
  // argument contains no special characters, it will be wrapped in single quotes to escape special
  // behaviour.
  private static String escapeBash(String arg) {
    final String s = arg.toString();
    if (s.isEmpty()) {
      // Empty string is a special case: needs to be quoted to ensure that it
      // gets treated as a separate argument.
      return "''";
    } else {
      return SAFECHAR_MATCHER.matchesAllOf(s) ? s : "'" + STRONGQUOTE_ESCAPER.escape(s) + "'";
    }
  }
}
