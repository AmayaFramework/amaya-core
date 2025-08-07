package io.github.amayaframework.application;

import io.github.amayaframework.service.Service;
import io.github.amayaframework.service.ServiceManager;

import java.lang.reflect.Type;

/**
 * Configures services and service manager used by an {@link Application}.
 * <p>
 * Provides methods to add, remove, and register services, optionally
 * integrating with dependency injection if available.
 * </p>
 */
public interface ServicesConfigurer extends Resettable {

    /**
     * Sets the factory to create the {@link ServiceManager} instance.
     *
     * @param factory the service manager factory, must be non-null
     * @return this services configurer for chaining
     */
    ServicesConfigurer withManagerFactory(ServiceManagerFactory factory);

    /**
     * Adds a service to the service manager.
     *
     * @param service the service instance to add, must be non-null
     * @return this services configurer for chaining
     */
    ServicesConfigurer add(Service service);

    /**
     * Removes a service from the service manager.
     *
     * @param service the service instance to remove, must be non-null
     * @return this services configurer for chaining
     */
    ServicesConfigurer remove(Service service);

    /**
     * Registers a service instance for a specific type,
     * optionally adding it to the dependency injection container.
     *
     * @param type    the service type, must be non-null
     * @param service the service instance, must be non-null
     * @return this services configurer for chaining
     */
    ServicesConfigurer register(Type type, Service service);

    /**
     * Removes a service registration by service type.
     *
     * @param type the service type to remove, must be non-null
     * @return this services configurer for chaining
     */
    ServicesConfigurer remove(Type type);

    /**
     * Registers a service implementation class for a service type,
     * adding it to the dependency injection container if loaded.
     * <p>
     * Throws an exception immediately if the DI module is not loaded.
     * </p>
     *
     * @param type           the service type, must be non-null
     * @param implementation the implementation class, must be non-null
     * @return this services configurer for chaining
     * @throws IllegalStateException if DI module is not loaded
     */
    ServicesConfigurer register(Type type, Class<? extends Service> implementation);

    /**
     * Registers a service implementation class for a service type,
     * adding it to the dependency injection container if loaded.
     * <p>
     * Throws an exception immediately if the DI module is not loaded.
     * </p>
     *
     * @param <T>            the service type
     * @param type           the service type class, must be non-null
     * @param implementation the implementation class extending {@link Service}, must be non-null
     * @return this services configurer for chaining
     * @throws IllegalStateException if DI module is not loaded
     */
    <T extends Service> ServicesConfigurer register(Class<T> type, Class<? extends T> implementation);

    /**
     * Registers a service implementation class without specifying a service type,
     * adding it to the dependency injection container if loaded.
     * <p>
     * Throws an exception immediately if the DI module is not loaded.
     * </p>
     *
     * @param implementation the implementation class extending {@link Service}, must be non-null
     * @return this services configurer for chaining
     * @throws IllegalStateException if DI module is not loaded
     */
    ServicesConfigurer register(Class<? extends Service> implementation);
}
