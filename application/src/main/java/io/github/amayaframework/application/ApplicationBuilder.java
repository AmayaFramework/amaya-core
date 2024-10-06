package io.github.amayaframework.application;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;

public interface ApplicationBuilder<T> extends Resettable {

    ApplicationBuilder<T> configure(Runnable1<ApplicationBuilder<T>> action);

    GroupOptionSet getOptions();

    ApplicationBuilder<T> setOptions(GroupOptionSet options);

    ApplicationBuilder<T> configureOptions(Runnable1<GroupOptionSet> action);

    ApplicationBuilder<T> setEnvironmentFactory(EnvironmentFactory factory);

    ApplicationBuilder<T> setEnvironmentName(String name);

    ServiceManagerBuilder getManagerBuilder();

    ApplicationBuilder<T> configureManager(Runnable1<ServiceManagerBuilder> action);

    ServiceProviderBuilder getProviderBuilder();

    ApplicationBuilder<T> configureProviderBuilder(Runnable1<ServiceProviderBuilder> action);

    ApplicationBuilder<T> configureProvider(Runnable1<ServiceProvider> action);

    ApplicationBuilder<T> configureApplication(Runnable1<Application<T>> action);

    Application<T> build();
}
