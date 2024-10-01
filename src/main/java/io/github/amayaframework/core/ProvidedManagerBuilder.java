package io.github.amayaframework.core;

import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.service.Service;
import io.github.amayaframework.service.ServiceHandler;
import io.github.amayaframework.service.ServiceManagerFactory;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

final class ProvidedManagerBuilder extends AbstractManagerBuilder {
    private final ServiceManagerFactory defaultFactory;
    private final ServiceHandler defaultHandler;
    private ServiceProviderBuilder builder;
    private Set<Type> provided;

    ProvidedManagerBuilder(ServiceManagerFactory defaultFactory, ServiceHandler defaultHandler) {
        this.defaultFactory = defaultFactory;
        this.defaultHandler = defaultHandler;
        this.provided = new HashSet<>();
    }

    @Override
    protected void innerReset() {
        this.builder = null;
        this.provided = new HashSet<>();
        super.innerReset();
    }

    Set<Type> getProvidedServices() {
        return provided;
    }

    void setProviderBuilder(ServiceProviderBuilder builder) {
        this.builder = builder;
    }

    @Override
    protected ServiceManagerFactory getDefaultFactory() {
        return defaultFactory;
    }

    @Override
    protected ServiceHandler getDefaultHandler() {
        return defaultHandler;
    }

    @Override
    public ServiceManagerBuilder addService(Service service) {
        Objects.requireNonNull(service);
        builder.addInstance(service);
        services.add(service);
        return this;
    }

    @Override
    public ServiceManagerBuilder addService(Type type, Service service) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(service);
        builder.addInstance(type, service);
        services.add(service);
        return this;
    }

    @Override
    public ServiceManagerBuilder addService(Type type, Class<? extends Service> implementation) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(implementation);
        provided.add(type);
        builder.addSingleton(type, implementation);
        return this;
    }

    @Override
    public ServiceManagerBuilder addService(Class<? extends Service> implementation) {
        Objects.requireNonNull(implementation);
        provided.add(implementation);
        builder.addSingleton(implementation);
        return this;
    }

    @Override
    public <T extends Service> ServiceManagerBuilder addService(Class<T> type, Class<? extends T> implementation) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(implementation);
        provided.add(type);
        builder.addSingleton(type, implementation);
        return this;
    }
}
