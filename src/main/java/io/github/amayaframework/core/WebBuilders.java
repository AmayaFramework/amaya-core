package io.github.amayaframework.core;

import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.web.WebApplicationBuilder;

/**
 * Utility class providing convenient static factory methods
 * for creating instances of {@link WebApplicationBuilder}.
 * <p>
 * Allows creation with optional configuration options and logger factory supplier.
 */
public final class WebBuilders {
    private WebBuilders() {
    }

    /**
     * Creates a {@link WebApplicationBuilder} using the provided options
     * and a custom {@link LoggerFactorySupplier}.
     *
     * @param options  the group of configuration options, must not be null
     * @param supplier the supplier providing the logger factory, must not be null
     * @return a configured instance of {@link WebApplicationBuilder}
     */
    public static WebApplicationBuilder create(GroupOptionSet options, LoggerFactorySupplier supplier) {
        var factory = WebBuilderFactories.create(supplier);
        return factory.create(options);
    }

    /**
     * Creates a {@link WebApplicationBuilder} using the provided options
     * with a default logger factory supplier.
     *
     * @param options the group of configuration options, must not be null
     * @return a configured instance of {@link WebApplicationBuilder}
     */
    public static WebApplicationBuilder create(GroupOptionSet options) {
        var factory = WebBuilderFactories.create();
        return factory.create(options);
    }

    /**
     * Creates a default {@link WebApplicationBuilder} with a custom
     * {@link LoggerFactorySupplier} and no preset options.
     *
     * @param supplier the supplier providing the logger factory, must not be null
     * @return a default instance of {@link WebApplicationBuilder}
     */
    public static WebApplicationBuilder create(LoggerFactorySupplier supplier) {
        var factory = WebBuilderFactories.create(supplier);
        return factory.create();
    }

    /**
     * Creates a default {@link WebApplicationBuilder} with no options
     * and default logger factory supplier.
     *
     * @return a default instance of {@link WebApplicationBuilder}
     */
    public static WebApplicationBuilder create() {
        return WebBuilderFactories.create().create();
    }
}
