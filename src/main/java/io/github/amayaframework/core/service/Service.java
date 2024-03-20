package io.github.amayaframework.core.service;

import java.util.concurrent.Future;

public interface Service {

    ServiceState getState();

    Future<?> start();

    Future<?> stop();

    boolean startNow();

    boolean stopNow();
}
