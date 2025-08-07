package io.github.amayaframework.application;

import com.github.romanqed.jfunc.Exceptions;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;

/**
 * An abstract implementation of the {@link ApplicationBuilder} interface that extends
 * {@link AbstractApplicationConfigurer} to provide a fluent builder for constructing application instances.
 *
 * <p>This class handles the full lifecycle of preparing options, creating the environment,
 * instantiating the application, and invoking all registered configuration actions.</p>
 *
 * <p>All exceptions are propagated using {@link Exceptions#throwAny(Throwable)} to preserve
 * unchecked propagation semantics.</p>
 *
 * @param <A> the type of the application being built
 * @param <C> the configurer type used for fluent configuration
 * @param <R> the concrete builder type used for chaining
 */
public abstract class AbstractApplicationBuilder
        <A extends Application<?>, C extends ApplicationConfigurer<A, C>, R extends ApplicationBuilder<A, C>>
        extends AbstractApplicationConfigurer<A, C, R>
        implements ApplicationBuilder<A, C> {

    /**
     * Constructs a new builder using the provided services configurer.
     *
     * @param configurer the services configurer to use
     */
    protected AbstractApplicationBuilder(ServicesConfigurer configurer) {
        super(configurer);
    }

    /**
     * Creates the application environment based on the given options.
     *
     * @param options the group option set to use
     * @return the created environment instance
     * @throws Throwable if environment creation fails
     */
    protected abstract Environment createEnvironment(GroupOptionSet options) throws Throwable;

    /**
     * Creates a new application instance based on the given options and environment.
     *
     * @param options the group option set to use
     * @param env     the environment to be passed to the application
     * @return the newly created application
     * @throws Throwable if application creation fails
     */
    protected abstract A createApplication(GroupOptionSet options, Environment env) throws Throwable;

    /**
     * Builds the application, executing all configuration logic in a defined sequence:
     * <ul>
     *     <li>Creates or uses existing options</li>
     *     <li>Initializes the environment</li>
     *     <li>Constructs the application instance</li>
     *     <li>Applies all registered application consumers</li>
     * </ul>
     *
     * <p>If application construction fails, the created environment is safely closed.</p>
     *
     * @return the fully configured application instance
     * @throws Throwable if any error occurs during build process
     */
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

    /**
     * Builds the application using the configured options and environment,
     * applying all custom logic and resetting the builder state upon completion.
     *
     * <p>Exceptions thrown during the build process are rethrown using {@link Exceptions#throwAny(Throwable)}.</p>
     *
     * @return the built application instance
     */
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
