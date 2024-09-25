package io.github.amayaframework.service;

/**
 * An interface describing an abstract {@link ServiceManager} factory.
 */
public interface ServiceManagerFactory {

    /**
     * Creates implementation of {@link ServiceManager} with given {@link ServiceHandler} instance.
     *
     * @param handler the specified {@link ServiceHandler} instance to be used by manager
     * @return {@link ServiceManager} instance
     */
    ServiceManager create(ServiceHandler handler);
}
