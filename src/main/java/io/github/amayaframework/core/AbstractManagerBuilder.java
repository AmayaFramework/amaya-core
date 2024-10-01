package io.github.amayaframework.core;

import io.github.amayaframework.service.Service;
import io.github.amayaframework.service.ServiceHandler;
import io.github.amayaframework.service.ServiceManager;
import io.github.amayaframework.service.ServiceManagerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractManagerBuilder implements ServiceManagerBuilder {
    protected ServiceManagerFactory factory;
    protected ServiceHandler handler;
    protected List<Service> services;

    protected void innerReset() {
        this.factory = null;
        this.handler = null;
        this.services = null;
    }

    protected void add(Service service) {
        if (services == null) {
            services = new LinkedList<>();
        }
        services.add(service);
    }

    @Override
    public ServiceManagerBuilder setFactory(ServiceManagerFactory factory) {
        this.factory = factory;
        return this;
    }

    @Override
    public ServiceManagerBuilder setHandler(ServiceHandler handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public ServiceManagerBuilder reset() {
        innerReset();
        return this;
    }

    protected abstract ServiceManagerFactory getDefaultFactory();

    protected abstract ServiceHandler getDefaultHandler();

    @Override
    public ServiceManager build() {
        try {
            var factory = Objects.requireNonNullElse(this.factory, getDefaultFactory());
            var handler = Objects.requireNonNullElse(this.handler, getDefaultHandler());
            var ret = factory.create(handler);
            services.forEach(ret::add);
            return ret;
        } finally {
            innerReset();
        }
    }
}
