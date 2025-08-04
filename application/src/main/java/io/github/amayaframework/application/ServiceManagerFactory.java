package io.github.amayaframework.application;

import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.OptionSet;
import io.github.amayaframework.service.ServiceManager;

public interface ServiceManagerFactory {

    default ServiceManager create(OptionSet options, Environment env) {
        return create(options);
    }

    ServiceManager create(OptionSet options);

    default ServiceManager create(Environment env) {
        return create();
    }

    ServiceManager create();
}
