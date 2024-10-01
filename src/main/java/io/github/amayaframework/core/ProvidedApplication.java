package io.github.amayaframework.core;

import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.service.ServiceManager;

final class ProvidedApplication extends AbstractApplication {
    private final ServiceProvider provider;

    ProvidedApplication(GroupOptionSet options,
                        Environment environment,
                        ServiceManager manager,
                        HttpServer server,
                        ServiceProvider provider) {
        super(options, environment, manager, server);
        this.provider = provider;
    }

    @Override
    public ServiceProvider getProvider() {
        return provider;
    }
}