package io.github.amayaframework.core.service;

public interface Service {

    ServiceState getState();

    boolean start() throws Throwable;

    boolean stop() throws Throwable;
}
