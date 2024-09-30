package io.github.amayaframework.impl;

import io.github.amayaframework.core.Application;
import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.service.ServiceManager;

final class ApplicationImpl implements Application {
    private final GroupOptionSet options;
    private final Environment environment;
    private final ServiceManager manager;
    private final HttpServer server;
    private Thread hook;
    private boolean destroyed;

    ApplicationImpl(GroupOptionSet options, Environment environment, ServiceManager manager, HttpServer server) {
        this.options = options;
        this.environment = environment;
        this.manager = manager;
        this.server = server;
        this.destroyed = false;
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
    public ServiceProvider getProvider() {
        throw new UnsupportedOperationException();
    }

    private void checkDestroyed() {
        if (destroyed) {
            throw new IllegalStateException();
        }
    }

    @Override
    public void start() throws Throwable {
        checkDestroyed();
        manager.start();
        server.start();
    }

    @Override
    public void stop() throws Throwable {
        checkDestroyed();
        manager.stop();
        server.stop();
    }

    private void onExit() {
        try {
            manager.stop();
            server.stop();
            environment.close();
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private Thread getHook() {
        if (hook != null) {
            return hook;
        }
        hook = new Thread(this::onExit);
        return hook;
    }

    @Override
    public void run() throws Throwable {
        checkDestroyed();
        Runtime.getRuntime().addShutdownHook(getHook());
        start();
    }

    @Override
    public void shutdown() throws Throwable {
        checkDestroyed();
        Runtime.getRuntime().removeShutdownHook(getHook());
        manager.stop();
        server.stop();
        environment.close();
        destroyed = true;
    }
}
