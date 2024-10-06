package io.github.amayaframework.application;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;

/**
 * An interface describing the abstract builder of the {@link Application}.
 *
 * @param <T> the type of application context
 */
public interface ApplicationBuilder<T> extends Resettable {

    /**
     * Applies given action to this {@link ApplicationBuilder} instance.
     *
     * @param action the specified action to be applied to this builder, must be non-null
     * @return this {@link ApplicationBuilder} instance
     */
    ApplicationBuilder<T> configure(Runnable1<ApplicationBuilder<T>> action);

    /**
     * Gets the {@link GroupOptionSet} instance.
     * If builder does not contain one, it will be created.
     *
     * @return the {@link GroupOptionSet} instance
     */
    GroupOptionSet getOptions();

    /**
     * Sets the {@link GroupOptionSet} instance.
     *
     * @param options the {@link GroupOptionSet} instance to be set, must be non-null
     * @return this {@link ApplicationBuilder} instance
     */
    ApplicationBuilder<T> setOptions(GroupOptionSet options);

    /**
     * Applies given action to the {@link GroupOptionSet} instance.
     * If builder does not contain one, it will be created.
     *
     * @param action the specified action to be applied to {@link GroupOptionSet} instance, must be non-null
     * @return this {@link ApplicationBuilder} instance
     */
    ApplicationBuilder<T> configureOptions(Runnable1<GroupOptionSet> action);

    /**
     * Sets the environment factory.
     *
     * @param factory the {@link EnvironmentFactory} instance, may be null
     * @return this {@link ApplicationBuilder} instance
     */
    ApplicationBuilder<T> setEnvironmentFactory(EnvironmentFactory factory);

    /**
     * Sets the environment name, that will be used to create application environment.
     *
     * @param name the string containing environment name, may be null
     * @return this {@link ApplicationBuilder} instance
     */
    ApplicationBuilder<T> setEnvironmentName(String name);

    /**
     * Gets the {@link ServiceManagerBuilder} instance.
     * Normally, you SHOULD NOT call {@link ServiceManagerBuilder#build()} manually.
     *
     * @return the {@link ServiceManagerBuilder} instance
     */
    ServiceManagerBuilder getManagerBuilder();

    /**
     * Applies given action to the {@link ServiceManagerBuilder}.
     * Normally, you SHOULD NOT call {@link ServiceManagerBuilder#build()} manually.
     *
     * @param action the specified action to be applied to {@link ServiceManagerBuilder} instance, must be non-null
     * @return this {@link ApplicationBuilder} instance
     */
    ApplicationBuilder<T> configureManager(Runnable1<ServiceManagerBuilder> action);

    /**
     * Gets the {@link ServiceProviderBuilder} instance.
     *
     * @return the {@link ServiceProviderBuilder} instance
     * @throws UnsupportedOperationException if amaya di module not loaded
     */
    ServiceProviderBuilder getProviderBuilder();

    /**
     * Applies given action to the {@link ServiceProviderBuilder}.
     *
     * @param action the specified action to be applied to {@link ServiceProviderBuilder} instance, must be non-null
     * @return this {@link ApplicationBuilder} instance
     * @throws UnsupportedOperationException if amaya di module not loaded
     */
    ApplicationBuilder<T> configureProviderBuilder(Runnable1<ServiceProviderBuilder> action);

    /**
     * Applies given action to the {@link ServiceProvider} instance after it will be built.
     *
     * @param action the specified action to be applied to {@link ServiceProvider} instance, must be non-null
     * @return this {@link ApplicationBuilder} instance
     * @throws UnsupportedOperationException if amaya di module not loaded
     */
    ApplicationBuilder<T> configureProvider(Runnable1<ServiceProvider> action);

    /**
     * Applies given action to the {@link Application} instance after it will be built.
     *
     * @param action the specified action to be applied to {@link Application} instance, must be non-null
     * @return this {@link ApplicationBuilder} instance
     */
    ApplicationBuilder<T> configureApplication(Runnable1<Application<T>> action);

    /**
     * Builds the {@link Application} instance with the specified components.
     *
     * @return the {@link Application} instance
     */
    Application<T> build();
}
