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

    }

    @Override
    public void stop() throws Throwable {

    }

    @Override
    public void dispose() {

    }
}
