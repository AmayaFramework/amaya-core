package io.github.amayaframework.core;

import io.github.amayaframework.core.handlers.EventManager;

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
     * @return the event manager of the current instance of the framework.
     */
    EventManager getEventManager();

    /**
     * Starts the server
     *
     * @throws Throwable if an exception occurs at startup
     */
    void start() throws Throwable;
}
