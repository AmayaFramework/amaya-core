package io.github.amayaframework.core;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.application.ServiceManagerBuilder;
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

final class StandaloneApplicationBuilder extends AbstractWebBuilder {
    private final EnvironmentFactory defaultFactory;

    StandaloneApplicationBuilder(ServiceManagerBuilder managerBuilder, EnvironmentFactory defaultFactory) {
        super(managerBuilder);
        this.defaultFactory = defaultFactory;
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
                                               HttpServer server) {
        return new StandaloneApplication(options, environment, manager, server);
    }

    @Override
    public ServiceProviderBuilder getProviderBuilder() {
        throw new UnsupportedOperationException("The amaya di module is not loaded");
    }

    @Override
    public WebApplicationBuilder configureProviderBuilder(Runnable1<ServiceProviderBuilder> action) {
        // No-op impl
        return this;
    }

    @Override
    public WebApplicationBuilder configureProvider(Runnable1<ServiceProvider> action) {
        // No-op impl
        return this;
    }
}
