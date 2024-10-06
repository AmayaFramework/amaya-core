package io.github.amayaframework.core;

import io.github.amayaframework.service.Service;
import io.github.amayaframework.service.ServiceHandler;
import io.github.amayaframework.service.ServiceManager;
import io.github.amayaframework.service.ServiceManagerFactory;

import java.lang.reflect.Type;

final class WrappedManagerBuilder implements ServiceManagerBuilder {
    private final ServiceManagerBuilder builder;

    WrappedManagerBuilder(ServiceManagerBuilder builder) {
        this.builder = builder;
    }

    @Override
    public ServiceManagerBuilder setFactory(ServiceManagerFactory factory) {
        builder.setFactory(factory);
        return this;
    }

    @Override
    public ServiceManagerBuilder setHandler(ServiceHandler handler) {
        builder.setHandler(handler);
        return this;
    }

    @Override
    public ServiceManagerBuilder addService(Service service) {
        builder.addService(service);
        return this;
    }

    @Override
    public ServiceManagerBuilder addService(Type type, Service service) {
        builder.addService(type, service);
        return this;
    }

    @Override
    public ServiceManagerBuilder addService(Type type, Class<? extends Service> implementation) {
        builder.addService(type, implementation);
        return this;
    }

    @Override
    public ServiceManagerBuilder addService(Class<? extends Service> implementation) {
        builder.addService(implementation);
        return this;
    }

    @Override
    public <T extends Service> ServiceManagerBuilder addService(Class<T> type, Class<? extends T> implementation) {
        builder.addService(type, implementation);
        return this;
    }

    @Override
    public void reset() {
        builder.reset();
    }

    @Override
    public ServiceManager build() {
        throw new UnsupportedOperationException("The builder is managed by the application builder");
    }
}
