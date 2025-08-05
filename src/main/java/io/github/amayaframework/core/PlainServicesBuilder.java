package io.github.amayaframework.core;

import io.github.amayaframework.application.ServiceManagerFactory;
import io.github.amayaframework.application.ServicesConfigurer;
import io.github.amayaframework.service.Service;

import java.lang.reflect.Type;
import java.util.Objects;

public final class PlainServicesBuilder extends AbstractServicesBuilder {

    public PlainServicesBuilder(ServiceManagerFactory defaultFactory) {
        super(defaultFactory);
    }

    @Override
    protected void removeService(Type type) {
        // Do nothing, no di
    }

    @Override
    public ServicesConfigurer register(Type type, Service service) {
        // Simple register in the service set
        addService(Objects.requireNonNull(service));
        return this;
    }

    @Override
    public ServicesConfigurer register(Type type, Class<? extends Service> implementation) {
        throw new UnsupportedOperationException("The amaya di module is not loaded");
    }

    @Override
    public <T extends Service> ServicesConfigurer register(Class<T> type, Class<? extends T> implementation) {
        throw new UnsupportedOperationException("The amaya di module is not loaded");
    }

    @Override
    public ServicesConfigurer register(Class<? extends Service> implementation) {
        throw new UnsupportedOperationException("The amaya di module is not loaded");
    }
}
