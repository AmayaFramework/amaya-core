package io.github.amayaframework.application;

import com.github.romanqed.jfunc.Exceptions;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.service.ServiceManager;

public abstract class AbstractApplicationBuilder<A extends Application<?>, C extends ApplicationConfigurer<A, C>>
        extends AbstractApplicationConfigurer<A, C> implements ApplicationBuilder<A, C> {

    protected AbstractApplicationBuilder(ServicesConfigurer configurer) {
        super(configurer);
    }

    protected abstract Environment createEnvironment(GroupOptionSet options) throws Throwable;

    protected abstract ServiceManager createServiceManager(GroupOptionSet options, Environment env) throws Throwable;

    protected abstract A createApplication(GroupOptionSet options, Environment env, ServiceManager manager) throws Throwable;

    protected A doBuild() throws Throwable {
        // Prepare options
        var options = this.options == null ? createDefaultOptions() : this.options;
        // Prepare environment
        var environment = createEnvironment(options);
        try {
            // Prepare service manager
            var manager = createServiceManager(options, environment);
            // Create application
            var ret = createApplication(options, environment, manager);
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
    public A build() {
        try {
            return doBuild();
        } catch (Throwable e) {
            Exceptions.throwAny(e);
            // Unreachable code to suppress javac error
            return null;
        } finally {
            reset();
        }
    }
}
