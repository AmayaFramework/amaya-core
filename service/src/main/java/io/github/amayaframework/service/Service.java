package io.github.amayaframework.service;

/**
 * An interface describing an abstract service running in the background.
 */
public interface Service {

    /**
     * Starts the service.
     *
     * @throws Throwable if any problems occurs during service start
     */
    void start() throws Throwable;

    /**
     * Stops the service.
     *
     * @throws Throwable if any problems occurs during service stop
     */
    void stop() throws Throwable;
}
