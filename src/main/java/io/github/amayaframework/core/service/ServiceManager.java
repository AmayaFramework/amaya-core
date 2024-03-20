package io.github.amayaframework.core.service;

import java.util.concurrent.Future;

public interface ServiceManager extends Service {

    ServiceCollection getServiceCollection();

    @Override
    ServiceState getState();

    @Override
    Future<?> start();

    @Override
    Future<?> stop();

    @Override
    boolean startNow() throws InterruptedException;

    @Override
    boolean stopNow() throws InterruptedException;
}
