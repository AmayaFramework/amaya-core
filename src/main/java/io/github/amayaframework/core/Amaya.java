package io.github.amayaframework.core;

/**
 * A universal interface that encapsulates interaction with the framework server.
 *
 * @param <T> server class type
 */
public interface Amaya<T> extends AutoCloseable {
    /**
     * @return the ip address to which the server was bound
     */
    String getAddress();

    /**
     * @return the ip port to which the server was bound
     */
    int getPort();

    /**
     * @return the implementation of the internal server
     */
    T getServer();

    /**
     * Starts the server
     *
     * @throws Throwable if an exception occurs at startup
     */
    void start() throws Throwable;
}
