package io.github.amayaframework.application;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.service.ServiceManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractApplicationBuilder<T> implements ApplicationBuilder<T> {
    // Service manager builder
    protected final ServiceManagerBuilder managerBuilder;
    // Modifiable params
    protected EnvironmentFactory environmentFactory;
    protected String environmentName;
    protected GroupOptionSet options;
    // Deferred application consumers
    protected List<Runnable1<Application<T>>> consumers;

    protected AbstractApplicationBuilder(ServiceManagerBuilder managerBuilder) {
        this.managerBuilder = managerBuilder;
    }

    @Override
    public void reset() {
        managerBuilder.reset();
        this.environmentFactory = null;
        this.environmentName = null;
        this.consumers = null;
    }

    @Override
    public ApplicationBuilder<T> configure(Runnable1<ApplicationBuilder<T>> action) {
        try {
            action.run(this);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public GroupOptionSet getOptions() {
        return options;
    }

    @Override
    public ApplicationBuilder<T> setOptions(GroupOptionSet options) {
        this.options = options;
        return this;
    }

    @Override
    public ApplicationBuilder<T> configureOptions(Runnable1<GroupOptionSet> action) {
        try {
            action.run(options);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public ApplicationBuilder<T> setEnvironmentFactory(EnvironmentFactory factory) {
        this.environmentFactory = factory;
        return this;
    }

    @Override
    public ApplicationBuilder<T> setEnvironmentName(String name) {
        this.environmentName = name;
        return this;
    }

    @Override
    public ServiceManagerBuilder getManagerBuilder() {
        return managerBuilder;
    }

    @Override
    public ApplicationBuilder<T> configureManager(Runnable1<ServiceManagerBuilder> action) {
        try {
            action.run(managerBuilder);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public ApplicationBuilder<T> configureApplication(Runnable1<Application<T>> action) {
        Objects.requireNonNull(action);
        if (consumers == null) {
            consumers = new LinkedList<>();
        }
        consumers.add(action);
        return this;
    }

    protected abstract Environment createEnvironment(GroupOptionSet options) throws Throwable;

    protected abstract GroupOptionSet createDefaultOptions();

    protected abstract Application<T> createApplication(GroupOptionSet options,
                                                        Environment environment,
                                                        ServiceManager manager) throws Throwable;

    protected Application<T> doBuild() throws Throwable {
        // Prepare options
        var set = Objects.requireNonNullElse(options, createDefaultOptions());
        // Prepare environment
        var environment = createEnvironment(options);
        try {
            // Prepare service manager
            var manager = managerBuilder.build();
            // Create application
            var ret = createApplication(set, environment, manager);
            if (consumers != null) {
                for (var consumer : consumers) {
                    consumer.run(ret);
                }
            }
            return ret;
        } catch (Throwable e) {
            environment.close();
            throw e;
        }
    }

    @Override
    public Application<T> build() {
        try {
            return doBuild();
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            reset();
        }
    }
}
