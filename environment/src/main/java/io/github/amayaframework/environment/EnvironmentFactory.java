package io.github.amayaframework.environment;

import io.github.amayaframework.options.OptionSet;

import java.io.IOException;

/**
 * An interface describing an abstract environment factory.
 */
public interface EnvironmentFactory {

    /**
     * Creates environment with the specified name and options from given {@link OptionSet} instance.
     *
     * @param name    the specified environment name
     * @param options the specified {@link OptionSet} instance containing environment options
     * @return the {@link Environment} instance
     * @throws IOException if some creating or initializing problems occurs
     */
    Environment create(String name, OptionSet options) throws IOException;

    /**
     * Creates environment with the specified name and default options.
     *
     * @param name the specified environment name
     * @return the {@link Environment} instance
     * @throws IOException if some creating or initializing problems occurs
     */
    Environment create(String name) throws IOException;
}
