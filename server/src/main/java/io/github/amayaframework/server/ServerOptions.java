package io.github.amayaframework.server;

import com.github.romanqed.jtype.JType;
import io.github.amayaframework.http.HttpVersion;
import io.github.amayaframework.options.Key;

import java.net.InetSocketAddress;

/**
 * A utility class defining standard {@link io.github.amayaframework.options.Key keys}
 * used for server configuration options.
 * <p>
 * All fields are static constants representing option keys and their expected value types.
 */
public final class ServerOptions {
    private ServerOptions() {
    }

    /**
     * The key for the listened port option.
     * <br>
     * Required type: {@link Integer}.
     */
    public static final Key<Integer> PORT = Key.of("port", Integer.class);

    /**
     * The key for the list of listened ports.
     * <p>
     * Required type: {@code Iterable<Integer>}.
     * <p>
     * <b>Note:</b> Support for multiple ports depends on the specific server implementation.
     */
    public static final Key<Iterable<Integer>> PORTS = Key.of("ports", new JType<>(){});

    /**
     * The key for the listened ip address option.
     * <br>
     * Required type: {@link InetSocketAddress}.
     */
    public static final Key<InetSocketAddress> IP = Key.of("ip", InetSocketAddress.class);

    /**
     * The key for the list of listened IP addresses.
     * <p>
     * Required type: {@code Iterable<InetSocketAddress>}.
     * <p>
     * <b>Note:</b> Support for multiple addresses depends on the specific server implementation.
     */
    public static final Key<Iterable<InetSocketAddress>> IPS = Key.of("ips", new JType<>(){});

    /**
     * The key for the http version option.
     * <br>
     * Required type: {@link HttpVersion}.
     */
    public static final Key<HttpVersion> HTTP_VERSION = Key.of("http_version", HttpVersion.class);

    /**
     * Name of the option that controls whether the server includes the
     * {@code Server} HTTP response header.
     *
     * <p>When enabled, the container will add its version information
     * in the {@code Server} header of responses. Disabling this option
     * suppresses the header, which is often desirable for security
     * hardening or to reduce unnecessary information disclosure.
     *
     * <p>Required type: {@link Boolean}.
     */
    public static final String SEND_SERVER = "send_server";

    /**
     * Name of the option that controls whether the server includes the
     * {@code X-Powered-By} HTTP response header.
     *
     * <p>When enabled, the container will add a {@code X-Powered-By}
     * header to responses. Disabling this option suppresses the header,
     * which is often desirable for security hardening or to reduce
     * unnecessary information disclosure.
     *
     * <p>Required type: {@link Boolean}.
     */
    public static final String SEND_POWERED_BY = "send_powered_by";

    /**
     * The key for preferring asynchronous handling of requests.
     * <br>
     * Required type: {@link Boolean}.
     * <p>
     * If set to {@code true}, the server should prefer asynchronous
     * execution where possible. In environments with Loom (virtual threads),
     * this option may be ignored.
     */
    public static final Key<Boolean> PREFER_ASYNC = Key.of("prefer_async", Boolean.class);

    /**
     * The key for providing a pre-initialized {@link HttpMethodBuffer}.
     * <br>
     * Required type: {@link HttpMethodBuffer}.
     * <p>
     * This allows replacing the default method lookup strategy with
     * a custom buffer implementation.
     */
    public static final Key<HttpMethodBuffer> HTTP_METHOD_BUFFER = Key.of(
            "http_method_buffer",
            HttpMethodBuffer.class
    );

    /**
     * The key for providing a pre-initialized {@link HttpCodeBuffer}.
     * <br>
     * Required type: {@link HttpCodeBuffer}.
     * <p>
     * This allows replacing the default status code lookup strategy with
     * a custom buffer implementation.
     */
    public static final Key<HttpCodeBuffer> HTTP_CODE_BUFFER = Key.of("http_code_buffer", HttpCodeBuffer.class);
}
