package io.github.amayaframework.core;

import io.github.amayaframework.di.ProviderBuilders;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.options.GroupOptionSet;

import java.util.function.Supplier;

public final class CoreBuilders {
    private CoreBuilders() {
    }

    public static ApplicationBuilder createStandalone(GroupOptionSet options) {
        var factory = new StandaloneBuilderFactory();
        return factory.create(options);
    }

    public static ApplicationBuilder createStandalone() {
        var factory = new StandaloneBuilderFactory();
        return factory.create();
    }

    public static ApplicationBuilder createProvided(Supplier<ServiceProviderBuilder> supplier, GroupOptionSet options) {
        var factory = new ServiceBuilderFactory(supplier);
        return factory.create(options);
    }

    public static ApplicationBuilder createProvided(Supplier<ServiceProviderBuilder> supplier) {
        var factory = new ServiceBuilderFactory(supplier);
        return factory.create();
    }

    public static ApplicationBuilder createProvided(GroupOptionSet options) {
        var factory = new ServiceBuilderFactory(ProviderBuilders::createChecked);
        return factory.create(options);
    }

    public static ApplicationBuilder createProvided() {
        var factory = new ServiceBuilderFactory(ProviderBuilders::createChecked);
        return factory.create();
    }

    public static ApplicationBuilder create(GroupOptionSet options) {
        var factory = CoreBuilderFactories.create();
        return factory.create(options);
    }

    public static ApplicationBuilder create() {
        var factory = CoreBuilderFactories.create();
        return factory.create();
    }
}
