package io.github.amayaframework.server;

import io.github.amayaframework.context.Context;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.OptionSet;

/**
 * An interface describing an abstract {@link Server} factory.
 *
 * @param <T> the type of server context
 */
public interface ServerFactory<T extends Context> {

    /**
     * Creates a {@link Server} instance with given options and environment.
     *
     * @param set the specified {@link OptionSet} instance, containing initial server options
     * @param env the specified {@link Environment} instance
     * @return ready to use, the properly configured {@link Server} instance
     */
    default Server<T> create(OptionSet set, Environment env) {
        return create(set);
    }

    /**
     * Creates a {@link Server} instance with given options.
     *
     * @param set the specified {@link OptionSet} instance, containing initial server options
     * @return ready to use, the properly configured {@link Server} instance
     */
    Server<T> create(OptionSet set);

    /**
     * Creates a {@link Server} instance with environment and default options, defined by the factory implementation.
     *
     * @param env the specified {@link Environment} instance
     * @return ready to use, the properly configured {@link Server} instance
     */
    default Server<T> create(Environment env) {
        return create();
    }

    /**
     * Creates a {@link Server} instance with default options, defined by the factory implementation.
     *
     * @return ready to use, the properly configured {@link Server} instance
     */
    Server<T> create();
}
