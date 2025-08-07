package io.github.amayaframework.application;

import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.OptionSet;
import io.github.amayaframework.service.ServiceManager;

/**
 * Factory interface for creating {@link ServiceManager} instances
 * based on provided configuration options and environment.
 */
public interface ServiceManagerFactory {

    /**
     * Creates a {@link ServiceManager} using the given options and environment.
     *
     * @param options the configuration options, must be non-null
     * @param env     the environment context, may be null
     * @return a new {@link ServiceManager} instance
     */
    default ServiceManager create(OptionSet options, Environment env) {
        return create(options);
    }

    /**
     * Creates a {@link ServiceManager} using the given configuration options.
     *
     * @param options the configuration options, must be non-null
     * @return a new {@link ServiceManager} instance
     */
    ServiceManager create(OptionSet options);

    /**
     * Creates a {@link ServiceManager} using the given environment context.
     *
     * @param env the environment context, may be null
     * @return a new {@link ServiceManager} instance
     */
    default ServiceManager create(Environment env) {
        return create();
    }

    /**
     * Creates a default {@link ServiceManager} without any specific configuration.
     *
     * @return a new {@link ServiceManager} instance
     */
    ServiceManager create();
}
