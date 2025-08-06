package io.github.amayaframework.core;

import io.github.amayaframework.application.ServiceManagerFactory;
import io.github.amayaframework.options.OptionSet;
import io.github.amayaframework.service.Service;
import io.github.amayaframework.service.ServiceManager;
import org.slf4j.ILoggerFactory;

import java.util.Map;
import java.util.function.Supplier;

final class PlainManagerFactory implements ServiceManagerFactory {
    private final Supplier<Map<Service, ?>> mapSupplier;
    private final ILoggerFactory loggerFactory;

    PlainManagerFactory(Supplier<Map<Service, ?>> mapSupplier, ILoggerFactory loggerFactory) {
        this.mapSupplier = mapSupplier;
        this.loggerFactory = loggerFactory;
    }

    @Override
    public ServiceManager create(OptionSet options) {
        return create();
    }

    @Override
    public ServiceManager create() {
        if (loggerFactory == null) {
            return new PlainServiceManager(mapSupplier);
        }
        var logger = loggerFactory.getLogger(LogNames.SERVICE_MANAGER);
        return new LoggingServiceManager(logger, mapSupplier);
    }
}
