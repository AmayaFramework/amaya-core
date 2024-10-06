package io.github.amayaframework.core;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.options.OptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.server.HttpServerFactory;
import io.github.amayaframework.service.ServiceManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractApplicationBuilder implements ApplicationBuilder {
    // Builders
    protected final ServiceManagerBuilder managerBuilder;
    // Wrapped builders
    protected final ServiceManagerBuilder wrappedManagerBuilder;
    // Modifiable params
    protected EnvironmentFactory environmentFactory;
    protected String environmentName;
    protected HttpServerFactory serverFactory;
    protected GroupOptionSet options;
    // Http server binds
    protected List<InetSocketAddress> binds;
    // Deferred application consumers
    protected List<Runnable1<Application>> consumers;

    protected AbstractApplicationBuilder(ServiceManagerBuilder managerBuilder) {
        this.managerBuilder = managerBuilder;
        this.wrappedManagerBuilder = new WrappedManagerBuilder(managerBuilder);
    }

    protected void innerReset() {
        managerBuilder.reset();
        this.environmentFactory = null;
        this.environmentName = null;
        this.serverFactory = null;
        this.binds = null;
        this.consumers = null;
    }

    @Override
    public ApplicationBuilder configure(Runnable1<ApplicationBuilder> action) {
        try {
            action.run(this);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public GroupOptionSet getOptions() {
        return options;
    }

    @Override
    public ApplicationBuilder setOptions(GroupOptionSet options) {
        this.options = options;
        return this;
    }

    @Override
    public ApplicationBuilder configureOptions(Runnable1<GroupOptionSet> action) {
        try {
            action.run(options);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public ApplicationBuilder setEnvironmentFactory(EnvironmentFactory factory) {
        this.environmentFactory = factory;
        return this;
    }

    @Override
    public ApplicationBuilder setEnvironmentName(String name) {
        this.environmentName = name;
        return this;
    }

    @Override
    public ServiceManagerBuilder getManagerBuilder() {
        return wrappedManagerBuilder;
    }

    @Override
    public ApplicationBuilder configureManager(Runnable1<ServiceManagerBuilder> action) {
        try {
            action.run(wrappedManagerBuilder);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public ApplicationBuilder setServerFactory(HttpServerFactory factory) {
        this.serverFactory = factory;
        return this;
    }

    @Override
    public ApplicationBuilder configureApplication(Runnable1<Application> action) {
        Objects.requireNonNull(action);
        if (consumers == null) {
            consumers = new LinkedList<>();
        }
        consumers.add(action);
        return this;
    }

    @Override
    public ApplicationBuilder bind(InetSocketAddress address) {
        Objects.requireNonNull(address);
        if (binds == null) {
            binds = new LinkedList<>();
        }
        binds.add(address);
        return this;
    }

    @Override
    public ApplicationBuilder bind(int port) {
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("Illegal port value: " + port);
        }
        if (binds == null) {
            binds = new LinkedList<>();
        }
        binds.add(new InetSocketAddress(port));
        return this;
    }

    @Override
    public void reset() {
        innerReset();
    }

    protected abstract String getDefaultName();

    protected abstract EnvironmentFactory getDefaultEnvironmentFactory();

    private Environment createEnvironment(OptionSet set) throws IOException {
        var factory = Objects.requireNonNullElse(environmentFactory, getDefaultEnvironmentFactory());
        var name = Objects.requireNonNullElse(environmentName, getDefaultName());
        return factory.create(name, set);
    }

    protected abstract GroupOptionSet createDefaultOptions();

    protected abstract Application createApplication(GroupOptionSet options,
                                                     Environment environment,
                                                     ServiceManager manager,
                                                     HttpServer server) throws Throwable;

    protected HttpServer createServer(OptionSet set) throws Throwable {
        var factory = Objects.requireNonNull(serverFactory);
        var ret = factory.create(set);
        var config = ret.getConfig();
        if (binds != null) {
            for (var bind : binds) {
                config.addAddress(bind);
            }
        }
        return ret;
    }

    protected Application checkedBuild() throws Throwable {
        // Prepare options
        var set = Objects.requireNonNullElse(options, createDefaultOptions());
        // Prepare environment
        var environment = createEnvironment(set.getGroup(CoreOptions.ENVIRONMENT_GROUP));
        // Prepare service manager
        var manager = managerBuilder.build();
        // Prepare http server
        var server = createServer(set.getGroup(CoreOptions.SERVER_GROUP));
        // Prepare application
        var ret = createApplication(set, environment, manager, server);
        if (consumers != null) {
            for (var consumer : consumers) {
                consumer.run(ret);
            }
        }
        return ret;
    }

    @Override
    public Application build() {
        try {
            return checkedBuild();
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            innerReset();
        }
    }
}
