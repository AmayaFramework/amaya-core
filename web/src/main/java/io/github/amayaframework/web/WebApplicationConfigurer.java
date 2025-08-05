package io.github.amayaframework.web;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.application.ApplicationConfigurer;
import io.github.amayaframework.application.ServicesConfigurer;
import io.github.amayaframework.di.ScopedProviderBuilder;
import io.github.amayaframework.di.core.ServiceProvider;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;

public interface WebApplicationConfigurer extends ApplicationConfigurer<WebApplication, WebApplicationConfigurer> {

    @Override
    WebApplicationConfigurer options(GroupOptionSet options);

    @Override
    WebApplicationConfigurer configure(Runnable1<WebApplicationConfigurer> action);

    @Override
    WebApplicationConfigurer configureOptions(Runnable1<GroupOptionSet> action);

    @Override
    WebApplicationConfigurer withEnvironmentFactory(EnvironmentFactory factory);

    @Override
    WebApplicationConfigurer environmentName(String name);

    @Override
    WebApplicationConfigurer configureServices(Runnable1<ServicesConfigurer> action);

    @Override
    WebApplicationConfigurer configureProviderBuilder(Runnable1<ScopedProviderBuilder> action);

    @Override
    WebApplicationConfigurer configureProvider(Runnable1<ServiceProvider> action);

    @Override
    WebApplicationConfigurer configureApplication(Runnable1<WebApplication> action);
}
