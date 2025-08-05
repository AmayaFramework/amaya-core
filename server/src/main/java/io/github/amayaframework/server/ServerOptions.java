package io.github.amayaframework.server;

import io.github.amayaframework.http.HttpVersion;
import io.github.amayaframework.options.Key;

import java.net.InetSocketAddress;

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
     * The key for the listened ip address option.
     * <br>
     * Required type: {@link InetSocketAddress}.
     */
    public static final Key<InetSocketAddress> IP = Key.of("ip", InetSocketAddress.class);

    /**
     * The key for the http version option.
     * <br>
     * Required type: {@link HttpVersion}.
     */
    public static final Key<HttpVersion> HTTP_VERSION = Key.of("http_version", HttpVersion.class);
}
