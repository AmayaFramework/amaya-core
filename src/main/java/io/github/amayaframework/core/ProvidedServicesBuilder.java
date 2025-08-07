package io.github.amayaframework.core;

import io.github.amayaframework.application.ServiceManagerFactory;
import io.github.amayaframework.application.ServicesConfigurer;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.di.core.ServiceProvider;
import io.github.amayaframework.service.Service;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

final class ProvidedServicesBuilder extends AbstractServicesBuilder {
    private Supplier<ServiceProviderBuilder> supplier;
    private ServiceProviderBuilder builder;
    private Set<Type> provided;

    ProvidedServicesBuilder(ServiceManagerFactory defaultFactory) {
        super(defaultFactory);
    }

    void setSupplier(Supplier<ServiceProviderBuilder> supplier) {
        this.supplier = supplier;
    }

    void setBuilder(ServiceProviderBuilder builder) {
        this.builder = builder;
    }

    void provide(ServiceProvider provider) {
        if (provided == null) {
            return;
        }
        for (var type : provided) {
            services.add(provider.get(type));
        }
        provided = null;
    }

    @Override
    public void reset() {
        super.reset();
        if (builder != null && provided != null) {
            provided.forEach(builder::remove);
        }
        this.builder = null;
        this.provided = null;
    }

    @Override
    protected void removeService(Type type) {
        super.removeService(type);
        if (provided != null) {
            provided.remove(type);
            builder.remove(type);
        }
    }

    private void ensureBuilder() {
        if (builder == null) {
            builder = supplier.get();
        }
    }

    private void addProvided(Type type) {
        if (provided == null) {
            provided = new HashSet<>();
        }
        provided.add(type);
    }

    @Override
    public ServicesConfigurer register(Type type, Service service) {
        Objects.requireNonNull(service);
        ensureBuilder();
        builder.addInstance(type, service);
        addProvided(type);
        return this;
    }

    @Override
    public ServicesConfigurer register(Type type, Class<? extends Service> implementation) {
        ensureBuilder();
        builder.addSingleton(type, implementation);
        addProvided(type);
        return this;
    }

    @Override
    public <T extends Service> ServicesConfigurer register(Class<T> type, Class<? extends T> implementation) {
        ensureBuilder();
        builder.addSingleton(type, implementation);
        addProvided(type);
        return this;
    }

    @Override
    public ServicesConfigurer register(Class<? extends Service> implementation) {
        ensureBuilder();
        builder.addSingleton(implementation);
        addProvided(implementation);
        return this;
    }
}
