package io.github.amayaframework.server;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.http.HttpVersion;

import java.net.InetSocketAddress;

/**
 * An interface describing an abstract http server.
 */
public interface HttpServer extends Server<HttpContext> {

    /**
     * Binds server to given {@link InetSocketAddress} address with the specified http version.
     * If the implementation supports it, multiple bindings are possible.
     *
     * @param address the specified address that the server will listen to, must be non-null
     * @param version the specified http version, must be non-null
     */
    void bind(InetSocketAddress address, HttpVersion version);

    /**
     * Binds server to given port with the specified http version.
     * If the implementation supports it, multiple bindings are possible.
     *
     * @param port    the specified port that the server will listen to
     * @param version the specified http version, must be non-null
     */
    void bind(int port, HttpVersion version);

    /**
     * Gets http server config. Any config changes are reflected on the server and vice versa.
     *
     * @return the {@link HttpServerConfig} instance
     */
    @Override
    HttpServerConfig getConfig();

    /**
     * Gets http context handler.
     *
     * @return the {@link Runnable1} instance
     */
    @Override
    Runnable1<HttpContext> getHandler();

    /**
     * Sets the context handler for the http protocol. This handler is called for each transaction.
     *
     * @param handler the {@link Runnable1} instance to be set as context handler
     * @throws IllegalStateException if server started
     */
    @Override
    void setHandler(Runnable1<HttpContext> handler);
}
