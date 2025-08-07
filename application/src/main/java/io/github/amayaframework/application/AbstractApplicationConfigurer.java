package io.github.amayaframework.application;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A base implementation of the {@link ApplicationConfigurer} interface providing
 * a fluent API for configuring application options, environment, services,
 * and custom actions to be applied before application start.
 *
 * <p>This class is not thread-safe and is intended to be used in a single-threaded
 * context during application configuration.</p>
 *
 * @param <A> the type of the application being configured
 * @param <C> the type of the configurer interface used for chaining
 * @param <R> the actual return type used for fluent method chaining
 */
public abstract class AbstractApplicationConfigurer
        <A extends Application<?>, C extends ApplicationConfigurer<A, C>, R extends ApplicationConfigurer<A, C>>
        implements ApplicationConfigurer<A, C> {

    /**
     * The internal services configurer used to define service bindings.
     */
    protected final ServicesConfigurer configurer;

    /**
     * The option set used to configure application settings.
     */
    protected GroupOptionSet options;

    /**
     * The factory used to create application environments.
     */
    protected EnvironmentFactory environmentFactory;

    /**
     * The name of the application environment.
     */
    protected String environmentName;

    /**
     * The list of actions to be executed against the application before starting.
     */
    protected List<Runnable1<A>> consumers;

    /**
     * Constructs a new configurer with the provided services configurer.
     *
     * @param configurer the services configurer to be used for this configuration
     */
    protected AbstractApplicationConfigurer(ServicesConfigurer configurer) {
        this.configurer = configurer;
    }

    /**
     * Creates a new default {@link GroupOptionSet} used when none was provided explicitly.
     *
     * @return a default option set instance
     */
    protected abstract GroupOptionSet createDefaultOptions();

    /**
     * Resets the entire configuration to its initial state.
     * This includes options, environment, and custom application consumers.
     */
    @Override
    public void reset() {
        configurer.reset();
        options = null;
        environmentFactory = null;
        environmentName = null;
        consumers = null;
    }

    @Override
    public GroupOptionSet options() {
        if (options == null) {
            options = createDefaultOptions();
        }
        return options;
    }

    @Override
    @SuppressWarnings("unchecked")
    public R options(GroupOptionSet options) {
        this.options = options;
        return (R) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public R configure(Runnable1<C> action) {
        Objects.requireNonNull(action);
        try {
            action.run((C) this);
        } catch (Throwable e) {
            Exceptions.throwAny(e);
        }
        return (R) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public R configureOptions(Runnable1<GroupOptionSet> action) {
        Objects.requireNonNull(action);
        if (options == null) {
            options = createDefaultOptions();
        }
        try {
            action.run(options);
        } catch (Throwable e) {
            Exceptions.throwAny(e);
        }
        return (R) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public R withEnvironmentFactory(EnvironmentFactory factory) {
        this.environmentFactory = factory;
        return (R) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public R environmentName(String name) {
        this.environmentName = name;
        return (R) this;
    }

    @Override
    public ServicesConfigurer servicesConfigurer() {
        return configurer;
    }

    @Override
    @SuppressWarnings("unchecked")
    public R configureServices(Runnable1<ServicesConfigurer> action) {
        Objects.requireNonNull(action);
        try {
            action.run(configurer);
        } catch (Throwable e) {
            Exceptions.throwAny(e);
        }
        return (R) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public R configureApplication(Runnable1<A> action) {
        Objects.requireNonNull(action);
        if (consumers == null) {
            consumers = new LinkedList<>();
        }
        consumers.add(action);
        return (R) this;
    }
}
