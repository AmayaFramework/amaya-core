package io.github.amayaframework.application;

import io.github.amayaframework.service.Service;

import java.lang.reflect.Type;

public interface ServicesConfigurer extends Resettable {

    ServicesConfigurer withManagerFactory(ServiceManagerFactory factory);

    ServicesConfigurer add(Service service); // plain add to service manager

    ServicesConfigurer remove(Service service); // remove by service itself

    ServicesConfigurer register(Type type, Service service); // add + add to di (if loaded)

    ServicesConfigurer remove(Type type); // remove by type

    // add to di + add to SM after di build (exception if di not loaded)

    ServicesConfigurer register(Type type, Class<? extends Service> implementation);

    <T extends Service> ServicesConfigurer register(Class<T> type, Class<? extends T> implementation);

    ServicesConfigurer register(Class<? extends Service> implementation);
}
