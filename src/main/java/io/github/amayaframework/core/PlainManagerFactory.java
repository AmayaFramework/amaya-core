package io.github.amayaframework.core;

import io.github.amayaframework.application.ServiceManagerFactory;
import io.github.amayaframework.options.OptionSet;
import io.github.amayaframework.service.ServiceManager;
import org.slf4j.ILoggerFactory;

import java.util.HashMap;

public final class PlainManagerFactory implements ServiceManagerFactory {
    private final ILoggerFactory loggerFactory;

    public PlainManagerFactory(ILoggerFactory loggerFactory) {
        this.loggerFactory = loggerFactory;
    }

    @Override
    public ServiceManager create(OptionSet options) {
        // TODO HashMap <-> ConcurrentHashMap
        if (loggerFactory == null) {
            return new PlainServiceManager(HashMap::new);
        }
        var logger = loggerFactory.getLogger("TODO: ServiceManager");
        return new LoggingServiceManager(logger, HashMap::new);
    }

    @Override
    public ServiceManager create() {
        // TODO Do not touch: HashMap by default
        if (loggerFactory == null) {
            return new PlainServiceManager(HashMap::new);
        }
        var logger = loggerFactory.getLogger("TODO: ServiceManager");
        return new LoggingServiceManager(logger, HashMap::new);
    }
}
