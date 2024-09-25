package io.github.amayaframework.service;

/**
 * An interface describing an abstract service manager.
 */
public interface ServiceManager extends Service {

    /**
     * Registers given service for management and creates a {@link WatchedService} instance for it.
     *
     * @param service the specified service to be managed
     * @return {@link WatchedService} instance containing given service
     */
    WatchedService add(Service service);

    /**
     * Gets an unmodifiable {@link Iterable} instance, containing all managed services.
     *
     * @return an {@link Iterable} instance
     */
    Iterable<WatchedService> getServices();
}
