package io.github.amayaframework.core;

import io.github.amayaframework.di.ProviderBuilders;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.web.WebApplicationBuilder;

import java.util.function.Supplier;

/**
 *
 */
public final class WebBuilders {
    private WebBuilders() {
    }

    /**
     *
     * @param options
     * @return
     */
    public static WebApplicationBuilder createStandalone(GroupOptionSet options) {
        var factory = new StandaloneBuilderFactory();
        return factory.create(options);
    }

    /**
     *
     * @return
     */
    public static WebApplicationBuilder createStandalone() {
        var factory = new StandaloneBuilderFactory();
        return factory.create();
    }

    /**
     *
     * @param supplier
     * @param options
     * @return
     */
    public static WebApplicationBuilder createProvided(Supplier<ServiceProviderBuilder> supplier,
                                                       GroupOptionSet options) {
        var factory = new ServiceBuilderFactory(supplier);
        return factory.create(options);
    }

    /**
     *
     * @param supplier
     * @return
     */
    public static WebApplicationBuilder createProvided(Supplier<ServiceProviderBuilder> supplier) {
        var factory = new ServiceBuilderFactory(supplier);
        return factory.create();
    }

    /**
     *
     * @param options
     * @return
     */
    public static WebApplicationBuilder createProvided(GroupOptionSet options) {
        var factory = new ServiceBuilderFactory(ProviderBuilders::createChecked);
        return factory.create(options);
    }

    /**
     *
     * @return
     */
    public static WebApplicationBuilder createProvided() {
        var factory = new ServiceBuilderFactory(ProviderBuilders::createChecked);
        return factory.create();
    }

    /**
     *
     * @param options
     * @return
     */
    public static WebApplicationBuilder create(GroupOptionSet options) {
        var factory = WebBuilderFactories.create();
        return factory.create(options);
    }

    /**
     *
     * @return
     */
    public static WebApplicationBuilder create() {
        var factory = WebBuilderFactories.create();
        return factory.create();
    }
}
