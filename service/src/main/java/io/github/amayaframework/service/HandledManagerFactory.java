package io.github.amayaframework.service;

/**
 * Implementation of {@link ServiceManagerFactory} that creates {@link HandledServiceManager} instances.
 */
public class HandledManagerFactory implements ServiceManagerFactory {

    @Override
    public ServiceManager create(ServiceHandler handler) {
        return new HandledServiceManager(handler);
    }
}
