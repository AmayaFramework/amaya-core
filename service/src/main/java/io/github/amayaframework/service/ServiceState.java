package io.github.amayaframework.service;

public final class ServiceState {
    private ServiceState() {
    }

    public static final int UNKNOWN = 0;
    public static final int NEW = 1;
    public static final int DISPOSED = 2;
    public static final int FAILED = 3;
    public static final int STARTING = 4;
    public static final int STARTED = 5;
    public static final int STOPPING = 6;
    public static final int STOPPED = 7;
}
