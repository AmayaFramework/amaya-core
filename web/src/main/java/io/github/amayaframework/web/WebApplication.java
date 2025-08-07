package io.github.amayaframework.web;

import io.github.amayaframework.application.Application;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.http.HttpVersion;
import io.github.amayaframework.server.HttpServerConfig;
import jakarta.servlet.ServletContext;

import java.net.InetSocketAddress;

/**
 * An interface describing an abstract web application that manages services
 * and handles HTTP requests via an embedded HTTP server.
 * <p>
 * Provides methods to bind the application to network addresses and configure
 * the underlying HTTP server.
 */
public interface WebApplication extends Application<HttpContext> {

    /**
     * Gets the {@link ServletContext} associated with the underlying HTTP server.
     * <p>
     * This provides low-level access to the servlet environment, if available.
     * If the implementation does not support a {@code ServletContext}, this method
     * returns {@code null}.
     * <p>
     *
     * @return the {@code ServletContext} instance if supported, or {@code null} otherwise
     */
    ServletContext servletContext();

    /**
     * Gets the HTTP server configuration associated with this web application.
     * Any changes to this config are reflected directly on the server and vice versa.
     *
     * @return the {@link HttpServerConfig} instance
     */
    HttpServerConfig serverConfig();

    /**
     * Binds this web application to the specified {@link InetSocketAddress} with
     * the given HTTP version.
     * <p>
     * If the implementation supports it, multiple bindings are allowed.
     *
     * @param address the address to bind to, must be non-null
     * @param version the HTTP version to use, must be non-null
     */
    void bind(InetSocketAddress address, HttpVersion version);

    /**
     * Binds this web application to the specified {@link InetSocketAddress} using
     * the default HTTP version.
     * <p>
     * If the implementation supports it, multiple bindings are allowed.
     *
     * @param address the address to bind to, must be non-null
     */
    void bind(InetSocketAddress address);

    /**
     * Binds this web application to the given host and port with the specified
     * HTTP version.
     * <p>
     * If {@code host} is {@code null}, this method is equivalent to {@link #bind(int, HttpVersion)}.
     * If the implementation supports it, multiple bindings are allowed.
     *
     * @param host    the host to bind to, may be {@code null}
     * @param port    the port to bind to
     * @param version the HTTP version to use, must be non-null
     */
    void bind(String host, int port, HttpVersion version);

    /**
     * Binds this web application to the given host and port using the default
     * HTTP version.
     * <p>
     * If {@code host} is {@code null}, this method is equivalent to {@link #bind(int)}.
     * If the implementation supports it, multiple bindings are allowed.
     *
     * @param host the host to bind to, may be {@code null}
     * @param port the port to bind to
     */
    void bind(String host, int port);

    /**
     * Binds this web application to the specified port with the given HTTP version.
     * <p>
     * If the implementation supports it, multiple bindings are allowed.
     *
     * @param port    the port to bind to
     * @param version the HTTP version to use, must be non-null
     */
    void bind(int port, HttpVersion version);

    /**
     * Binds this web application to the specified port using the default HTTP version.
     * <p>
     * If the implementation supports it, multiple bindings are allowed.
     *
     * @param port the port to bind to
     */
    void bind(int port);
}
