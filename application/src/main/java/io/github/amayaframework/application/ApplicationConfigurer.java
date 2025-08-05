package io.github.amayaframework.application;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.di.ScopedProviderBuilder;
import io.github.amayaframework.di.core.ServiceProvider;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;

public interface ApplicationConfigurer<A extends Application<?>, C extends ApplicationConfigurer<A, C>>
        extends Resettable {

    /**
     * Gets the {@link GroupOptionSet} instance.
     * If builder does not contain one, it will be created.
     *
     * @return the {@link GroupOptionSet} instance
     */
    GroupOptionSet options();

    /**
     * Sets the {@link GroupOptionSet} instance.
     *
     * @param options the {@link GroupOptionSet} instance to be set, must be non-null
     * @return this {@link ApplicationConfigurer} instance
     */
    ApplicationConfigurer<A, C> options(GroupOptionSet options);

    ApplicationConfigurer<A, C> configure(Runnable1<C> action);

    /**
     * Applies given action to the {@link GroupOptionSet} instance.
     * If builder does not contain one, it will be created.
     *
     * @param action the specified action to be applied to {@link GroupOptionSet} instance, must be non-null
     * @return this {@link ApplicationConfigurer} instance
     */
    ApplicationConfigurer<A, C> configureOptions(Runnable1<GroupOptionSet> action);

    /**
     * Sets the environment factory.
     *
     * @param factory the {@link EnvironmentFactory} instance, may be null
     * @return this {@link ApplicationConfigurer} instance
     */
    ApplicationConfigurer<A, C> environmentFactory(EnvironmentFactory factory);

    /**
     * Sets the environment name, that will be used to create application environment.
     *
     * @param name the string containing environment name, may be null
     * @return this {@link ApplicationConfigurer} instance
     */
    ApplicationConfigurer<A, C> environmentName(String name);

    ServiceManagerConfigurer managerConfigurer();

    ApplicationConfigurer<A, C> configureManager(Runnable1<ServiceManagerConfigurer> action);

    /**
     * Gets the {@link ScopedProviderBuilder} instance.
     *
     * @return the {@link ScopedProviderBuilder} instance if amaya di module loaded, null otherwise
     */
    ScopedProviderBuilder getProviderBuilder();

    /**
     * Applies given action to the {@link ScopedProviderBuilder}. Do nothing if amaya di module not loaded.
     *
     * @param action the specified action to be applied to {@link ScopedProviderBuilder} instance, must be non-null
     * @return this {@link ApplicationConfigurer} instance
     */
    ApplicationConfigurer<A, C> configureProviderBuilder(Runnable1<ScopedProviderBuilder> action);

    /**
     * Applies given action to the {@link ServiceProvider} instance after it will be built.
     * Do nothing if amaya di module not loaded.
     *
     * @param action the specified action to be applied to {@link ServiceProvider} instance, must be non-null
     * @return this {@link ApplicationConfigurer} instance
     */
    ApplicationConfigurer<A, C> configureProvider(Runnable1<ServiceProvider> action);

    /**
     * Applies given action to the {@link Application} instance after it will be built.
     *
     * @param action the specified action to be applied to {@link Application} instance, must be non-null
     * @return this {@link ApplicationConfigurer} instance
     */
    ApplicationConfigurer<A, C> configureApplication(Runnable1<A> action);
}
