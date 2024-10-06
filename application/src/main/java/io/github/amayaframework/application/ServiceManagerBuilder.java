package io.github.amayaframework.application;

import io.github.amayaframework.service.Service;
import io.github.amayaframework.service.ServiceHandler;
import io.github.amayaframework.service.ServiceManager;
import io.github.amayaframework.service.ServiceManagerFactory;

import java.lang.reflect.Type;

/**
 *
 */
public interface ServiceManagerBuilder extends Resettable {

    /**
     *
     * @param factory
     * @return
     */
    ServiceManagerBuilder setFactory(ServiceManagerFactory factory);

    /**
     *
     * @param handler
     * @return
     */
    ServiceManagerBuilder setHandler(ServiceHandler handler);

    /**
     *
     * @param service
     * @return
     */
    ServiceManagerBuilder addService(Service service);

    /**
     *
     * @param type
     * @param service
     * @return
     */
    ServiceManagerBuilder addService(Type type, Service service);

    /**
     *
     * @param type
     * @param implementation
     * @return
     */
    ServiceManagerBuilder addService(Type type, Class<? extends Service> implementation);

    /**
     *
     * @param implementation
     * @return
     */
    ServiceManagerBuilder addService(Class<? extends Service> implementation);

    /**
     *
     * @param type
     * @param implementation
     * @return
     * @param <T>
     */
    <T extends Service> ServiceManagerBuilder addService(Class<T> type, Class<? extends T> implementation);

    /**
     *
     * @return
     */
    ServiceManager build();
}
