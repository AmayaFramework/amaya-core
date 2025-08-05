package io.github.amayaframework.server;

import com.github.romanqed.jct.CancelToken;
import com.github.romanqed.juni.UniRunnable1;
import io.github.amayaframework.context.Context;
import io.github.amayaframework.service.Service;
import io.github.amayaframework.service.ServiceCallback;

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
    ServerConfig config();

    // TODO jdoc
    UniRunnable1<T> handler();

    // TODO jdoc
    void handler(UniRunnable1<T> handler);

    /**
     * Starts the server, after which the listening of the specified addresses begins.
     * While the server is running, context change is prohibited.
     * After the stop, the server can be started again.
     *
     * @param token cancellation token for cooperative stopping
     * @param callback lifecycle callback
     * @throws IllegalStateException if server already started
     * @throws Throwable             if any problems occurred during the start
     */
    @Override
    void start(CancelToken token, ServiceCallback callback) throws Throwable;

    /**
     * Stops the server.
     * After the stop, a change of context is possible.
     *
     * @param token cancellation token for cooperative stopping
     * @throws IllegalStateException if server already stopped
     * @throws Throwable             if any problems occurred during stop
     */
    @Override
    void stop(CancelToken token) throws Throwable;
}
