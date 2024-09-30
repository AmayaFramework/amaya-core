package io.github.amayaframework.server;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.context.HttpContext;

/**
 * An interface describing an abstract http server.
 */
public interface HttpServer extends Server<HttpContext> {

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
