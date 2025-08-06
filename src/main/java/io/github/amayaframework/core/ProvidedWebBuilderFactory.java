package io.github.amayaframework.core;

import io.github.amayaframework.di.ScopedProviderBuilder;
import io.github.amayaframework.environment.NativeEnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.service.Service;
import io.github.amayaframework.web.WebApplicationBuilder;
import org.slf4j.ILoggerFactory;

import java.util.Map;
import java.util.function.Supplier;

public final class ProvidedWebBuilderFactory implements WebBuilderFactory {
    private final Supplier<Map<Service, ?>> mapSupplier;
    private final Supplier<ScopedProviderBuilder> builderSupplier;
    private final ILoggerFactory loggerFactory;

    public ProvidedWebBuilderFactory(Supplier<Map<Service, ?>> mapSupplier,
                              Supplier<ScopedProviderBuilder> builderSupplier,
                              ILoggerFactory loggerFactory) {
        this.mapSupplier = mapSupplier;
        this.loggerFactory = loggerFactory;
        this.builderSupplier = builderSupplier;
    }

    @Override
    public WebApplicationBuilder create(GroupOptionSet options) {
        var ret = create();
        ret.options(options);
        return ret;
    }

    @Override
    public WebApplicationBuilder create() {
        var managerFactory = new PlainManagerFactory(mapSupplier, loggerFactory);
        var envFactory = new NativeEnvironmentFactory();
        var servicesBuilder = new ProvidedServicesBuilder(managerFactory);
        return new ProvidedWebBuilder(servicesBuilder, envFactory, builderSupplier, loggerFactory);
    }
}
