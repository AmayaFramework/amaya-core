package io.github.amayaframework.application;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;

/**
 *
 * @param <T>
 */
public interface ApplicationBuilder<T> extends Resettable {

    /**
     *
     * @param action
     * @return
     */
    ApplicationBuilder<T> configure(Runnable1<ApplicationBuilder<T>> action);

    /**
     *
     * @return
     */
    GroupOptionSet getOptions();

    /**
     *
     * @param options
     * @return
     */
    ApplicationBuilder<T> setOptions(GroupOptionSet options);

    /**
     *
     * @param action
     * @return
     */
    ApplicationBuilder<T> configureOptions(Runnable1<GroupOptionSet> action);

    /**
     *
     * @param factory
     * @return
     */
    ApplicationBuilder<T> setEnvironmentFactory(EnvironmentFactory factory);

    /**
     *
     * @param name
     * @return
     */
    ApplicationBuilder<T> setEnvironmentName(String name);

    /**
     *
     * @return
     */
    ServiceManagerBuilder getManagerBuilder();

    /**
     *
     * @param action
     * @return
     */
    ApplicationBuilder<T> configureManager(Runnable1<ServiceManagerBuilder> action);

    /**
     *
     * @return
     */
    ServiceProviderBuilder getProviderBuilder();

    /**
     *
     * @param action
     * @return
     */
    ApplicationBuilder<T> configureProviderBuilder(Runnable1<ServiceProviderBuilder> action);

    /**
     *
     * @param action
     * @return
     */
    ApplicationBuilder<T> configureProvider(Runnable1<ServiceProvider> action);

    /**
     *
     * @param action
     * @return
     */
    ApplicationBuilder<T> configureApplication(Runnable1<Application<T>> action);

    /**
     *
     * @return
     */
    Application<T> build();
}
