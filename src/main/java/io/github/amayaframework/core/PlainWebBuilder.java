package io.github.amayaframework.core;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.di.ScopedProviderConfigurer;
import io.github.amayaframework.di.core.ServiceProvider;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.web.WebApplication;
import io.github.amayaframework.web.WebApplicationBuilder;
import io.github.amayaframework.web.WebOptions;
import org.slf4j.ILoggerFactory;

final class PlainWebBuilder extends CommonWebBuilder {
    private final PlainServicesBuilder servicesBuilder;

    PlainWebBuilder(PlainServicesBuilder servicesBuilder,
                    EnvironmentFactory defaultEnvironmentFactory,
                    ILoggerFactory loggerFactory) {
        super(servicesBuilder, defaultEnvironmentFactory, loggerFactory);
        this.servicesBuilder = servicesBuilder;
    }

    @Override
    public ScopedProviderConfigurer providerBuilder() {
        // Amaya DI module not loaded, so return null
        return null;
    }

    @Override
    public WebApplicationBuilder configureProviderBuilder(Runnable1<ScopedProviderConfigurer> action) {
        // Amaya DI module not loaded, do nothing
        return this;
    }

    @Override
    public WebApplicationBuilder configureProvider(Runnable1<ServiceProvider> action) {
        // Amaya DI module not loaded, do nothing
        return this;
    }

    @Override
    protected WebApplication createApplication(GroupOptionSet options, Environment environment, HttpServer server) {
        var managerOptions = options.getGroup(WebOptions.MANAGER_GROUP);
        var manager = servicesBuilder.build(managerOptions, environment);
        return createApplication(options, environment, manager, null, server);
    }
}
