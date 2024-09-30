package io.github.amayaframework.impl;

import io.github.amayaframework.core.ServiceManagerBuilder;
import io.github.amayaframework.service.*;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class PlainManagerBuilder implements ServiceManagerBuilder {
    private static final ServiceHandler PLAIN_HANDLER = new PlainServiceHandler();
    private static final ServiceManagerFactory FACTORY = new HandledManagerFactory();

    private ServiceManagerFactory factory;
    private ServiceHandler handler;
    private List<Service> services;

    public PlainManagerBuilder() {
        this.reset();
    }

    private void reset() {
        this.factory = FACTORY;
        this.handler = PLAIN_HANDLER;
        this.services = new LinkedList<>();
    }

    @Override
    public ServiceManagerBuilder setFactory(ServiceManagerFactory factory) {
        this.factory = Objects.requireNonNull(factory);
        return this;
    }

    @Override
    public ServiceManagerBuilder setHandler(ServiceHandler handler) {
        this.handler = Objects.requireNonNull(handler);
        return this;
    }

    @Override
    public ServiceManagerBuilder addService(Service service) {
        this.services.add(Objects.requireNonNull(service));
        return this;
    }

    @Override
    public ServiceManagerBuilder addService(Type type, Service service) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServiceManagerBuilder addService(Type type, Class<? extends Service> implementation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServiceManagerBuilder addService(Class<? extends Service> implementation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends Service> ServiceManagerBuilder addService(Class<T> type, Class<? extends T> implementation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServiceManager build() {
        try {
            var ret = factory.create(handler);
            services.forEach(ret::add);
            return ret;
        } finally {
            this.reset();
        }
    }
}
