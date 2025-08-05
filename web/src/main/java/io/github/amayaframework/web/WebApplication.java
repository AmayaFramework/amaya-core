package io.github.amayaframework.web;

import io.github.amayaframework.application.Application;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.http.HttpVersion;
import io.github.amayaframework.server.HttpServerConfig;

import java.net.InetSocketAddress;

/**
 * An interface describing an abstract web application that manages services.
 */
public interface WebApplication extends Application<HttpContext> {

    /**
     * Gets http server config. Any config changes are reflected on the server and vice versa.
     *
     * @return the {@link HttpServerConfig} instance
     */
    HttpServerConfig serverConfig();

    /**
     * Binds web application to given address with the specified http version.
     * If the implementation supports it, multiple bindings are possible.
     *
     * @param address the specified address that the server will listen to, must be non-null
     * @param version the specified http version, must be non-null
     */
    void bind(InetSocketAddress address, HttpVersion version);

    /**
     * Binds web application to given address.
     * If the implementation supports it, multiple bindings are possible.
     *
     * @param address the specified address that the server will listen to, must be non-null
     */
    void bind(InetSocketAddress address);

    void bind(String host, int port, HttpVersion version);

    void bind(String host, int port);

    /**
     * Binds web application to given port with the specified http version.
     * If the implementation supports it, multiple bindings are possible.
     *
     * @param port    the specified port that the server will listen to
     * @param version the specified http version, must be non-null
     */
    void bind(int port, HttpVersion version);

    /**
     * Binds web application to given port.
     * If the implementation supports it, multiple bindings are possible.
     *
     * @param port the specified port that the server will listen to
     */
    void bind(int port);
}
