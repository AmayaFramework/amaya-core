package io.github.amayaframework.core;

import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.web.WebApplicationBuilder;
import io.github.amayaframework.web.WebBuilderFactory;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * A factory implementation that creates web applications integrated with the amaya di module.
 */
public final class ProvidedBuilderFactory implements WebBuilderFactory {
    private final Supplier<ServiceProviderBuilder> supplier;

    /**
     * Constructs a {@link ProvidedBuilderFactory} instance with given {@link ServiceProviderBuilder} supplier.
     *
     * @param supplier the specified {@link ServiceProviderBuilder} supplier, must be non-null
     */
    public ProvidedBuilderFactory(Supplier<ServiceProviderBuilder> supplier) {
        this.supplier = Objects.requireNonNull(supplier);
    }

    @Override
    public WebApplicationBuilder create(GroupOptionSet options) {
        var ret = create();
        ret.setOptions(options);
        return ret;
    }

    @Override
    public WebApplicationBuilder create() {
        var managerBuilder = new ProvidedManagerBuilder(Defaults.DEFAULT_MANAGER_FACTORY, Defaults.DEFAULT_HANDLER);
        return new ProvidedApplicationBuilder(managerBuilder, supplier, Defaults.DEFAULT_ENVIRONMENT_FACTORY);
    }
}
