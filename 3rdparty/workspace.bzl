def declare_maven(hash):
    native.maven_jar(
        name = hash["name"],
        artifact = hash["artifact"],
        sha1 = hash["sha1"],
        repository = hash["repository"]
    )
    native.bind(
        name = hash["bind"],
        actual = hash["actual"]
    )

def maven_dependencies(callback = declare_maven):
    callback({"artifact": "com.beust:jcommander:1.72", "lang": "java", "sha1": "6375e521c1e11d6563d4f25a07ce124ccf8cd171", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_beust_jcommander", "actual": "@com_beust_jcommander//jar", "bind": "jar/com/beust/jcommander"})
    callback({"artifact": "com.fasterxml.jackson.core:jackson-core:2.1.3", "lang": "java", "sha1": "f6c3aed1cdfa21b5c1737c915186ea93a95a58bd", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_fasterxml_jackson_core_jackson_core", "actual": "@com_fasterxml_jackson_core_jackson_core//jar", "bind": "jar/com/fasterxml/jackson/core/jackson_core"})
    callback({"artifact": "com.google.api.grpc:proto-google-common-protos:1.0.0", "lang": "java", "sha1": "86f070507e28b930e50d218ee5b6788ef0dd05e6", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_google_api_grpc_proto_google_common_protos", "actual": "@com_google_api_grpc_proto_google_common_protos//jar", "bind": "jar/com/google/api/grpc/proto_google_common_protos"})
    callback({"artifact": "com.google.auth:google-auth-library-credentials:0.9.0", "lang": "java", "sha1": "8e2b181feff6005c9cbc6f5c1c1e2d3ec9138d46", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_google_auth_google_auth_library_credentials", "actual": "@com_google_auth_google_auth_library_credentials//jar", "bind": "jar/com/google/auth/google_auth_library_credentials"})
    callback({"artifact": "com.google.auth:google-auth-library-oauth2-http:0.9.0", "lang": "java", "sha1": "04e6152c3aead24148627e84f5651e79698c00d9", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_google_auth_google_auth_library_oauth2_http", "actual": "@com_google_auth_google_auth_library_oauth2_http//jar", "bind": "jar/com/google/auth/google_auth_library_oauth2_http"})
# duplicates in com.google.code.findbugs:jsr305 promoted to 3.0.0
# - com.google.http-client:google-http-client:1.23.0 wanted version 1.3.9
# - com.google.guava:guava:23.6-jre wanted version 1.3.9
# - io.grpc:grpc-core:1.9.0 wanted version 3.0.0
    callback({"artifact": "com.google.code.findbugs:jsr305:3.0.0", "lang": "java", "sha1": "5871fb60dc68d67da54a663c3fd636a10a532948", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_google_code_findbugs_jsr305", "actual": "@com_google_code_findbugs_jsr305//jar", "bind": "jar/com/google/code/findbugs/jsr305"})
    callback({"artifact": "com.google.code.gson:gson:2.7", "lang": "java", "sha1": "751f548c85fa49f330cecbb1875893f971b33c4e", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_google_code_gson_gson", "actual": "@com_google_code_gson_gson//jar", "bind": "jar/com/google/code/gson/gson"})
# duplicates in com.google.errorprone:error_prone_annotations promoted to 2.1.3
# - io.grpc:grpc-core:1.9.0 wanted version 2.1.2
# - com.google.guava:guava:23.6-jre wanted version 2.1.3
# - com.google.truth:truth:0.39 wanted version 2.1.3
    callback({"artifact": "com.google.errorprone:error_prone_annotations:2.1.3", "lang": "java", "sha1": "39b109f2cd352b2d71b52a3b5a1a9850e1dc304b", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_google_errorprone_error_prone_annotations", "actual": "@com_google_errorprone_error_prone_annotations//jar", "bind": "jar/com/google/errorprone/error_prone_annotations"})
# duplicates in com.google.guava:guava fixed to 23.6-jre
# - com.google.truth:truth:0.39 wanted version 23.4-android
# - com.google.jimfs:jimfs:1.1 wanted version 18.0
# - io.grpc:grpc-core:1.9.0 wanted version 19.0
# - com.google.auth:google-auth-library-oauth2-http:0.9.0 wanted version 19.0
    callback({"artifact": "com.google.guava:guava:23.6-jre", "lang": "java", "sha1": "c0b638df79e7b2e1ed98f8d68ac62538a715ab1d", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_google_guava_guava", "actual": "@com_google_guava_guava//jar", "bind": "jar/com/google/guava/guava"})
    callback({"artifact": "com.google.http-client:google-http-client-jackson2:1.19.0", "lang": "java", "sha1": "81dbf9795d387d5e80e55346582d5f2fb81a42eb", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_google_http_client_google_http_client_jackson2", "actual": "@com_google_http_client_google_http_client_jackson2//jar", "bind": "jar/com/google/http_client/google_http_client_jackson2"})
    callback({"artifact": "com.google.http-client:google-http-client:1.23.0", "lang": "java", "sha1": "8e86c84ff3c98eca6423e97780325b299133d858", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_google_http_client_google_http_client", "actual": "@com_google_http_client_google_http_client//jar", "bind": "jar/com/google/http_client/google_http_client"})
    callback({"artifact": "com.google.instrumentation:instrumentation-api:0.4.3", "lang": "java", "sha1": "41614af3429573dc02645d541638929d877945a2", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_google_instrumentation_instrumentation_api", "actual": "@com_google_instrumentation_instrumentation_api//jar", "bind": "jar/com/google/instrumentation/instrumentation_api"})
    callback({"artifact": "com.google.j2objc:j2objc-annotations:1.1", "lang": "java", "sha1": "ed28ded51a8b1c6b112568def5f4b455e6809019", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_google_j2objc_j2objc_annotations", "actual": "@com_google_j2objc_j2objc_annotations//jar", "bind": "jar/com/google/j2objc/j2objc_annotations"})
    callback({"artifact": "com.google.jimfs:jimfs:1.1", "lang": "java", "sha1": "8fbd0579dc68aba6186935cc1bee21d2f3e7ec1c", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_google_jimfs_jimfs", "actual": "@com_google_jimfs_jimfs//jar", "bind": "jar/com/google/jimfs/jimfs"})
    callback({"artifact": "com.google.protobuf.nano:protobuf-javanano:3.0.0-alpha-5", "lang": "java", "sha1": "357e60f95cebb87c72151e49ba1f570d899734f8", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_google_protobuf_nano_protobuf_javanano", "actual": "@com_google_protobuf_nano_protobuf_javanano//jar", "bind": "jar/com/google/protobuf/nano/protobuf_javanano"})
    callback({"artifact": "com.google.protobuf:protobuf-java-util:3.5.1", "lang": "java", "sha1": "6e40a6a3f52455bd633aa2a0dba1a416e62b4575", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_google_protobuf_protobuf_java_util", "actual": "@com_google_protobuf_protobuf_java_util//jar", "bind": "jar/com/google/protobuf/protobuf_java_util"})
    callback({"artifact": "com.google.protobuf:protobuf-java:3.5.1", "lang": "java", "sha1": "8c3492f7662fa1cbf8ca76a0f5eb1146f7725acd", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_google_protobuf_protobuf_java", "actual": "@com_google_protobuf_protobuf_java//jar", "bind": "jar/com/google/protobuf/protobuf_java"})
    callback({"artifact": "com.google.truth:truth:0.39", "lang": "java", "sha1": "bd1bf5706ff34eb7ff80fef8b0c4320f112ef899", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_google_truth_truth", "actual": "@com_google_truth_truth//jar", "bind": "jar/com/google/truth/truth"})
    callback({"artifact": "com.squareup.okhttp:okhttp:2.5.0", "lang": "java", "sha1": "4de2b4ed3445c37ec1720a7d214712e845a24636", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_squareup_okhttp_okhttp", "actual": "@com_squareup_okhttp_okhttp//jar", "bind": "jar/com/squareup/okhttp/okhttp"})
    callback({"artifact": "com.squareup.okio:okio:1.13.0", "lang": "java", "sha1": "a9283170b7305c8d92d25aff02a6ab7e45d06cbe", "repository": "https://repo.maven.apache.org/maven2/", "name": "com_squareup_okio_okio", "actual": "@com_squareup_okio_okio//jar", "bind": "jar/com/squareup/okio/okio"})
# duplicates in commons-codec:commons-codec promoted to 1.6
# - org.apache.httpcomponents:httpclient:4.0.1 wanted version 1.6
# - org.apache.httpcomponents:httpclient:4.0.1 wanted version 1.3
    callback({"artifact": "commons-codec:commons-codec:1.6", "lang": "java", "sha1": "b7f0fc8f61ecadeb3695f0b9464755eee44374d4", "repository": "https://repo.maven.apache.org/maven2/", "name": "commons_codec_commons_codec", "actual": "@commons_codec_commons_codec//jar", "bind": "jar/commons_codec/commons_codec"})
    callback({"artifact": "commons-logging:commons-logging:1.1.1", "lang": "java", "sha1": "5043bfebc3db072ed80fbd362e7caf00e885d8ae", "repository": "https://repo.maven.apache.org/maven2/", "name": "commons_logging_commons_logging", "actual": "@commons_logging_commons_logging//jar", "bind": "jar/commons_logging/commons_logging"})
    callback({"artifact": "io.grpc:grpc-all:1.9.0", "lang": "java", "sha1": "442dfac27fd072e15b7134ab02c2b38136036090", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_grpc_grpc_all", "actual": "@io_grpc_grpc_all//jar", "bind": "jar/io/grpc/grpc_all"})
    callback({"artifact": "io.grpc:grpc-auth:1.9.0", "lang": "java", "sha1": "d2eadc6d28ebee8ec0cef74f882255e4069972ad", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_grpc_grpc_auth", "actual": "@io_grpc_grpc_auth//jar", "bind": "jar/io/grpc/grpc_auth"})
    callback({"artifact": "io.grpc:grpc-context:1.9.0", "lang": "java", "sha1": "28b0836f48c9705abf73829bbc536dba29a1329a", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_grpc_grpc_context", "actual": "@io_grpc_grpc_context//jar", "bind": "jar/io/grpc/grpc_context"})
    callback({"artifact": "io.grpc:grpc-core:1.9.0", "lang": "java", "sha1": "cf76ab13d35e8bd5d0ffad6d82bb1ef1770f050c", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_grpc_grpc_core", "actual": "@io_grpc_grpc_core//jar", "bind": "jar/io/grpc/grpc_core"})
    callback({"artifact": "io.grpc:grpc-netty:1.9.0", "lang": "java", "sha1": "8157384d87497dc18604a5ba3760763fe643f16e", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_grpc_grpc_netty", "actual": "@io_grpc_grpc_netty//jar", "bind": "jar/io/grpc/grpc_netty"})
    callback({"artifact": "io.grpc:grpc-okhttp:1.9.0", "lang": "java", "sha1": "4e7fbb9d3cd65848f42494de165b1c5839f69a8a", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_grpc_grpc_okhttp", "actual": "@io_grpc_grpc_okhttp//jar", "bind": "jar/io/grpc/grpc_okhttp"})
    callback({"artifact": "io.grpc:grpc-protobuf-lite:1.9.0", "lang": "java", "sha1": "9dc9c6531ae0b304581adff0e9b7cff21a4073ac", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_grpc_grpc_protobuf_lite", "actual": "@io_grpc_grpc_protobuf_lite//jar", "bind": "jar/io/grpc/grpc_protobuf_lite"})
    callback({"artifact": "io.grpc:grpc-protobuf-nano:1.9.0", "lang": "java", "sha1": "561b03d3fd5178117a51f9f7ef9d9e5442ed2348", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_grpc_grpc_protobuf_nano", "actual": "@io_grpc_grpc_protobuf_nano//jar", "bind": "jar/io/grpc/grpc_protobuf_nano"})
    callback({"artifact": "io.grpc:grpc-protobuf:1.9.0", "lang": "java", "sha1": "94ca247577e4cf1a38d5ac9d536ac1d426a1ccc5", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_grpc_grpc_protobuf", "actual": "@io_grpc_grpc_protobuf//jar", "bind": "jar/io/grpc/grpc_protobuf"})
    callback({"artifact": "io.grpc:grpc-stub:1.9.0", "lang": "java", "sha1": "20e310f888860a27dfa509a69eebb236417ee93f", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_grpc_grpc_stub", "actual": "@io_grpc_grpc_stub//jar", "bind": "jar/io/grpc/grpc_stub"})
    callback({"artifact": "io.grpc:grpc-testing:1.9.0", "lang": "java", "sha1": "3d20675f0e64825f565a7d21456e7dbdd5886c6b", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_grpc_grpc_testing", "actual": "@io_grpc_grpc_testing//jar", "bind": "jar/io/grpc/grpc_testing"})
    callback({"artifact": "io.netty:netty-buffer:4.1.17.Final", "lang": "java", "sha1": "fdd68fb3defd7059a7392b9395ee941ef9bacc25", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_netty_netty_buffer", "actual": "@io_netty_netty_buffer//jar", "bind": "jar/io/netty/netty_buffer"})
    callback({"artifact": "io.netty:netty-codec-http2:4.1.17.Final", "lang": "java", "sha1": "f9844005869c6d9049f4b677228a89fee4c6eab3", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_netty_netty_codec_http2", "actual": "@io_netty_netty_codec_http2//jar", "bind": "jar/io/netty/netty_codec_http2"})
    callback({"artifact": "io.netty:netty-codec-http:4.1.17.Final", "lang": "java", "sha1": "251d7edcb897122b9b23f24ff793cd0739056b9e", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_netty_netty_codec_http", "actual": "@io_netty_netty_codec_http//jar", "bind": "jar/io/netty/netty_codec_http"})
    callback({"artifact": "io.netty:netty-codec-socks:4.1.17.Final", "lang": "java", "sha1": "a159bf1f3d5019e0d561c92fbbec8400967471fa", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_netty_netty_codec_socks", "actual": "@io_netty_netty_codec_socks//jar", "bind": "jar/io/netty/netty_codec_socks"})
    callback({"artifact": "io.netty:netty-codec:4.1.17.Final", "lang": "java", "sha1": "1d00f56dc9e55203a4bde5aae3d0828fdeb818e7", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_netty_netty_codec", "actual": "@io_netty_netty_codec//jar", "bind": "jar/io/netty/netty_codec"})
    callback({"artifact": "io.netty:netty-common:4.1.17.Final", "lang": "java", "sha1": "581c8ee239e4dc0976c2405d155f475538325098", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_netty_netty_common", "actual": "@io_netty_netty_common//jar", "bind": "jar/io/netty/netty_common"})
    callback({"artifact": "io.netty:netty-handler-proxy:4.1.17.Final", "lang": "java", "sha1": "9330ee60c4e48ca60aac89b7bc5ec2567e84f28e", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_netty_netty_handler_proxy", "actual": "@io_netty_netty_handler_proxy//jar", "bind": "jar/io/netty/netty_handler_proxy"})
    callback({"artifact": "io.netty:netty-handler:4.1.17.Final", "lang": "java", "sha1": "18c40ffb61a1d1979eca024087070762fdc4664a", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_netty_netty_handler", "actual": "@io_netty_netty_handler//jar", "bind": "jar/io/netty/netty_handler"})
    callback({"artifact": "io.netty:netty-resolver:4.1.17.Final", "lang": "java", "sha1": "8f386c80821e200f542da282ae1d3cde5cad8368", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_netty_netty_resolver", "actual": "@io_netty_netty_resolver//jar", "bind": "jar/io/netty/netty_resolver"})
    callback({"artifact": "io.netty:netty-tcnative-boringssl-static:2.0.7.Final", "lang": "java", "sha1": "a8ec0f0ee612fa89c709bdd3881c3f79fa00431d", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_netty_netty_tcnative_boringssl_static", "actual": "@io_netty_netty_tcnative_boringssl_static//jar", "bind": "jar/io/netty/netty_tcnative_boringssl_static"})
    callback({"artifact": "io.netty:netty-transport:4.1.17.Final", "lang": "java", "sha1": "9585776b0a8153182412b5d5366061ff486914c1", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_netty_netty_transport", "actual": "@io_netty_netty_transport//jar", "bind": "jar/io/netty/netty_transport"})
    callback({"artifact": "io.opencensus:opencensus-api:0.10.0", "lang": "java", "sha1": "46bcf07e0bd835022ccd531d99c3eb813382d4d8", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_opencensus_opencensus_api", "actual": "@io_opencensus_opencensus_api//jar", "bind": "jar/io/opencensus/opencensus_api"})
    callback({"artifact": "io.opencensus:opencensus-contrib-grpc-metrics:0.10.0", "lang": "java", "sha1": "e47f918dc577b6316f57a884c500b13a98d3c11b", "repository": "https://repo.maven.apache.org/maven2/", "name": "io_opencensus_opencensus_contrib_grpc_metrics", "actual": "@io_opencensus_opencensus_contrib_grpc_metrics//jar", "bind": "jar/io/opencensus/opencensus_contrib_grpc_metrics"})
    callback({"artifact": "junit:junit:4.12", "lang": "java", "sha1": "2973d150c0dc1fefe998f834810d68f278ea58ec", "repository": "https://repo.maven.apache.org/maven2/", "name": "junit_junit", "actual": "@junit_junit//jar", "bind": "jar/junit/junit"})
    callback({"artifact": "org.apache.httpcomponents:httpclient:4.0.1", "lang": "java", "sha1": "1d7d28fa738bdbfe4fbd895d9486308999bdf440", "repository": "https://repo.maven.apache.org/maven2/", "name": "org_apache_httpcomponents_httpclient", "actual": "@org_apache_httpcomponents_httpclient//jar", "bind": "jar/org/apache/httpcomponents/httpclient"})
    callback({"artifact": "org.apache.httpcomponents:httpcore:4.0.1", "lang": "java", "sha1": "e813b8722c387b22e1adccf7914729db09bcb4a9", "repository": "https://repo.maven.apache.org/maven2/", "name": "org_apache_httpcomponents_httpcore", "actual": "@org_apache_httpcomponents_httpcore//jar", "bind": "jar/org/apache/httpcomponents/httpcore"})
    callback({"artifact": "org.checkerframework:checker-compat-qual:2.0.0", "lang": "java", "sha1": "fc89b03860d11d6213d0154a62bcd1c2f69b9efa", "repository": "https://repo.maven.apache.org/maven2/", "name": "org_checkerframework_checker_compat_qual", "actual": "@org_checkerframework_checker_compat_qual//jar", "bind": "jar/org/checkerframework/checker_compat_qual"})
    callback({"artifact": "org.codehaus.mojo:animal-sniffer-annotations:1.14", "lang": "java", "sha1": "775b7e22fb10026eed3f86e8dc556dfafe35f2d5", "repository": "https://repo.maven.apache.org/maven2/", "name": "org_codehaus_mojo_animal_sniffer_annotations", "actual": "@org_codehaus_mojo_animal_sniffer_annotations//jar", "bind": "jar/org/codehaus/mojo/animal_sniffer_annotations"})
    callback({"artifact": "org.hamcrest:hamcrest-core:1.3", "lang": "java", "sha1": "42a25dc3219429f0e5d060061f71acb49bf010a0", "repository": "https://repo.maven.apache.org/maven2/", "name": "org_hamcrest_hamcrest_core", "actual": "@org_hamcrest_hamcrest_core//jar", "bind": "jar/org/hamcrest/hamcrest_core"})
    callback({"artifact": "org.mockito:mockito-core:1.9.5", "lang": "java", "sha1": "c3264abeea62c4d2f367e21484fbb40c7e256393", "repository": "https://repo.maven.apache.org/maven2/", "name": "org_mockito_mockito_core", "actual": "@org_mockito_mockito_core//jar", "bind": "jar/org/mockito/mockito_core"})
    callback({"artifact": "org.objenesis:objenesis:1.0", "lang": "java", "sha1": "9b473564e792c2bdf1449da1f0b1b5bff9805704", "repository": "https://repo.maven.apache.org/maven2/", "name": "org_objenesis_objenesis", "actual": "@org_objenesis_objenesis//jar", "bind": "jar/org/objenesis/objenesis"})