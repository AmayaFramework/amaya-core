package io.github.amayaframework.core;

import io.github.amayaframework.di.ProviderBuilders;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.di.stub.StubFactory;
import io.github.amayaframework.web.WebBuilderFactory;

import java.util.function.Supplier;

/**
 * A class containing methods for creating {@link WebBuilderFactory} instances.
 */
public final class WebBuilderFactories {
    private WebBuilderFactories() {
    }

    /**
     * Creates standalone {@link WebBuilderFactory} instance (without integration with amaya di module).
     *
     * @return the {@link WebBuilderFactory} instance
     */
    public static WebBuilderFactory createStandalone() {
        return new StandaloneBuilderFactory();
    }

    /**
     * Creates provided {@link WebBuilderFactory} instance (with integration with amaya di module)
     * with given {@link ServiceProviderBuilder} supplier.
     *
     * @param supplier the specified supplier of {@link ServiceProviderBuilder}
     * @return the {@link WebBuilderFactory} instance
     */
    public static WebBuilderFactory createProvided(Supplier<ServiceProviderBuilder> supplier) {
        return new ProvidedBuilderFactory(supplier);
    }

    /**
     * Creates provided {@link WebBuilderFactory} instance (with integration with amaya di module).
     *
     * @return the {@link WebBuilderFactory} instance
     */
    public static WebBuilderFactory createProvided() {
        var factory = (StubFactory) ReflectUtil.lookupStubFactory();
        return new ProvidedBuilderFactory(() -> ProviderBuilders.createChecked(factory));
    }

    /**
     * Create a {@link WebBuilderFactory} instance.
     * Dynamically determines if amaya di module loaded and use appropriate factory.
     *
     * @return the {@link WebBuilderFactory} instance
     */
    public static WebBuilderFactory create() {
        if (ReflectUtil.isDILoaded()) {
            return createProvided();
        }
        return createStandalone();
    }
}
