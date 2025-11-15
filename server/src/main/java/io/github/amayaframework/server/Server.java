package io.github.amayaframework.server;

import com.github.romanqed.jct.CancelToken;
import com.github.romanqed.juni.UniRunnable1;
import io.github.amayaframework.context.Context;
import io.github.amayaframework.service.Service;
import io.github.amayaframework.service.ServiceCallback;

import java.net.InetSocketAddress;

/**
 * An interface describing an abstract web server.
 * <p>
 * This server handles incoming protocol contexts of type {@code T} and manages
 * its lifecycle according to the {@link Service} contract.
 *
 * @param <T> the protocol context type that this server processes
 */
public interface Server<T extends Context> extends Service {

    /**
     * Binds the server to the given {@link InetSocketAddress} address.
     * If the implementation supports multiple bindings, this method can be called multiple times.
     * <p>
     * This method only configures the server to listen on the specified address.
     * Actual listening (accepting connections) begins only after the server is started.
     * If the server is currently stopped, calling this method does not start listening.
     *
     * @param address the address the server will listen on; must not be {@code null}
     * @throws IllegalArgumentException if the address is {@code null}
     *
     * @return TODO
     */
    Connector bind(InetSocketAddress address);

    /**
     * Binds the server to the specified port on all local interfaces (usually 0.0.0.0).
     * If multiple bindings are supported, this method can be called multiple times.
     * <p>
     * This method only configures the server to listen on the specified port.
     * Actual listening (accepting connections) begins only after the server is started.
     * If the server is currently stopped, calling this method does not start listening.
     *
     * @param port the port number to bind the server to; must be in valid port range (0-65535)
     *
     * @return TODO
     */
    Connector bind(int port);

    /**
     * Gets the current server configuration.
     * Changes to the returned config object are reflected in the server behavior immediately, and vice versa.
     *
     * @return the server configuration instance
     */
    ServerConfig config();

    /**
     * Gets the current request handler.
     * The handler is a callback that processes each incoming protocol context.
     * <p>
     * If no handler is set, incoming requests may be rejected or ignored depending on implementation.
     *
     * @return the currently registered handler, or {@code null} if no handler is set
     */
    UniRunnable1<T> handler();

    /**
     * Sets the request handler that processes incoming protocol contexts.
     * The handler will be invoked for each new connection or request context.
     * <p>
     * Implementations should ensure the handler is thread-safe or properly synchronized
     * if the server runs in a multi-threaded environment.
     *
     * @param handler the handler to process incoming contexts, may be {@code null}
     */
    void handler(UniRunnable1<T> handler);

    /**
     * Starts the server and begins listening on all bound addresses.
     * <p>
     * While the server is running, changes to the context or binding are prohibited.
     * After stopping, the server may be started again.
     *
     * @param token    the cancellation token for cooperative stopping
     * @param callback lifecycle callback to receive start/stop events
     * @throws IllegalStateException if the server is already started
     * @throws Throwable             if any error occurs during startup
     */
    @Override
    void start(CancelToken token, ServiceCallback callback) throws Throwable;

    /**
     * Stops the server and releases any network resources.
     * <p>
     * After the server is stopped, context changes and new bindings are allowed.
     *
     * @param token cancellation token for cooperative stopping
     * @throws IllegalStateException if the server is already stopped
     * @throws Throwable             if any error occurs during shutdown
     */
    @Override
    void stop(CancelToken token) throws Throwable;
}
