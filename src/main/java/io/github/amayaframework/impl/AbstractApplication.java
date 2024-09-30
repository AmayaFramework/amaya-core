package io.github.amayaframework.impl;

import io.github.amayaframework.core.Application;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.service.ServiceManager;

public abstract class AbstractApplication implements Application {
    protected final GroupOptionSet options;
    protected final Environment environment;
    protected final ServiceManager manager;
    protected final HttpServer server;
    protected final Object lock;

    protected boolean shutdown;
    protected Thread hook;

    protected AbstractApplication(GroupOptionSet options,
                                  Environment environment,
                                  ServiceManager manager,
                                  HttpServer server) {
        this.options = options;
        this.environment = environment;
        this.manager = manager;
        this.server = server;
        this.lock = new Object();
    }

    @Override
    public GroupOptionSet getOptions() {
        return options;
    }

    @Override
    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public ServiceManager getManager() {
        return manager;
    }

    @Override
    public HttpServer getServer() {
        return server;
    }

    @Override
    public void start() throws Throwable {
        synchronized (lock) {
            this.manager.start();
            this.server.start();
        }
    }

    @Override
    public void stop() throws Throwable {
        synchronized (lock) {
            this.server.stop();
            this.manager.stop();
        }
    }

    @Override
    public void run() throws Throwable {

    }

    @Override
    public void shutdown() throws Throwable {

    }
}
