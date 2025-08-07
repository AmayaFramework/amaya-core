package io.github.amayaframework.web;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.application.ApplicationBuilder;
import io.github.amayaframework.application.ServicesConfigurer;
import io.github.amayaframework.di.ScopedProviderBuilder;
import io.github.amayaframework.di.core.ServiceProvider;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServerFactory;

/**
 * Builder interface for creating a {@link WebApplication} instance,
 * extending both {@link ApplicationBuilder} and {@link WebApplicationConfigurer}.
 */
public interface WebApplicationBuilder extends
        ApplicationBuilder<WebApplication, WebApplicationConfigurer>,
        WebApplicationConfigurer {

    @Override
    WebApplicationBuilder options(GroupOptionSet options);

    @Override
    WebApplicationBuilder configure(Runnable1<WebApplicationConfigurer> action);

    @Override
    WebApplicationBuilder configureOptions(Runnable1<GroupOptionSet> action);

    @Override
    WebApplicationBuilder withEnvironmentFactory(EnvironmentFactory factory);

    @Override
    WebApplicationBuilder environmentName(String name);

    @Override
    WebApplicationBuilder configureServices(Runnable1<ServicesConfigurer> action);

    @Override
    WebApplicationBuilder configureProviderBuilder(Runnable1<ScopedProviderBuilder> action);

    @Override
    WebApplicationBuilder configureProvider(Runnable1<ServiceProvider> action);

    @Override
    WebApplicationBuilder configureApplication(Runnable1<WebApplication> action);

    @Override
    WebApplicationBuilder withServerFactory(HttpServerFactory factory);

    /**
     * Builds and returns the configured {@link WebApplication} instance.
     *
     * @return the built web application instance
     */
    @Override
    WebApplication build();
}
