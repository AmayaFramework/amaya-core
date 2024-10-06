package io.github.amayaframework.core;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.options.OpenOptionSet;
import io.github.amayaframework.options.ProvidedGroupSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.service.ServiceManager;
import io.github.amayaframework.web.AbstractWebBuilder;
import io.github.amayaframework.web.WebApplication;
import io.github.amayaframework.web.WebApplicationBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

final class ProvidedApplicationBuilder extends AbstractWebBuilder {
    private final ProvidedManagerBuilder providedBuilder;
    private final EnvironmentFactory defaultFactory;
    private final Supplier<ServiceProviderBuilder> supplier;
    private ServiceProviderBuilder builder;
    private List<Runnable1<ServiceProvider>> providerConsumers;

    ProvidedApplicationBuilder(ProvidedManagerBuilder managerBuilder,
                               Supplier<ServiceProviderBuilder> supplier,
                               EnvironmentFactory defaultFactory) {
        super(managerBuilder);
        this.providedBuilder = managerBuilder;
        this.defaultFactory = defaultFactory;
        this.supplier = supplier;
        this.builder = supplier.get();
        this.providedBuilder.setProviderBuilder(this.builder);
    }

    @Override
    public void reset() {
        builder = supplier.get();
        providedBuilder.setProviderBuilder(builder);
        providerConsumers = null;
        super.reset();
    }

    @Override
    protected EnvironmentFactory getDefaultEnvironmentFactory() {
        return defaultFactory;
    }

    @Override
    protected GroupOptionSet createDefaultOptions() {
        return new ProvidedGroupSet(".", "", OpenOptionSet::new);
    }

    @Override
    protected WebApplication createApplication(GroupOptionSet options,
                                               Environment environment,
                                               ServiceManager manager,
                                               HttpServer server) throws Throwable {
        // Add options and environment to container
        builder.addInstance(GroupOptionSet.class, options);
        builder.addInstance(Environment.class, environment);
        // Build di container
        var provider = builder.build();
        // Add delayed services
        var types = providedBuilder.getProvidedServices();
        for (var type : types) {
            manager.add(provider.get(type));
        }
        // Fire delayed actions
        if (providerConsumers != null) {
            for (var consumer : providerConsumers) {
                consumer.run(provider);
            }
        }
        return new ProvidedApplication(options, environment, manager, server, provider);
    }

    @Override
    public ServiceProviderBuilder getProviderBuilder() {
        return builder;
    }

    @Override
    public WebApplicationBuilder configureProviderBuilder(Runnable1<ServiceProviderBuilder> action) {
        try {
            action.run(builder);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
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
}
