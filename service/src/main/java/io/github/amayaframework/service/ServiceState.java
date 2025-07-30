package io.github.amayaframework.service;

public enum ServiceState {
    UNMANAGED(false),
    NEW(true),
    DISPOSED(true),
    FAILED(true),
    STARTING(false),
    STARTED(false),
    STOPPING(false),
    STOPPED(true);

    private final boolean isStopped;

    ServiceState(boolean isStopped) {
        this.isStopped = isStopped;
    }

    public boolean isStopped() {
        return isStopped;
    }
}
