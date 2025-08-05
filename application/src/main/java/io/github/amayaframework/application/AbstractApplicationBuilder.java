package io.github.amayaframework.application;

import com.github.romanqed.jfunc.Exceptions;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;

public abstract class AbstractApplicationBuilder
        <A extends Application<?>, C extends ApplicationConfigurer<A, C>, R extends ApplicationBuilder<A, C>>
        extends AbstractApplicationConfigurer<A, C, R>
        implements ApplicationBuilder<A, C> {

    protected AbstractApplicationBuilder(ServicesConfigurer configurer) {
        super(configurer);
    }

    protected abstract Environment createEnvironment(GroupOptionSet options) throws Throwable;

    protected abstract A createApplication(GroupOptionSet options, Environment env) throws Throwable;

    protected A doBuild() throws Throwable {
        // Prepare options
        var options = this.options == null ? createDefaultOptions() : this.options;
        // Prepare environment
        var environment = createEnvironment(options);
        try {
            // Create application
            var ret = createApplication(options, environment);
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
