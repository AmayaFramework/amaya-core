package io.github.amayaframework.core;

import io.github.amayaframework.di.ScopedProviderBuilder;
import io.github.amayaframework.environment.NativeEnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.web.WebApplicationBuilder;
import org.slf4j.ILoggerFactory;

import java.util.function.Supplier;

/**
 * A {@link WebBuilderFactory} implementation that provides builders
 * with injected dependencies such as a scoped provider builder supplier and logger factory.
 */
public final class ProvidedWebBuilderFactory implements WebBuilderFactory {
    private final Supplier<ScopedProviderBuilder> builderSupplier;
    private final ILoggerFactory loggerFactory;

    /**
     * Constructs a ProvidedWebBuilderFactory with given dependencies.
     *
     * @param builderSupplier a supplier for {@link ScopedProviderBuilder} instances, must be non-null
     * @param loggerFactory   the SLF4J logger factory instance, must be non-null
     */
    public ProvidedWebBuilderFactory(Supplier<ScopedProviderBuilder> builderSupplier, ILoggerFactory loggerFactory) {
        this.loggerFactory = loggerFactory;
        this.builderSupplier = builderSupplier;
    }

    @Override
    public WebApplicationBuilder create(GroupOptionSet options) {
        var ret = create();
        ret.options(options);
        return ret;
    }

    @Override
    public WebApplicationBuilder create() {
        var managerFactory = new PlainManagerFactory(loggerFactory);
        var envFactory = new NativeEnvironmentFactory();
        var servicesBuilder = new ProvidedServicesBuilder(managerFactory);
        return new ProvidedWebBuilder(servicesBuilder, envFactory, builderSupplier, loggerFactory);
    }
}
