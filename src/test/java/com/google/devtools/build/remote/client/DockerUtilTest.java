package com.google.devtools.build.remote.client;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.hash.Hashing;
import com.google.devtools.remoteexecution.v1test.Action;
import com.google.devtools.remoteexecution.v1test.Command;
import com.google.devtools.remoteexecution.v1test.Command.EnvironmentVariable;
import com.google.devtools.remoteexecution.v1test.Platform;
import com.google.devtools.remoteexecution.v1test.Platform.Property;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Tests for {@link DockerUtil}. */
@RunWith(JUnit4.class)
public class DockerUtilTest {
  private static final DigestUtil DIGEST_UTIL = new DigestUtil(Hashing.sha256());

  @Test
  public void testGetDockerCommand() {
    Command command =
        Command.newBuilder()
            .addArguments("/bin/echo")
            .addArguments("hello")
            .addArguments("escape<'>")
            .addEnvironmentVariables(
                EnvironmentVariable.newBuilder().setName("PATH").setValue("/home/test"))
            .build();
    Action action =
        Action.newBuilder()
            .setCommandDigest(DIGEST_UTIL.compute(command.toByteArray()))
            .setPlatform(
                Platform.newBuilder()
                    .addProperties(
                        Property.newBuilder()
                            .setName("container-image")
                            .setValue("docker://gcr.io/image")))
            .build();
    String commandLine = DockerUtil.getDockerCommand(action, command, "/tmp/test");
    assertThat(commandLine)
        .isEqualTo(
            "docker run -v /tmp/test:/tmp/test-docker -w /tmp/test-docker -e 'PATH=/home/test' "
                + "gcr.io/image /bin/echo hello 'escape<'\\''>'");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetDockerCommandNoPlatformFail() {
    Command command =
        Command.newBuilder()
            .addArguments("/bin/echo")
            .addArguments("hello")
            .addArguments("escape<'>")
            .addEnvironmentVariables(
                EnvironmentVariable.newBuilder().setName("PATH").setValue("/home/test"))
            .build();
    Action action =
        Action.newBuilder().setCommandDigest(DIGEST_UTIL.compute(command.toByteArray())).build();
    DockerUtil.getDockerCommand(action, command, "/tmp/test");
  }
}
