package io.github.amayaframework.application;

import io.github.amayaframework.service.Service;
import io.github.amayaframework.service.ServiceHandler;
import io.github.amayaframework.service.ServiceManager;
import io.github.amayaframework.service.ServiceManagerFactory;

import java.lang.reflect.Type;

/**
 * An interface describing the abstract builder of the {@link ServiceManager}.
 */
public interface ServiceManagerBuilder extends Resettable {

    /**
     * Sets the service manager factory.
     *
     * @param factory the {@link ServiceManagerFactory} instance, may be null
     * @return this {@link ServiceManagerBuilder} instance
     */
    ServiceManagerBuilder setFactory(ServiceManagerFactory factory);

    /**
     * Sets the service handler.
     *
     * @param handler the {@link ServiceHandler} instance
     * @return this {@link ServiceManagerBuilder} instance
     */
    ServiceManagerBuilder setHandler(ServiceHandler handler);

    /**
     * Adds a {@link Service} instance to service manager.
     * If amaya di module loaded, it will be registered as service instance at di container.
     *
     * @param service the {@link Service} instance to be managed
     * @return this {@link ServiceManagerBuilder} instance
     */
    ServiceManagerBuilder addService(Service service);

    /**
     * Adds a {@link Service} instance to service manager and register as server instance at di container.
     *
     * @param type    the specified service type, must be non-null
     * @param service the {@link Service} instance to be managed
     * @return this {@link ServiceManagerBuilder} instance
     * @throws UnsupportedOperationException if amaya di module not loaded
     */
    ServiceManagerBuilder addService(Type type, Service service);

    /**
     * Registers a singleton service by type at di container and adds service instance to service manager.
     *
     * @param type           the specified service type, must be non-null
     * @param implementation the specified service implementation class, must be non-null
     * @return this {@link ServiceManagerBuilder} instance
     * @throws UnsupportedOperationException if amaya di module not loaded
     */
    ServiceManagerBuilder addService(Type type, Class<? extends Service> implementation);

    /**
     * Registers a singleton service by type at di container and adds service instance to service manager.
     *
     * @param type           the specified service type, must be non-null
     * @param implementation the specified service implementation class, must be non-null
     * @param <T>            the type of the service
     * @return this {@link ServiceManagerBuilder} instance
     * @throws UnsupportedOperationException if amaya di module not loaded
     */
    <T extends Service> ServiceManagerBuilder addService(Class<T> type, Class<? extends T> implementation);

    /**
     * Registers a singleton service at di container and adds service instance to service manager.
     *
     * @param implementation the specified service implementation class, must be non-null
     * @return this {@link ServiceManagerBuilder} instance
     * @throws UnsupportedOperationException if amaya di module not loaded
     */
    ServiceManagerBuilder addService(Class<? extends Service> implementation);

    /**
     * Builds the {@link ServiceManager} instance with added services.
     *
     * @return the {@link ServiceManager} instance
     */
    ServiceManager build();
}
