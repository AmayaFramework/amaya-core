package io.github.amayaframework.core;

import io.github.amayaframework.application.AbstractManagerBuilder;
import io.github.amayaframework.application.ServiceManagerBuilder;
import io.github.amayaframework.service.Service;
import io.github.amayaframework.service.ServiceHandler;
import io.github.amayaframework.service.ServiceManagerFactory;

import java.lang.reflect.Type;
import java.util.Objects;

final class StandaloneManagerBuilder extends AbstractManagerBuilder {
    private final ServiceManagerFactory defaultFactory;
    private final ServiceHandler defaultHandler;

    StandaloneManagerBuilder(ServiceManagerFactory defaultFactory, ServiceHandler defaultHandler) {
        this.defaultFactory = defaultFactory;
        this.defaultHandler = defaultHandler;
    }

    @Override
    protected ServiceManagerFactory getDefaultFactory() {
        return defaultFactory;
    }

    @Override
    protected ServiceHandler getDefaultHandler() {
        return defaultHandler;
    }

    @Override
    public ServiceManagerBuilder addService(Service service) {
        add(Objects.requireNonNull(service));
        return this;
    }

    @Override
    public ServiceManagerBuilder addService(Type type, Service service) {
        // Just add service to list and do nothing about di
        add(Objects.requireNonNull(service));
        return this;
    }

    @Override
    public ServiceManagerBuilder addService(Type type, Class<? extends Service> implementation) {
        throw new UnsupportedOperationException("The amaya di module is not loaded");
    }

    @Override
    public ServiceManagerBuilder addService(Class<? extends Service> implementation) {
        throw new UnsupportedOperationException("The amaya di module is not loaded");
    }

    @Override
    public <T extends Service> ServiceManagerBuilder addService(Class<T> type, Class<? extends T> implementation) {
        throw new UnsupportedOperationException("The amaya di module is not loaded");
    }
}
