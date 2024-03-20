package io.github.amayaframework.core.builder;

import com.github.romanqed.jconv.PipelineBuilder;
import io.github.amayaframework.core.Application;
import io.github.amayaframework.core.Environment;
import io.github.amayaframework.core.context.ApplicationContext;
import io.github.amayaframework.core.service.ServiceCollection;
import io.github.amayaframework.di.ManualProviderBuilder;

public interface ApplicationBuilder {

    Environment getEnvironment();

    ServiceCollection getServiceCollection();

    PipelineBuilder<ApplicationContext> getPipelineBuilder();

    ManualProviderBuilder getProviderBuilder();

    ApplicationBuilder configure(ServiceCollectionConfigurator configurator);

    ApplicationBuilder configure(PipelineConfigurator configurator);

    ApplicationBuilder configure(ServiceProviderConfigurator configurator);

    ApplicationBuilder configure(ApplicationBuilderConfigurator configurator);

    Application build();
}
