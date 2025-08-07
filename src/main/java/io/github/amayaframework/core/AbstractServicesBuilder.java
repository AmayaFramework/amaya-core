package io.github.amayaframework.core;

import io.github.amayaframework.application.AbstractServicesConfigurer;
import io.github.amayaframework.application.ServiceManagerFactory;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.OptionSet;
import io.github.amayaframework.service.ServiceManager;

abstract class AbstractServicesBuilder extends AbstractServicesConfigurer implements ServicesBuilder {
    protected final ServiceManagerFactory defaultFactory;

    protected AbstractServicesBuilder(ServiceManagerFactory defaultFactory) {
        this.defaultFactory = defaultFactory;
    }

    @Override
    public ServiceManager build(OptionSet options, Environment env) {
        try {
            var factory = this.factory == null ? defaultFactory : this.factory;
            var ret = factory.create(options, env);
            if (services != null) {
                ret.add(services);
            }
            return ret;
        } finally {
            reset();
        }
    }
}
