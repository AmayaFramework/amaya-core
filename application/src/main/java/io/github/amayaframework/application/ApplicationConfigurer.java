package io.github.amayaframework.application;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.di.ScopedProviderConfigurer;
import io.github.amayaframework.di.core.ServiceProvider;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;

/**
 * Configures an {@link Application} instance, providing methods to manage
 * options, environment, services, and dependency injection setup.
 * <p>
 * Supports fluent API style with chaining and reset capability.
 * </p>
 *
 * @param <A> the type of {@link Application} being configured
 * @param <C> the type of the concrete {@link ApplicationConfigurer} implementation
 */
public interface ApplicationConfigurer<A extends Application<?>, C extends ApplicationConfigurer<A, C>> extends Resettable {

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
     * @return this configurer instance for chaining
     */
    ApplicationConfigurer<A, C> options(GroupOptionSet options);

    /**
     * Applies the given configuration action to this configurer.
     * Useful for applying multiple configuration steps in a fluent style.
     *
     * @param action the configuration action to apply, must be non-null
     * @return this configurer instance for chaining
     */
    ApplicationConfigurer<A, C> configure(Runnable1<C> action);

    /**
     * Applies given action to the {@link GroupOptionSet} instance.
     * If builder does not contain one, it will be created.
     *
     * @param action the specified action to be applied to {@link GroupOptionSet} instance, must be non-null
     * @return this configurer instance for chaining
     */
    ApplicationConfigurer<A, C> configureOptions(Runnable1<GroupOptionSet> action);

    /**
     * Sets the environment factory.
     *
     * @param factory the {@link EnvironmentFactory} instance, may be null
     * @return this configurer instance for chaining
     */
    ApplicationConfigurer<A, C> withEnvironmentFactory(EnvironmentFactory factory);

    /**
     * Sets the environment name, that will be used to create application environment.
     *
     * @param name the string containing environment name, may be null
     * @return this configurer instance for chaining
     */
    ApplicationConfigurer<A, C> environmentName(String name);

    /**
     * Returns the {@link ServicesConfigurer} instance responsible for configuring
     * internal services used by the application.
     *
     * @return the services configurer
     */
    ServicesConfigurer servicesConfigurer();

    /**
     * Applies the given configuration action to the {@link ServicesConfigurer}.
     * Allows fine-grained control over services and service manager configuration.
     *
     * @param action the action to configure services, must be non-null
     * @return this configurer instance for chaining
     */
    ApplicationConfigurer<A, C> configureServices(Runnable1<ServicesConfigurer> action);

    /**
     * Gets the {@link ScopedProviderConfigurer} instance.
     *
     * @return the {@link ScopedProviderConfigurer} instance if amaya di module loaded, null otherwise
     */
    ScopedProviderConfigurer providerBuilder();

    /**
     * Applies given action to the {@link ScopedProviderConfigurer}. Do nothing if amaya di module not loaded.
     *
     * @param action the specified action to be applied to {@link ScopedProviderConfigurer} instance, must be non-null
     * @return this configurer instance for chaining
     */
    ApplicationConfigurer<A, C> configureProviderBuilder(Runnable1<ScopedProviderConfigurer> action);

    /**
     * Applies given action to the {@link ServiceProvider} instance after it will be built.
     * Do nothing if amaya di module not loaded.
     *
     * @param action the specified action to be applied to {@link ServiceProvider} instance, must be non-null
     * @return this configurer instance for chaining
     */
    ApplicationConfigurer<A, C> configureProvider(Runnable1<ServiceProvider> action);

    /**
     * Applies given action to the {@link Application} instance after it will be built.
     *
     * @param action the specified action to be applied to {@link Application} instance, must be non-null
     * @return this configurer instance for chaining
     */
    ApplicationConfigurer<A, C> configureApplication(Runnable1<A> action);
}
