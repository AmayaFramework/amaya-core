package io.github.amayaframework.application;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractApplicationConfigurer
        <A extends Application<?>, C extends ApplicationConfigurer<A, C>, R extends ApplicationConfigurer<A, C>>
        implements ApplicationConfigurer<A, C> {

    protected final ServicesConfigurer configurer;
    protected GroupOptionSet options;
    protected EnvironmentFactory environmentFactory;
    protected String environmentName;
    protected List<Runnable1<A>> consumers;

    protected AbstractApplicationConfigurer(ServicesConfigurer configurer) {
        this.configurer = configurer;
    }

    protected abstract GroupOptionSet createDefaultOptions();

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
