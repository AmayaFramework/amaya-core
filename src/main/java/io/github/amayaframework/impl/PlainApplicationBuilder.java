package io.github.amayaframework.impl;

import com.github.romanqed.jconv.PipelineBuilder;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.core.*;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.server.HttpServerFactory;

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class PlainApplicationBuilder implements ApplicationBuilder {
    private final OptionSetBuilder optionBuilder;
    private final ServiceManagerBuilder managerBuilder;
    private final PipelineBuilder<HttpContext> handlerBuilder;
    private final OptionSetBuilder wrappedOptionBuilder;
    private final ServiceManagerBuilder wrappedManagerBuilder;
    private final PipelineBuilder<HttpContext> wrappedHandlerBuilder;
    private EnvironmentFactory environmentFactory;
    private HttpServerFactory serverFactory;
    private String name;
    private List<HttpConfigConsumer> deferred;
    private List<InetSocketAddress> binds;

    public PlainApplicationBuilder(OptionSetBuilder optionBuilder,
                                   ServiceManagerBuilder managerBuilder,
                                   PipelineBuilder<HttpContext> handlerBuilder) {
        this.optionBuilder = Objects.requireNonNull(optionBuilder);
        this.managerBuilder = Objects.requireNonNull(managerBuilder);
        this.handlerBuilder = Objects.requireNonNull(handlerBuilder);
        this.wrappedOptionBuilder = new WrappedOptionSetBuilder(optionBuilder);
        this.wrappedManagerBuilder = new WrappedManagerBuilder(managerBuilder);
        this.wrappedHandlerBuilder = new WrappedPipelineBuilder<>(handlerBuilder);
        this.deferred = new LinkedList<>();
        this.binds = new LinkedList<>();
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
        this.environmentFactory = Objects.requireNonNull(factory);
        return this;
    }

    @Override
    public ApplicationBuilder setEnvironmentName(String name) {
        this.name = Objects.requireNonNull(name);
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
    public ServiceProviderBuilder getProviderBuilder() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ApplicationBuilder configure(ProviderBuilderConsumer action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ApplicationBuilder setServerFactory(HttpServerFactory factory) {
        this.serverFactory = Objects.requireNonNull(factory);
        return this;
    }

    @Override
    public ApplicationBuilder configure(HttpConfigConsumer action) {
        deferred.add(Objects.requireNonNull(action));
        return this;
    }

    @Override
    public ApplicationBuilder bind(InetSocketAddress address) {
        binds.add(Objects.requireNonNull(address));
        return this;
    }

    @Override
    public ApplicationBuilder bind(int port) {
        binds.add(new InetSocketAddress(port));
        return this;
    }

    private Application uncheckedBuild() throws Throwable {
        // Build option set
        var options = optionBuilder.build();
        // Create environment
        var environment = environmentFactory.create(name, options);
        // Build service manager
        var manager = managerBuilder.build();
        // Create http server
        var server = serverFactory.create(options);
        var config = server.getConfig();
        for (var handler : deferred) {
            handler.run(config);
        }
        for (var bind : binds) {
            config.addAddress(bind);
        }
        var handler = handlerBuilder.build();
        server.setHandler(handler);
        return new ApplicationImpl(options, environment, manager, server);
    }

    @Override
    public Application build() {
        try {
            return uncheckedBuild();
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            // reset
        }
    }
}
