package io.github.amayaframework.core;

import io.github.amayaframework.service.Service;
import io.github.amayaframework.service.ServiceHandler;
import io.github.amayaframework.service.ServiceManager;
import io.github.amayaframework.service.ServiceManagerFactory;

import java.lang.reflect.Type;

public interface ServiceManagerBuilder extends Resettable<ServiceManagerBuilder> {

    ServiceManagerBuilder setFactory(ServiceManagerFactory factory);

    ServiceManagerBuilder setHandler(ServiceHandler handler);

    ServiceManagerBuilder addService(Service service);

    ServiceManagerBuilder addService(Type type, Service service);

    ServiceManagerBuilder addService(Type type, Class<? extends Service> implementation);

    ServiceManagerBuilder addService(Class<? extends Service> implementation);

    <T extends Service> ServiceManagerBuilder addService(Class<T> type, Class<? extends T> implementation);

    ServiceManager build();
}
