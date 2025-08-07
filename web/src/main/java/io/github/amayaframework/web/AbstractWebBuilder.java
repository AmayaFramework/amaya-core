package io.github.amayaframework.web;

import io.github.amayaframework.application.AbstractApplicationBuilder;
import io.github.amayaframework.application.ServicesConfigurer;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.server.HttpServerFactory;

import java.util.Objects;

/**
 * Base builder implementation for {@link WebApplication} instances.
 * <p>
 * Manages configuration of services, environment, and the HTTP server factory.
 * Delegates actual application creation to subclass.
 */
public abstract class AbstractWebBuilder
        extends AbstractApplicationBuilder<WebApplication, WebApplicationConfigurer, WebApplicationBuilder>
        implements WebApplicationBuilder {
    /**
     * The default environment factory used if no other is configured.
     */
    protected final EnvironmentFactory defaultEnvironmentFactory;

    /**
     * The factory used to create the HTTP server instance.
     */
    protected HttpServerFactory serverFactory;

    /**
     * Creates a new builder instance with the given services configurer and default environment factory.
     *
     * @param configurer                the services configurer, must not be null
     * @param defaultEnvironmentFactory the default environment factory, must not be null
     */
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

    /**
     * Creates the {@link WebApplication} instance using the given options, environment, and HTTP server.
     * <p>
     * Subclasses must implement this method to provide the actual application creation logic.
     *
     * @param options     the group of configuration options
     * @param environment the environment instance
     * @param server      the HTTP server instance
     * @return a new {@link WebApplication} instance
     * @throws Throwable if any error occurs during creation
     */
    protected abstract WebApplication createApplication(GroupOptionSet options,
                                                        Environment environment,
                                                        HttpServer server) throws Throwable;

    @Override
    protected WebApplication createApplication(GroupOptionSet options, Environment environment) throws Throwable {
        var factory = Objects.requireNonNull(this.serverFactory, "No HttpServerFactory implementation found");
        var group = options.getGroup(WebOptions.SERVER_GROUP);
        var server = factory.create(group, environment);
        return createApplication(options, environment, server);
    }
}
