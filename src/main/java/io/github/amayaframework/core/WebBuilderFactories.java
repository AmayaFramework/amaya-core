package io.github.amayaframework.core;

import io.github.amayaframework.di.ProviderBuilders;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.web.WebBuilderFactory;

import java.util.function.Supplier;

/**
 * A class containing methods for creating {@link WebBuilderFactory} instances.
 */
public final class WebBuilderFactories {
    private static final String AMAYA_DI_MODULE = "io.github.amayaframework.di";
    private static final String AMAYA_SERVICE_PROVER = "io.github.amayaframework.di.ServiceProvider";

    private WebBuilderFactories() {
    }

    private static boolean isDIModuleLoaded() {
        var layer = ModuleLayer.boot();
        return layer.findModule(AMAYA_DI_MODULE).isPresent();
    }

    private static boolean isDIClassExists() {
        var loader = Thread.currentThread().getContextClassLoader();
        try {
            var loaded = loader.loadClass(AMAYA_SERVICE_PROVER);
            return loaded.getName().equals(AMAYA_SERVICE_PROVER);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static boolean isDILoaded() {
        return isDIModuleLoaded() || isDIClassExists();
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
        return new ProvidedBuilderFactory(ProviderBuilders::createChecked);
    }

    /**
     * Create a {@link WebBuilderFactory} instance.
     * Dynamically determines if amaya di module loaded and use appropriate factory.
     *
     * @return the {@link WebBuilderFactory} instance
     */
    public static WebBuilderFactory create() {
        if (isDILoaded()) {
            return createProvided();
        }
        return createStandalone();
    }
}
