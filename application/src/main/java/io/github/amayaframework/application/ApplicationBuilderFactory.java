package io.github.amayaframework.application;

import io.github.amayaframework.options.GroupOptionSet;

/**
 * An interface describing an abstract {@link ApplicationBuilder} factory.
 *
 * @param <T> the application type
 */
public interface ApplicationBuilderFactory<T extends Application<?>> {

    /**
     * Creates the {@link ApplicationBuilder} instance with the specified {@link GroupOptionSet}.
     *
     * @param options the specified {@link GroupOptionSet} to be used in {@link ApplicationBuilder}
     * @return the {@link ApplicationBuilder} instance
     */
    ApplicationBuilder<T> create(GroupOptionSet options);

    /**
     * Creates the {@link ApplicationBuilder} instance without specified {@link GroupOptionSet}.
     *
     * @return the {@link ApplicationBuilder} instance
     */
    ApplicationBuilder<T> create();
}
