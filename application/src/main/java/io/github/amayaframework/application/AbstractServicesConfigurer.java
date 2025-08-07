package io.github.amayaframework.application;

import io.github.amayaframework.service.Service;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Base implementation of the {@link ServicesConfigurer} interface,
 * providing core management of services and service manager factory.
 * <p>
 * Manages a set of registered services and a factory to create the service manager.
 * Designed to be extended for specific behavior around service lifecycle and configuration.
 */
public abstract class AbstractServicesConfigurer implements ServicesConfigurer {

    /**
     * Factory used to create the {@link io.github.amayaframework.service.ServiceManager}.
     */
    protected ServiceManagerFactory factory;

    /**
     * Set of currently registered services.
     */
    protected Set<Service> services;

    /**
     * Resets this configurer to its initial state,
     * clearing all services and removing the factory reference.
     */
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

    /**
     * Adds a service instance to the internal registry.
     * Initializes the services set if it was not created yet.
     *
     * @param service the service to add; must not be {@code null}
     */
    protected void addService(Service service) {
        if (services == null) {
            services = new HashSet<>();
        }
        services.add(service);
    }

    /**
     * Removes a service instance from the internal registry if present.
     *
     * @param service the service instance to remove
     */
    protected void removeService(Service service) {
        if (services != null) {
            services.remove(service);
        }
    }

    /**
     * Removes all registered services matching the specified type.
     * Subclasses must implement the logic to handle type-based removal properly.
     *
     * @param type the class type of the service(s) to remove
     */
    protected void removeService(Type type) {
        services.removeIf(service -> service.getClass() == type);
    }

    @Override
    public ServicesConfigurer add(Service service) {
        addService(Objects.requireNonNull(service));
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
        }
        return this;
    }
}
