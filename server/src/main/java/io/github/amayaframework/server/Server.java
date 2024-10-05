package io.github.amayaframework.server;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.context.Context;
import io.github.amayaframework.service.Service;

import java.net.InetSocketAddress;

/**
 * An interface describing an abstract web server.
 *
 * @param <T> the protocol context type
 */
public interface Server<T extends Context> extends Service {

    /**
     * Binds server to given {@link InetSocketAddress} address.
     * If the implementation supports it, multiple bindings are possible.
     *
     * @param address the specified address that the server will listen to, must be non-null
     */
    void bind(InetSocketAddress address);

    /**
     * Binds server to given port.
     * If the implementation supports it, multiple bindings are possible.
     *
     * @param port the specified port that the server will listen to
     */
    void bind(int port);

    /**
     * Gets server config. Any config changes are reflected on the server and vice versa.
     *
     * @return the {@link ServerConfig} instance
     */
    ServerConfig getConfig();

    /**
     * Gets protocol context handler.
     *
     * @return the {@link Runnable1} instance
     */
    Runnable1<T> getHandler();

    /**
     * Sets the context handler for the protocol used by the server. This handler is called for each transaction.
     *
     * @param handler the {@link Runnable1} instance to be set as context handler
     * @throws IllegalStateException if server started
     */
    void setHandler(Runnable1<T> handler);

    /**
     * Starts the server, after which the listening of the specified addresses begins.
     * While the server is running, context change is prohibited.
     * After the stop, the server can be started again.
     *
     * @throws IllegalStateException if server already started
     * @throws Throwable             if any problems occurred during start
     */
    @Override
    void start() throws Throwable;

    /**
     * Stops the server.
     * After the stop, a change of context is possible.
     *
     * @throws IllegalStateException if server already stopped
     * @throws Throwable             if any problems occurred during stop
     */
    @Override
    void stop() throws Throwable;
}
