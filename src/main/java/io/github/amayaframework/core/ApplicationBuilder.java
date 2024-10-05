package io.github.amayaframework.core;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServerFactory;

import java.net.InetSocketAddress;

public interface ApplicationBuilder extends Resettable<ApplicationBuilder> {

    ApplicationBuilder configure(Runnable1<ApplicationBuilder> action);

    GroupOptionSet getOptions();

    ApplicationBuilder setOptions(GroupOptionSet options);

    ApplicationBuilder configureOptions(Runnable1<GroupOptionSet> action);

    ApplicationBuilder setEnvironmentFactory(EnvironmentFactory factory);

    ApplicationBuilder setEnvironmentName(String name);

    ServiceManagerBuilder getManagerBuilder();

    ApplicationBuilder configureManager(Runnable1<ServiceManagerBuilder> action);

    ServiceProviderBuilder getProviderBuilder();

    ApplicationBuilder configureProviderBuilder(Runnable1<ServiceProviderBuilder> action);

    ApplicationBuilder configureProvider(Runnable1<ServiceProvider> action);

    ApplicationBuilder setServerFactory(HttpServerFactory factory);

    ApplicationBuilder configureApplication(Runnable1<Application> action);

    ApplicationBuilder bind(InetSocketAddress address);

    ApplicationBuilder bind(int port);

    Application build();
}
