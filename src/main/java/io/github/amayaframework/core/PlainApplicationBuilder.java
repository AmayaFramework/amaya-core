package io.github.amayaframework.core;

import com.github.romanqed.jconv.PipelineBuilder;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.options.OpenOptionSet;
import io.github.amayaframework.options.ProvidedGroupSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.service.ServiceManager;

final class PlainApplicationBuilder extends AbstractApplicationBuilder {
    private final EnvironmentFactory defaultFactory;

    PlainApplicationBuilder(ServiceManagerBuilder managerBuilder,
                            PipelineBuilder<HttpContext> handlerBuilder,
                            EnvironmentFactory defaultFactory) {
        super(managerBuilder, handlerBuilder);
        this.defaultFactory = defaultFactory;
    }

    @Override
    protected String getDefaultName() {
        return Defaults.DEFAULT_ENVIRONMENT_NAME;
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
        return new PlainApplication(options, environment, manager, server);
    }

    @Override
    public ServiceProviderBuilder getProviderBuilder() {
        throw new UnsupportedOperationException("The amaya-di module is not loaded");
    }

    @Override
    public ApplicationBuilder configure(ProviderBuilderConsumer action) {
        throw new UnsupportedOperationException("The amaya-di module is not loaded");
    }

    @Override
    public ApplicationBuilder configure(ProviderConsumer action) {
        throw new UnsupportedOperationException("The amaya-di module is not loaded");
    }
}
