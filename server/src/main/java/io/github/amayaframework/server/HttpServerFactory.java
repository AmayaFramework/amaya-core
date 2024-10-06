package io.github.amayaframework.server;

import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.options.OptionSet;

/**
 * An interface describing an abstract {@link HttpServer} factory.
 */
public interface HttpServerFactory extends ServerFactory<HttpContext> {

    /**
     * Creates a {@link HttpServer} instance with given options.
     *
     * @param set the specified {@link OptionSet} instance, containing initial server options
     * @return ready to use, the properly configured {@link HttpServer} instance
     */
    @Override
    HttpServer create(OptionSet set);

    /**
     * Creates a {@link HttpServer} instance with default options, defined by the factory implementation.
     *
     * @return ready to use, the properly configured {@link HttpServer} instance
     */
    @Override
    HttpServer create();
}
