package io.github.amayaframework.core;

import io.github.amayaframework.application.ServiceManagerFactory;
import io.github.amayaframework.options.OptionSet;
import io.github.amayaframework.service.ServiceManager;
import org.slf4j.ILoggerFactory;

final class PlainManagerFactory implements ServiceManagerFactory {
    private final ILoggerFactory loggerFactory;

    PlainManagerFactory(ILoggerFactory loggerFactory) {
        this.loggerFactory = loggerFactory;
    }

    @Override
    public ServiceManager create(OptionSet options) {
        return create();
    }

    @Override
    public ServiceManager create() {
        if (loggerFactory == null) {
            return new PlainServiceManager();
        }
        var logger = loggerFactory.getLogger(LogNames.SERVICE_MANAGER);
        return new LoggingServiceManager(logger);
    }
}
