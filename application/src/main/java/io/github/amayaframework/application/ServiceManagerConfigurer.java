package io.github.amayaframework.application;

import io.github.amayaframework.service.Service;

import java.lang.reflect.Type;

public interface ServiceManagerConfigurer {

    ServiceManagerConfigurer setFactory(ServiceManagerFactory factory);

    /**
     * Adds a {@link Service} instance to service manager.
     * If amaya di module loaded, it will be registered as service instance at di container.
     *
     * @param service the {@link Service} instance to be managed
     * @return this {@link ServiceManagerConfigurer} instance
     */
    ServiceManagerConfigurer addService(Service service);

    /**
     * Adds a {@link Service} instance to service manager and register as server instance at di container.
     * If amaya di module loaded, it will be registered as service instance at di container.
     *
     * @param type    the specified service type, must be non-null
     * @param service the {@link Service} instance to be managed
     * @return this {@link ServiceManagerConfigurer} instance
     */
    ServiceManagerConfigurer addService(Type type, Service service);

    /**
     * Registers a singleton service by type at di container and adds service instance to service manager.
     *
     * @param type           the specified service type, must be non-null
     * @param implementation the specified service implementation class, must be non-null
     * @return this {@link ServiceManagerConfigurer} instance
     * @throws UnsupportedOperationException if amaya di module not loaded
     */
    ServiceManagerConfigurer addService(Type type, Class<? extends Service> implementation);

    /**
     * Registers a singleton service by type at di container and adds service instance to service manager.
     *
     * @param type           the specified service type, must be non-null
     * @param implementation the specified service implementation class, must be non-null
     * @param <T>            the type of the service
     * @return this {@link ServiceManagerConfigurer} instance
     * @throws UnsupportedOperationException if amaya di module not loaded
     */
    <T extends Service> ServiceManagerConfigurer addService(Class<T> type, Class<? extends T> implementation);

    /**
     * Registers a singleton service at di container and adds service instance to service manager.
     *
     * @param implementation the specified service implementation class, must be non-null
     * @return this {@link ServiceManagerConfigurer} instance
     * @throws UnsupportedOperationException if amaya di module not loaded
     */
    ServiceManagerConfigurer addService(Class<? extends Service> implementation);
}
