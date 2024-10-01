package io.github.amayaframework.core;

import com.github.romanqed.jconv.PipelineBuilder;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServerFactory;

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractApplicationBuilder implements ApplicationBuilder {
    // Builders
    protected final OptionSetBuilder optionBuilder;
    protected final ServiceManagerBuilder managerBuilder;
    protected final PipelineBuilder<HttpContext> handlerBuilder;
    // Wrapped builders
    protected final OptionSetBuilder wrappedOptionBuilder;
    protected final ServiceManagerBuilder wrappedManagerBuilder;
    protected final PipelineBuilder<HttpContext> wrappedHandlerBuilder;
    // Modifiable params
    protected EnvironmentFactory environmentFactory;
    protected String environmentName;
    protected HttpServerFactory serverFactory;
    // Http server binds
    protected List<InetSocketAddress> binds;
    // Deferred http config handlers
    protected List<HttpConfigConsumer> httpConsumers;

    protected AbstractApplicationBuilder(OptionSetBuilder optionBuilder,
                                         ServiceManagerBuilder managerBuilder,
                                         PipelineBuilder<HttpContext> handlerBuilder) {
        this.optionBuilder = optionBuilder;
        this.managerBuilder = managerBuilder;
        this.handlerBuilder = handlerBuilder;
        this.wrappedOptionBuilder = new WrappedOptionBuilder(optionBuilder);
        this.wrappedManagerBuilder = new WrappedManagerBuilder(managerBuilder);
        this.wrappedHandlerBuilder = new WrappedPipelineBuilder<>(handlerBuilder);
    }

    protected void innerReset() {
        optionBuilder.reset();
        managerBuilder.reset();
        handlerBuilder.clear();
        this.environmentFactory = null;
        this.environmentName = null;
        this.serverFactory = null;
        this.binds = null;
        this.httpConsumers = null;
    }

    @Override
    public OptionSetBuilder getOptionBuilder() {
        return wrappedOptionBuilder;
    }

    @Override
    public ApplicationBuilder configure(OptionBuilderConsumer action) {
        try {
            action.run(wrappedOptionBuilder);
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
    public ApplicationBuilder configure(ManagerBuilderConsumer action) {
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
    public PipelineBuilder<HttpContext> getHandlerBuilder() {
        return wrappedHandlerBuilder;
    }

    @Override
    public ApplicationBuilder configure(HandlerBuilderConsumer action) {
        try {
            action.run(wrappedHandlerBuilder);
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
    public ApplicationBuilder configure(HttpConfigConsumer action) {
        Objects.requireNonNull(action);
        if (httpConsumers == null) {
            httpConsumers = new LinkedList<>();
        }
        httpConsumers.add(action);
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
    public ApplicationBuilder reset() {
        innerReset();
        return this;
    }

    protected abstract String getDefaultName();
    protected abstract EnvironmentFactory getDefaultEnvironmentFactory();
    protected abstract HttpServerFactory getDefaultServerFactory();

    private Environment create(GroupOptionSet set) {
        var environmentFactory = Objects.requireNonNullElse(this.environmentFactory, getDefaultEnvironmentFactory());
        var name = Objects.requireNonNullElse(this.environmentName, getDefaultName());
        var options = set.getGroup(Options.ENVIRONMENT_OPTION_GROUP);
        if (options == null || options.asMap().isEmpty()) {
            return null;
        }
        return null;
    }

    protected Application checkedBuild() throws Throwable {
        // Build options
        var options = optionBuilder.build();
        // Create env
        return null;
    }

    @Override
    public Application build() {

        return null;
    }
}
