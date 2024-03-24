package io.github.amayaframework.core.service;

public abstract class AbstractServiceManager extends AbstractService implements ServiceManager {
    protected final ServiceCollection collection;
    protected final ServiceHandler handler;

    protected AbstractServiceManager(ServiceCollection collection, ServiceHandler handler) {
        super(ServiceState.STOPPED);
        this.collection = collection;
        this.handler = handler;
    }

    @Override
    public ServiceCollection getServiceCollection() {
        return collection;
    }
}
