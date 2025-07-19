package io.github.amayaframework.service;

public interface Service extends Disposable {

    void start(ServiceCallback callback) throws Throwable;

    default void start() throws Throwable {
        start(null);
    }

    void stop() throws Throwable;

    default int state() {
        return ServiceState.UNKNOWN;
    }
}
