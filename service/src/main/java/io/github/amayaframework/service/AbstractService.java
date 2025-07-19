package io.github.amayaframework.service;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractService implements Service {
    protected final Object lifecycleLock;
    protected AtomicInteger state;

    protected AbstractService(Object lifecycleLock) {
        this.lifecycleLock = lifecycleLock;
        this.state = new AtomicInteger(ServiceState.NEW);
    }

    protected AbstractService() {
        this(new Object());
    }

    @Override
    public int state() {
        return state.get();
    }

    protected abstract void doStart(ServiceCallback callback) throws Throwable;

    protected abstract void doStop() throws Throwable;

    protected abstract void doDispose();

    @Override
    public void start(ServiceCallback callback) throws Throwable {
        if (state.get() == ServiceState.DISPOSED) {
            throw new IllegalStateException("Cannot start disposed service");
        }
        if (state.get() == ServiceState.STARTED) {
            return;
        }
        synchronized (lifecycleLock) {
            if (state.get() == ServiceState.DISPOSED) {
                throw new IllegalStateException("Cannot start disposed service");
            }
            if (state.get() == ServiceState.STARTED) {
                return;
            }
            try {
                state.set(ServiceState.STARTING);
                doStart(callback);
                state.compareAndSet(ServiceState.STARTING, ServiceState.STARTED);
            } catch (Throwable e) {
                state.set(ServiceState.FAILED);
                throw e;
            }
        }
    }

    @Override
    public void stop() throws Throwable {
        if (state.get() == ServiceState.DISPOSED || state.get() == ServiceState.STOPPED) {
            return;
        }
        synchronized (lifecycleLock) {
            if (state.get() == ServiceState.DISPOSED || state.get() == ServiceState.STOPPED) {
                return;
            }
            try {
                state.set(ServiceState.STOPPING);
                doStop();
                state.compareAndSet(ServiceState.STOPPING, ServiceState.STOPPED);
            } catch (Throwable e) {
                state.set(ServiceState.FAILED);
                throw e;
            }
        }
    }

    @Override
    public void dispose() {
        if (state.getAndSet(ServiceState.DISPOSED) == ServiceState.DISPOSED) {
            return;
        }
        doDispose();
    }
}
