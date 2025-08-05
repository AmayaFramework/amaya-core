package io.github.amayaframework.core;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.di.ScopedProviderBuilder;
import io.github.amayaframework.di.core.ServiceProvider;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.web.WebApplication;
import io.github.amayaframework.web.WebApplicationBuilder;
import org.slf4j.ILoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

final class ProvidedWebBuilder extends CommonWebBuilder {
    private final ProvidedServicesBuilder servicesBuilder;
    private final Supplier<ScopedProviderBuilder> supplier;
    private ScopedProviderBuilder builder;
    private List<Runnable1<ServiceProvider>> providerConsumers;

    ProvidedWebBuilder(ProvidedServicesBuilder servicesBuilder,
                       EnvironmentFactory defaultEnvironmentFactory,
                       Supplier<ScopedProviderBuilder> supplier,
                       ILoggerFactory loggerFactory) {
        super(servicesBuilder, defaultEnvironmentFactory, loggerFactory);
        this.servicesBuilder = servicesBuilder;
        this.supplier = supplier;
        this.servicesBuilder.setSupplier(this::ensureBuilder);
    }

    @Override
    public void reset() {
        super.reset();
        builder = null;
        providerConsumers = null;
    }

    private ScopedProviderBuilder ensureBuilder() {
        if (builder == null) {
            builder = supplier.get();
        }
        return builder;
    }

    @Override
    public ScopedProviderBuilder providerBuilder() {
        return ensureBuilder();
    }

    @Override
    public WebApplicationBuilder configureProviderBuilder(Runnable1<ScopedProviderBuilder> action) {
        Objects.requireNonNull(action);
        var builder = ensureBuilder();
        try {
            action.run(builder);
        } catch (Throwable e) {
            Exceptions.throwAny(e);
        }
        return this;
    }

    @Override
    public WebApplicationBuilder configureProvider(Runnable1<ServiceProvider> action) {
        Objects.requireNonNull(action);
        if (providerConsumers == null) {
            providerConsumers = new LinkedList<>();
        }
        providerConsumers.add(action);
        return this;
    }

    @Override
    protected WebApplication createApplication(GroupOptionSet options,
                                               Environment environment,
                                               HttpServer server) throws Throwable {
        // Add options and environment to container
        var builder = ensureBuilder();
        builder.addInstance(GroupOptionSet.class, options);
        builder.addInstance(Environment.class, environment);
        // Build provider
        var provider = builder.build();
        // Build service manager
        servicesBuilder.provide(provider);
        var managerOptions = options.getGroup("TODO: Manager options");
        var manager = servicesBuilder.build(managerOptions, environment);
        // Fire delayed provider consumers
        if (providerConsumers != null) {
            for (var consumer : providerConsumers) {
                consumer.run(provider);
            }
        }
        // Create application
        return createApplication(options, environment, manager, provider, server);
    }
}
