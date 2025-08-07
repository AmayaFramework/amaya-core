package io.github.amayaframework.core;

import io.github.amayaframework.environment.NativeEnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.web.WebApplicationBuilder;
import org.slf4j.ILoggerFactory;

/**
 * A simple {@link WebBuilderFactory} implementation that creates plain builders
 * with no special dependency injection, using default environment and services.
 */
public final class PlainWebBuilderFactory implements WebBuilderFactory {
    private final ILoggerFactory loggerFactory;

    /**
     * Constructs a PlainWebBuilderFactory with a logger factory.
     *
     * @param loggerFactory the SLF4J logger factory instance, must be non-null
     */
    public PlainWebBuilderFactory(ILoggerFactory loggerFactory) {
        this.loggerFactory = loggerFactory;
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
        var servicesBuilder = new PlainServicesBuilder(managerFactory);
        return new PlainWebBuilder(servicesBuilder, envFactory, loggerFactory);
    }
}
