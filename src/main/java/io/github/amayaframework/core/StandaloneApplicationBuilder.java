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

final class StandaloneApplicationBuilder extends AbstractApplicationBuilder {
    private final EnvironmentFactory defaultFactory;

    StandaloneApplicationBuilder(ServiceManagerBuilder managerBuilder, EnvironmentFactory defaultFactory) {
        super(managerBuilder);
        this.defaultFactory = defaultFactory;
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
        return new StandaloneApplication(options, environment, manager, server);
    }

    @Override
    public ServiceProviderBuilder getProviderBuilder() {
        throw new UnsupportedOperationException("The amaya-di module is not loaded");
    }

    @Override
    public ApplicationBuilder configureProviderBuilder(Runnable1<ServiceProviderBuilder> action) {
        throw new UnsupportedOperationException("The amaya-di module is not loaded");
    }

    @Override
    public ApplicationBuilder configureProvider(Runnable1<ServiceProvider> action) {
        throw new UnsupportedOperationException("The amaya-di module is not loaded");
    }
}
