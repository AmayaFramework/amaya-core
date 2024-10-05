package io.github.amayaframework.core;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.options.OpenOptionSet;
import io.github.amayaframework.options.OptionSet;
import io.github.amayaframework.options.ProvidedGroupSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.service.ServiceManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

final class ProvidedApplicationBuilder extends AbstractApplicationBuilder {
    private final ProvidedManagerBuilder providedBuilder;
    private final EnvironmentFactory defaultFactory;
    private final Supplier<ServiceProviderBuilder> supplier;
    private ServiceProviderBuilder builder;
    private List<Runnable1<ServiceProvider>> providerConsumers;
    private ServiceProvider provider;

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
    protected void innerReset() {
        builder = supplier.get();
        providedBuilder.setProviderBuilder(builder);
        providerConsumers = null;
        provider = null;
        super.innerReset();
    }

    @Override
    protected String getDefaultName() {
        return CoreOptions.DEFAULT_ENVIRONMENT_NAME;
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
    protected Application createApplication(GroupOptionSet options,
                                            Environment environment,
                                            ServiceManager manager,
                                            HttpServer server) {
        // Add delayed services
        var types = providedBuilder.getProvidedServices();
        for (var type : types) {
            manager.add(provider.get(type));
        }
        return new ProvidedApplication(options, environment, manager, server, provider);
    }

    @Override
    public ServiceProviderBuilder getProviderBuilder() {
        return builder;
    }

    @Override
    public ApplicationBuilder configureProviderBuilder(Runnable1<ServiceProviderBuilder> action) {
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
    public ApplicationBuilder configureProvider(Runnable1<ServiceProvider> action) {
        Objects.requireNonNull(action);
        if (providerConsumers == null) {
            providerConsumers = new LinkedList<>();
        }
        providerConsumers.add(action);
        return this;
    }

    @Override
    protected HttpServer createServer(OptionSet set) throws Throwable {
        // Build di container
        provider = builder.build();
        // Fire delayed actions
        if (providerConsumers != null) {
            for (var consumer : providerConsumers) {
                consumer.run(provider);
            }
        }
        return super.createServer(set);
    }
}
