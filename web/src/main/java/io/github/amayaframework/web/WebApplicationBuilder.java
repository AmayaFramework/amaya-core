package io.github.amayaframework.web;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.application.ApplicationBuilder;
import io.github.amayaframework.application.ServiceManagerBuilder;
import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServerFactory;

import java.net.InetSocketAddress;

/**
 *
 */
public interface WebApplicationBuilder extends ApplicationBuilder<WebApplication> {

    /**
     * @param action
     * @return
     */
    WebApplicationBuilder configure(Runnable1<WebApplicationBuilder> action);

    /**
     * @param factory
     * @return
     */
    WebApplicationBuilder setServerFactory(HttpServerFactory factory);

    /**
     * @param address
     * @return
     */
    WebApplicationBuilder bind(InetSocketAddress address);

    /**
     * @param port
     * @return
     */
    WebApplicationBuilder bind(int port);

    @Override
    WebApplicationBuilder setOptions(GroupOptionSet options);

    @Override
    WebApplicationBuilder configureOptions(Runnable1<GroupOptionSet> action);

    @Override
    WebApplicationBuilder setEnvironmentFactory(EnvironmentFactory factory);

    @Override
    WebApplicationBuilder setEnvironmentName(String name);

    @Override
    WebApplicationBuilder configureManager(Runnable1<ServiceManagerBuilder> action);

    @Override
    WebApplicationBuilder configureProviderBuilder(Runnable1<ServiceProviderBuilder> action);

    @Override
    WebApplicationBuilder configureProvider(Runnable1<ServiceProvider> action);

    @Override
    WebApplicationBuilder configureApplication(Runnable1<WebApplication> action);

    @Override
    WebApplication build();
}
