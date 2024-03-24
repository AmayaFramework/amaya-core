package io.github.amayaframework.core.service;

public interface ServiceManager extends Service {

    ServiceCollection getServiceCollection();

    @Override
    ServiceState getState();

    @Override
    boolean start() throws Throwable;

    @Override
    boolean stop() throws Throwable;
}
