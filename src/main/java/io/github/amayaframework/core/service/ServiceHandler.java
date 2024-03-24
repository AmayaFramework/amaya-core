package io.github.amayaframework.core.service;

public interface ServiceHandler {

    void start(Service service) throws Throwable;

    void stop(Service service) throws Throwable;
}
