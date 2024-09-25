package io.github.amayaframework.service;

/**
 * An interface describing an abstract service handler.
 * It is used as an abstraction to modify the behavior of the {@link ServiceManager}.
 */
public interface ServiceHandler {

    /**
     * Tries to start all given services. Behaviour depends on implementation.
     *
     * @param services the specified services to be started
     * @throws Throwable if any problems occurred
     */
    void start(Iterable<WatchedService> services) throws Throwable;

    /**
     * Tries to stop all given services. Behaviour depends on implementation.
     *
     * @param services the specified services to be stopped
     * @throws Throwable if any problems occurred
     */
    void stop(Iterable<WatchedService> services) throws Throwable;
}
