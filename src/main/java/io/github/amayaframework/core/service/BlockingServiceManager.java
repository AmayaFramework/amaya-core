package io.github.amayaframework.core.service;

public final class BlockingServiceManager extends AbstractServiceManager {

    public BlockingServiceManager(ServiceCollection collection, ServiceHandler handler) {
        super(collection, handler);
    }

    @Override
    protected void checkedStart() throws Throwable {
        for (var service : collection) {
            handler.start(service);
        }
    }

    @Override
    protected void checkedStop() throws Throwable {
        for (var service : collection) {
            handler.stop(service);
        }
    }
}
