maven_jar(
    name = "guava_maven",
    artifact = "com.google.guava:guava:19.0",
)

bind(
    name = "guava",
    actual = "@guava_maven//jar",
)

maven_jar(
    name = "jcommander_maven",
    artifact = "com.beust:jcommander:1.72",
)

bind(
    name = "jcommander",
    actual = "@jcommander_maven//jar",
)

maven_jar(
    name = "netty_maven",
    artifact = "io.netty:netty:3.10.6.Final",
)

bind(
    name = "netty",
    actual = "@netty_maven//jar",
)

maven_jar(
    name = "google_auth_oauth2_maven",
    artifact = "com.google.auth:google-auth-library-oauth2-http:0.9.0"
)

bind(
    name = "google_auth_oauth2",
    actual = "@google_auth_oauth2_maven//jar",
)

maven_jar(
    name = "google_auth_creds_maven",
    artifact = "com.google.auth:google-auth-library-credentials:0.9.0",
)

bind(
    name = "google_auth_creds",
    actual = "@google_auth_creds_maven//jar",
)

maven_jar(
    name = "google_http_client_maven",
    artifact = "com.google.http-client:google-http-client:1.23.0",
)

maven_jar(
    name = "google_http_client_jackson2_maven",
    artifact = "com.google.http-client:google-http-client-jackson2:1.23.0",
)

maven_jar(
    name = "jackson2_maven",
    artifact = "com.fasterxml.jackson.core:jackson-core:2.9.4",
)

maven_jar(
    name = "netty_tcnative_maven",
    artifact = "io.netty:netty-tcnative-boringssl-static:2.0.7.Final",
)

http_archive(
    name = "com_google_protobuf",
    sha256 = "091d4263d9a55eccb6d3c8abde55c26eaaa933dea9ecabb185cdf3795f9b5ca2",
    strip_prefix = "protobuf-3.5.1.1",
    urls = ["https://github.com/google/protobuf/archive/v3.5.1.1.zip"],
)

# This commit of grpc-java is used instead of the latest released version since the latest released
# version (1.9.0) has a few problems with being used as an external bazel workspace.
http_archive(
    name = "grpc_java",
    sha256 = "000a6f8579f1b93e5d1b085c29d89dbc1ea8b5a0c16d7427f42715f0d7f0b247",
    strip_prefix = "grpc-java-d792a72ea15156254e3b3735668e9c4539837fd3",
    urls = ["https://github.com/grpc/grpc-java/archive/d792a72ea15156254e3b3735668e9c4539837fd3.zip"],
)

new_http_archive(
    name = "googleapis",
    sha256 = "7b6ea252f0b8fb5cd722f45feb83e115b689909bbb6a393a873b6cbad4ceae1d",
    url = "https://github.com/googleapis/googleapis/archive/143084a2624b6591ee1f9d23e7f5241856642f4d.zip",
    strip_prefix = "googleapis-143084a2624b6591ee1f9d23e7f5241856642f4d",
    build_file = "BUILD.googleapis",
)

load("@grpc_java//:repositories.bzl", "grpc_java_repositories")

grpc_java_repositories(omit_com_google_protobuf=True)
