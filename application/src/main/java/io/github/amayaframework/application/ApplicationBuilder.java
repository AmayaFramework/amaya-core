package io.github.amayaframework.application;

/**
 * Builds a configured {@link Application} instance.
 * <p>
 * Extends {@link ApplicationConfigurer} to allow configuration before build.
 * </p>
 *
 * @param <A> the type of {@link Application} produced by this builder
 * @param <C> the type of {@link ApplicationConfigurer} used during configuration
 */
public interface ApplicationBuilder<A extends Application<?>, C extends ApplicationConfigurer<A, C>>
        extends ApplicationConfigurer<A, C> {

    /**
     * Constructs and returns the configured {@link Application} instance.
     *
     * @return the built application
     */
    A build();
}
