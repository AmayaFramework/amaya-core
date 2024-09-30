package io.github.amayaframework.core;

import com.github.romanqed.jconv.PipelineBuilder;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.server.HttpServerFactory;

import java.net.InetSocketAddress;

public interface ApplicationBuilder {

    OptionSetBuilder getOptionBuilder();

    ApplicationBuilder configure(OptionBuilderConsumer action);

    ApplicationBuilder setEnvironmentFactory(EnvironmentFactory factory);

    ApplicationBuilder setEnvironmentName(String name);

    ServiceManagerBuilder getManagerBuilder();

    ApplicationBuilder configure(ManagerBuilderConsumer action);

    PipelineBuilder<HttpContext> getHandlerBuilder();

    ApplicationBuilder configure(HandlerBuilderConsumer action);

    ServiceProviderBuilder getProviderBuilder();

    ApplicationBuilder configure(ProviderBuilderConsumer action);

    ApplicationBuilder setServerFactory(HttpServerFactory factory);

    ApplicationBuilder configure(HttpConfigConsumer action);

    ApplicationBuilder bind(InetSocketAddress address);

    ApplicationBuilder bind(int port);

    Application build();
}
