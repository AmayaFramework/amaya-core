package io.github.amayaframework.application;

import io.github.amayaframework.service.Service;
import io.github.amayaframework.service.ServiceHandler;
import io.github.amayaframework.service.ServiceManager;
import io.github.amayaframework.service.ServiceManagerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A class that provides a skeletal implementation of the {@link ServiceManagerBuilder}.
 */
public abstract class AbstractManagerBuilder implements ServiceManagerBuilder {
    /**
     * Service manager factory.
     */
    protected ServiceManagerFactory factory;
    /**
     * Service handler.
     */
    protected ServiceHandler handler;
    /**
     * List of managed services.
     */
    protected List<Service> services;

    @Override
    public void reset() {
        this.factory = null;
        this.handler = null;
        this.services = null;
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

    /**
     * Adds service to managed services list.
     *
     * @param service the specified {@link Service} instance, must be non-null
     */
    protected void add(Service service) {
        if (services == null) {
            services = new LinkedList<>();
        }
        services.add(service);
    }

    /**
     * Gets default service manager factory.
     *
     * @return the {@link ServiceManagerFactory} instance
     */
    protected abstract ServiceManagerFactory getDefaultFactory();

    /**
     * Gets default service handler.
     *
     * @return the {@link ServiceHandler} instance
     */
    protected abstract ServiceHandler getDefaultHandler();

    @Override
    public ServiceManager build() {
        try {
            var factory = Objects.requireNonNullElse(this.factory, getDefaultFactory());
            var handler = Objects.requireNonNullElse(this.handler, getDefaultHandler());
            var ret = factory.create(handler);
            if (services != null) {
                services.forEach(ret::add);
            }
            return ret;
        } finally {
            reset();
        }
    }
}
