licenses(["notice"])  # Apache 2.0

java_library(
    name = "client",
    srcs = glob(["*.java"]),
    visibility = ["//:__subpackages__"],
    runtime_deps = [
        # Needed for tls client.
        "@google_http_client_maven//jar",
        "@google_http_client_jackson2_maven//jar",
        "@jackson2_maven//jar",
        "@netty_tcnative_maven//jar",
    ],
    deps = [
        "//external:google_auth_creds",
        "//external:google_auth_oauth2",
        "//external:guava",
        "//external:jcommander",
        "@com_google_code_findbugs_jsr305//jar",
        "@com_google_protobuf//:protobuf_java",
        "@googleapis//:google_bytestream_bytestream_java_grpc",
        "@googleapis//:google_bytestream_bytestream_java_proto",
        "@googleapis//:google_devtools_remoteexecution_v1test_remote_execution_java_grpc",
        "@googleapis//:google_devtools_remoteexecution_v1test_remote_execution_java_proto",
        "@grpc_java//auth:auth",
        "@grpc_java//context:context",
        "@grpc_java//core:core",
        "@grpc_java//core:util",
        "@grpc_java//grpclb:grpclb",
        "@grpc_java//netty:netty",
        "@grpc_java//protobuf:protobuf",
        "@grpc_java//stub:stub",
        "@io_netty_netty_handler//jar",
    ],
)
