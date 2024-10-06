package io.github.amayaframework.service;

/**
 * An abstract interface describing an observable service for which an up-to-date state is maintained.
 */
public interface WatchedService extends Service {

    /**
     * Gets the current state of the service.
     *
     * @return the current {@link ServiceState}
     */
    ServiceState getState();

    /**
     * Stops watching and returns the original service.
     * <br>
     * This action is irreversible, after which the {@link WatchedService} instance can no longer be used.
     *
     * @return the {@link Service} instance
     */
    Service unwatch();
}
