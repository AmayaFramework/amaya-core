package io.github.amayaframework.server;

import com.github.romanqed.juni.UniRunnable1;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.http.HttpVersion;
import jakarta.servlet.ServletContext;

import java.net.InetSocketAddress;

/**
 * An interface describing an abstract HTTP server capable of binding
 * to network addresses and processing HTTP requests within a
 * specific {@link HttpContext}.
 * <p>
 * This server supports lifecycle management through the {@link Server}
 * interface, allowing it to be started and stopped cooperatively.
 * <p>
 * Implementations of this interface typically integrate with servlet
 * containers or provide embedded HTTP server capabilities.
 * <p>
 * The server allows binding to multiple addresses and ports with
 * specified HTTP protocol versions, supporting simultaneous
 * listening on multiple endpoints.
 * <p>
 * HTTP request processing is performed by a user-configurable handler,
 * represented by a {@link com.github.romanqed.juni.UniRunnable1} that
 * accepts {@link HttpContext} instances.
 * <p>
 * This interface also optionally exposes the underlying
 * {@link jakarta.servlet.ServletContext} if supported by the
 * implementation.
 *
 * @see Server
 * @see HttpContext
 * @see jakarta.servlet.ServletContext
 */
public interface HttpServer extends Server<HttpContext> {

    /**
     * Returns the {@link ServletContext} associated with this server.
     * <p>
     * If the implementation does not support a {@code ServletContext}, this method will return {@code null}.
     * Otherwise, the returned context is guaranteed to be ready for use.
     *
     * @return the {@code ServletContext} if supported, or {@code null} otherwise
     */
    ServletContext servletContext();

    @Override
    HttpConnector bind(InetSocketAddress address);

    @Override
    HttpConnector bind(int port);

    /**
     * Binds server to given {@link InetSocketAddress} address with the specified HTTP version.
     * If the implementation supports it, multiple bindings are possible.
     * <p>
     * Binding will start listening immediately only if the server is already started.
     * Otherwise, binding only adds the address to configuration without starting to listen.
     *
     * @param address the specified address that the server will listen to, must be non-null
     * @param version the specified HTTP version, must be non-null
     */
    HttpConnector bind(InetSocketAddress address, HttpVersion version);

    /**
     * Binds server to given port with the specified HTTP version.
     * If the implementation supports it, multiple bindings are possible.
     * <p>
     * Binding will start listening immediately only if the server is already started.
     * Otherwise, binding only adds the port to configuration without starting to listen.
     *
     * @param port    the specified port that the server will listen to
     * @param version the specified HTTP version, must be non-null
     */
    HttpConnector bind(int port, HttpVersion version);

    /**
     * Gets HTTP server config. Any config changes are reflected on the server and vice versa.
     *
     * @return the {@link HttpServerConfig} instance
     */
    @Override
    HttpServerConfig config();

    /**
     * Gets the handler responsible for processing HTTP contexts.
     *
     * @return the handler as a {@link UniRunnable1} accepting {@link HttpContext}
     */
    @Override
    UniRunnable1<HttpContext> handler();

    /**
     * Sets the handler responsible for processing HTTP contexts.
     * This handler will be invoked for each HTTP request context.
     *
     * @param handler the handler to set, must not be null
     */
    @Override
    void handler(UniRunnable1<HttpContext> handler);
}
