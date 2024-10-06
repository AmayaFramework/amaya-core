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

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public abstract class AbstractWebBuilder extends AbstractApplicationBuilder<WebApplication> implements WebApplicationBuilder {
    /**
     * Http server factory.
     */
    protected HttpServerFactory factory;
    /**
     * Http server binds.
     */
    protected List<InetSocketAddress> binds;

    /**
     *
     * @param managerBuilder
     */
    protected AbstractWebBuilder(ServiceManagerBuilder managerBuilder) {
        super(managerBuilder);
    }

    @Override
    public void reset() {
        this.factory = null;
        this.binds = null;
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

    @Override
    public WebApplicationBuilder bind(InetSocketAddress address) {
        Objects.requireNonNull(address);
        if (binds == null) {
            binds = new LinkedList<>();
        }
        binds.add(address);
        return this;
    }

    @Override
    public WebApplicationBuilder bind(int port) {
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("Illegal port value: " + port);
        }
        if (binds == null) {
            binds = new LinkedList<>();
        }
        binds.add(new InetSocketAddress(port));
        return this;
    }

    /**
     *
     * @return
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
     *
     * @param options
     * @param environment
     * @param manager
     * @param server
     * @return
     * @throws Throwable
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
        if (binds != null) {
            var config = server.getConfig();
            for (var bind : binds) {
                config.addAddress(bind);
            }
        }
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
