package io.github.amayaframework.core;

import io.github.amayaframework.di.ProviderBuilders;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.web.WebBuilderFactory;

import java.util.function.Supplier;

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

    public static WebBuilderFactory createStandalone() {
        return new StandaloneBuilderFactory();
    }

    public static WebBuilderFactory createProvided(Supplier<ServiceProviderBuilder> supplier) {
        return new ServiceBuilderFactory(supplier);
    }

    public static WebBuilderFactory createProvided() {
        return new ServiceBuilderFactory(ProviderBuilders::createChecked);
    }

    public static WebBuilderFactory create() {
        if (isDILoaded()) {
            return createProvided();
        }
        return createStandalone();
    }
}
