package io.github.amayaframework.server;

import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.OptionSet;

/**
 * An interface describing an abstract {@link HttpServer} factory.
 */
public interface HttpServerFactory extends ServerFactory<HttpContext> {

    /**
     * Creates a {@link HttpServer} instance with given options and environment.
     *
     * @param set the specified {@link OptionSet} instance, containing initial server options
     * @param env the specified {@link Environment} instance
     * @return ready to use, the properly configured {@link HttpServer} instance
     */
    default HttpServer create(OptionSet set, Environment env) {
        return create(set);
    }

    /**
     * Creates a {@link HttpServer} instance with given options.
     *
     * @param set the specified {@link OptionSet} instance, containing initial server options
     * @return ready to use, the properly configured {@link HttpServer} instance
     */
    @Override
    HttpServer create(OptionSet set);

    /**
     * Creates a {@link HttpServer} instance with environment and default options,
     * defined by the factory implementation.
     *
     * @param env the specified {@link Environment} instance
     * @return ready to use, the properly configured {@link HttpServer} instance
     */
    default HttpServer create(Environment env) {
        return create();
    }

    /**
     * Creates a {@link HttpServer} instance with default options, defined by the factory implementation.
     *
     * @return ready to use, the properly configured {@link HttpServer} instance
     */
    @Override
    HttpServer create();
}
