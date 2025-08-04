package io.github.amayaframework.application;

/**
 * An interface describing the abstract builder of the {@link Application}.
 *
 * @param <T> the application type
 */
public interface ApplicationBuilder<A extends Application<?>, C extends ApplicationBuilder<A, C>>
        extends ApplicationConfigurer<A, C> {

    /**
     * Builds the {@link Application} instance with the specified components.
     *
     * @return the {@link Application} instance
     */
    A build();
}
