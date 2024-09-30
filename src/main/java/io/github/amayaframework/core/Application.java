package io.github.amayaframework.core;

import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.service.ServiceManager;

public interface Application {

    GroupOptionSet getOptions();

    Environment getEnvironment();

    ServiceManager getManager();

    HttpServer getServer();

    ServiceProvider getProvider();

    void start() throws Throwable;

    void stop() throws Throwable;

    void run() throws Throwable;

    void shutdown() throws Throwable;
}
