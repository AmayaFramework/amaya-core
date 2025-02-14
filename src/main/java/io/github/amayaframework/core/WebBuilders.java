package io.github.amayaframework.core;

import io.github.amayaframework.di.ProviderBuilders;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.di.stub.StubFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.web.WebApplicationBuilder;

import java.util.function.Supplier;

/**
 * A class containing methods for creating {@link WebApplicationBuilder} instances.
 */
public final class WebBuilders {
    private WebBuilders() {
    }

    /**
     * Creates standalone {@link WebApplicationBuilder} instance (without integration with amaya di module)
     * with given options.
     *
     * @param options the specified {@link GroupOptionSet} instance
     * @return the {@link WebApplicationBuilder} instance
     */
    public static WebApplicationBuilder createStandalone(GroupOptionSet options) {
        var factory = new StandaloneBuilderFactory();
        return factory.create(options);
    }

    /**
     * Creates standalone {@link WebApplicationBuilder} instance (without integration with amaya di module).
     *
     * @return the {@link WebApplicationBuilder} instance
     */
    public static WebApplicationBuilder createStandalone() {
        var factory = new StandaloneBuilderFactory();
        return factory.create();
    }

    /**
     * Creates provided {@link WebApplicationBuilder} instance (with integration with amaya di module)
     * with given {@link ServiceProviderBuilder} supplier and option set.
     *
     * @param supplier the specified supplier of {@link ServiceProviderBuilder}
     * @param options  the specified {@link GroupOptionSet} instance
     * @return the {@link WebApplicationBuilder} instance
     */
    public static WebApplicationBuilder createProvided(Supplier<ServiceProviderBuilder> supplier,
                                                       GroupOptionSet options) {
        var factory = new ProvidedBuilderFactory(supplier);
        return factory.create(options);
    }

    /**
     * Creates provided {@link WebApplicationBuilder} instance (with integration with amaya di module)
     * with given {@link ServiceProviderBuilder} supplier.
     *
     * @param supplier the specified supplier of {@link ServiceProviderBuilder}
     * @return the {@link WebApplicationBuilder} instance
     */
    public static WebApplicationBuilder createProvided(Supplier<ServiceProviderBuilder> supplier) {
        var factory = new ProvidedBuilderFactory(supplier);
        return factory.create();
    }

    /**
     * Creates provided {@link WebApplicationBuilder} instance (with integration with amaya di module)
     * with given option set.
     *
     * @param options the specified {@link GroupOptionSet} instance
     * @return the {@link WebApplicationBuilder} instance
     */
    public static WebApplicationBuilder createProvided(GroupOptionSet options) {
        var stubFactory = (StubFactory) ReflectUtil.lookupStubFactory();
        var factory = new ProvidedBuilderFactory(() -> ProviderBuilders.createChecked(stubFactory));
        return factory.create(options);
    }

    /**
     * Creates provided {@link WebApplicationBuilder} instance (with integration with amaya di module).
     *
     * @return the {@link WebApplicationBuilder} instance
     */
    public static WebApplicationBuilder createProvided() {
        var stubFactory = (StubFactory) ReflectUtil.lookupStubFactory();
        var factory = new ProvidedBuilderFactory(() -> ProviderBuilders.createChecked(stubFactory));
        return factory.create();
    }

    /**
     * Creates a {@link WebApplicationBuilder} instance with given option set.
     * Dynamically determines if amaya di module loaded and use appropriate factory.
     *
     * @param options the specified {@link GroupOptionSet} instance
     * @return the {@link WebApplicationBuilder} instance
     */
    public static WebApplicationBuilder create(GroupOptionSet options) {
        var factory = WebBuilderFactories.create();
        return factory.create(options);
    }

    /**
     * Creates a {@link WebApplicationBuilder} instance.
     * Dynamically determines if amaya di module loaded and use appropriate factory.
     *
     * @return the {@link WebApplicationBuilder} instance
     */
    public static WebApplicationBuilder create() {
        var factory = WebBuilderFactories.create();
        return factory.create();
    }
}
