package io.github.amayaframework.core;

import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.service.ServiceManager;

final class StandaloneApplication extends AbstractApplication {

    StandaloneApplication(GroupOptionSet options, Environment environment, ServiceManager manager, HttpServer server) {
        super(options, environment, manager, server);
    }

    @Override
    public ServiceProvider getProvider() {
        throw new UnsupportedOperationException("The amaya-di module is not loaded");
    }
}
