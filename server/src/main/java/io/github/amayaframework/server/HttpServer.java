package io.github.amayaframework.server;

import com.github.romanqed.juni.UniRunnable1;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.http.HttpVersion;
import jakarta.servlet.ServletContext;

import java.net.InetSocketAddress;

/**
 * An interface describing an abstract http server.
 */
public interface HttpServer extends Server<HttpContext> {

    /**
     * Returns the {@link ServletContext} associated with this server.
     * <p>
     * If the implementation does not support a {@code ServletContext}, this method will return {@code null}.
     * Otherwise, the returned context is guaranteed to be fully initialized and ready for use.
     *
     * @return the {@code ServletContext} if supported, or {@code null} otherwise
     */
    ServletContext servletContext();

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
    HttpServerConfig config();

    // TODO jdoc
    @Override
    UniRunnable1<HttpContext> handler();

    // TODO jdoc
    @Override
    void handler(UniRunnable1<HttpContext> handler);
}
