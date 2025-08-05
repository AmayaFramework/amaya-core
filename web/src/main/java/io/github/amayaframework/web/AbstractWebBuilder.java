package io.github.amayaframework.web;

import io.github.amayaframework.application.AbstractApplicationBuilder;
import io.github.amayaframework.application.ServicesConfigurer;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.server.HttpServerFactory;

import java.util.Objects;

public abstract class AbstractWebBuilder
        extends AbstractApplicationBuilder<WebApplication, WebApplicationConfigurer, WebApplicationBuilder>
        implements WebApplicationBuilder {
    protected final EnvironmentFactory defaultEnvironmentFactory;
    protected HttpServerFactory serverFactory;

    protected AbstractWebBuilder(ServicesConfigurer configurer, EnvironmentFactory defaultEnvironmentFactory) {
        super(configurer);
        this.defaultEnvironmentFactory = defaultEnvironmentFactory;
    }

    @Override
    public void reset() {
        super.reset();
        serverFactory = null;
    }

    @Override
    public WebApplicationBuilder withServerFactory(HttpServerFactory factory) {
        this.serverFactory = factory;
        return this;
    }

    @Override
    protected Environment createEnvironment(GroupOptionSet set) throws Throwable {
        var factory = environmentFactory == null ? defaultEnvironmentFactory : environmentFactory;
        var name = environmentName == null ? WebOptions.DEFAULT_ENVIRONMENT_NAME : environmentName;
        var group = set.getGroup(WebOptions.ENVIRONMENT_GROUP);
        return factory.create(name, group);
    }

    protected abstract WebApplication createApplication(GroupOptionSet options,
                                                        Environment environment,
                                                        HttpServer server) throws Throwable;

    @Override
    protected WebApplication createApplication(GroupOptionSet options, Environment environment) throws Throwable {
        var factory = Objects.requireNonNull(this.serverFactory, "TODO: Missing server factory msg");
        var group = options.getGroup(WebOptions.SERVER_GROUP);
        var server = factory.create(group, environment);
        return createApplication(options, environment, server);
    }
}
