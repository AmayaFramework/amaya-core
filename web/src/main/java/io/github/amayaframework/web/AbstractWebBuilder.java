package io.github.amayaframework.web;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.application.AbstractApplicationBuilder;
import io.github.amayaframework.application.ServiceManagerBuilder;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.server.HttpServerFactory;
import io.github.amayaframework.service.ServiceManager;

import java.util.Objects;

/**
 * A class that provides a skeletal implementation of the {@link WebApplicationBuilder}.
 */
public abstract class AbstractWebBuilder
        extends AbstractApplicationBuilder<WebApplication>
        implements WebApplicationBuilder {
    /**
     * Http server factory.
     */
    protected HttpServerFactory factory;

    /**
     * Constructs an {@link AbstractWebBuilder} instance with given service manager builder.
     *
     * @param managerBuilder the specified {@link ServiceManagerBuilder} instance, must be non-null
     */
    protected AbstractWebBuilder(ServiceManagerBuilder managerBuilder) {
        super(managerBuilder);
    }

    @Override
    public void reset() {
        this.factory = null;
        super.reset();
    }

    @Override
    public WebApplicationBuilder configure(Runnable1<WebApplicationBuilder> handler) {
        Objects.requireNonNull(handler);
        try {
            handler.run(this);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public WebApplicationBuilder setServerFactory(HttpServerFactory factory) {
        this.factory = factory;
        return this;
    }

    /**
     * Creates default environment factory.
     *
     * @return the {@link EnvironmentFactory} instance
     */
    protected abstract EnvironmentFactory getDefaultEnvironmentFactory();

    @Override
    protected Environment createEnvironment(GroupOptionSet set) throws Throwable {
        var factory = environmentFactory == null ? getDefaultEnvironmentFactory() : environmentFactory;
        var name = Objects.requireNonNullElse(environmentName, WebOptions.DEFAULT_ENVIRONMENT_NAME);
        var group = set.getGroup(WebOptions.ENVIRONMENT_GROUP);
        return factory.create(name, group);
    }

    /**
     * Creates application with given options, environment, service manager and server.
     *
     * @param options     the specified {@link GroupOptionSet} instance
     * @param environment the specified {@link Environment} instance
     * @param manager     the specified {@link ServiceManager} instance
     * @param server      the specified {@link HttpServer} instance
     * @return the {@link WebApplication} instance
     * @throws Throwable if any problems during application initialization occurred
     */
    protected abstract WebApplication createApplication(GroupOptionSet options,
                                                        Environment environment,
                                                        ServiceManager manager,
                                                        HttpServer server) throws Throwable;

    @Override
    protected WebApplication createApplication(GroupOptionSet options,
                                               Environment environment,
                                               ServiceManager manager) throws Throwable {
        var factory = Objects.requireNonNull(this.factory);
        var group = options.getGroup(WebOptions.SERVER_GROUP);
        var server = factory.create(group);
        return createApplication(options, environment, manager, server);
    }

    @Override
    public WebApplicationBuilder setOptions(GroupOptionSet options) {
        super.setOptions(options);
        return this;
    }

    @Override
    public WebApplicationBuilder configureOptions(Runnable1<GroupOptionSet> action) {
        super.configureOptions(action);
        return this;
    }

    @Override
    public WebApplicationBuilder setEnvironmentFactory(EnvironmentFactory factory) {
        super.setEnvironmentFactory(factory);
        return this;
    }

    @Override
    public WebApplicationBuilder setEnvironmentName(String name) {
        super.setEnvironmentName(name);
        return this;
    }

    @Override
    public WebApplicationBuilder configureManager(Runnable1<ServiceManagerBuilder> action) {
        super.configureManager(action);
        return this;
    }

    @Override
    public WebApplicationBuilder configureApplication(Runnable1<WebApplication> action) {
        super.configureApplication(action);
        return this;
    }
}
