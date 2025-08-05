package io.github.amayaframework.application;

import io.github.amayaframework.service.Service;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractServicesConfigurer implements ServicesConfigurer {
    protected ServiceManagerFactory factory;
    protected Set<Service> services;

    @Override
    public void reset() {
        this.factory = null;
        this.services = null;
    }

    @Override
    public ServicesConfigurer withManagerFactory(ServiceManagerFactory factory) {
        this.factory = factory;
        return this;
    }

    protected void addService(Service service) {
        if (services == null) {
            services = new HashSet<>();
        }
        services.add(service);
    }

    protected void removeService(Service service) {
        if (services != null) {
            services.remove(service);
        }
    }

    protected abstract void removeService(Type type);

    @Override
    public ServicesConfigurer add(Service service) {
        Objects.requireNonNull(service);
        addService(service);
        return this;
    }

    @Override
    public ServicesConfigurer remove(Service service) {
        removeService(service);
        return this;
    }

    @Override
    public ServicesConfigurer remove(Type type) {
        if (services != null) {
            removeService(type);
            services.removeIf(service -> service.getClass() == type);
        }
        return this;
    }
}
