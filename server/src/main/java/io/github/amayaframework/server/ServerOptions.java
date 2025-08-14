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
}
