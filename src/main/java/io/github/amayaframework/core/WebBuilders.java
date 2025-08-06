package io.github.amayaframework.core;

import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.web.WebApplicationBuilder;

public final class WebBuilders {
    private WebBuilders() {
    }

    public static WebApplicationBuilder create(GroupOptionSet options, LoggerFactorySupplier supplier) {
        var factory = WebBuilderFactories.create(supplier);
        return factory.create(options);
    }

    public static WebApplicationBuilder create(GroupOptionSet options) {
        var factory = WebBuilderFactories.create();
        return factory.create(options);
    }

    public static WebApplicationBuilder create() {
        return WebBuilderFactories.create().create();
    }
}
