package io.github.amayaframework.core;

import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.service.ServiceManager;
import io.github.amayaframework.web.AbstractWebApplication;

final class StandaloneApplication extends AbstractWebApplication {

    StandaloneApplication(GroupOptionSet options, Environment environment, ServiceManager manager, HttpServer server) {
        super(options, environment, manager, server);
    }

    @Override
    public ServiceProvider getProvider() {
        // No-op impl
        return null;
    }
}
