package io.github.amayaframework.core.service;

public abstract class AbstractService implements Service {
    protected volatile ServiceState state;
    protected final Object lock;

    protected AbstractService(ServiceState initial) {
        this.state = initial;
        this.lock = new Object();
    }

    protected abstract void checkedStart() throws Throwable;

    protected abstract void checkedStop() throws Throwable;

    @Override
    public ServiceState getState() {
        return state;
    }

    private void innerStart() throws Throwable {
        try {
            checkedStart();
            state = ServiceState.STARTED;
        } catch (Throwable e) {
            state = ServiceState.FAILED;
            throw e;
        }
    }

    private void innerStop() throws Throwable {
        try {
            checkedStop();
            state = ServiceState.STOPPED;
        } catch (Throwable e) {
            state = ServiceState.FAILED;
            throw e;
        }
    }

    @Override
    public boolean start() throws Throwable {
        // Cannot start service, if it already started
        // Double-check style
        if (state == ServiceState.STARTED) {
            return false;
        }
        synchronized (lock) {
            if (state == ServiceState.STARTED) {
                return false;
            }
            innerStart();
            return true;
        }
    }

    @Override
    public boolean stop() throws Throwable {
        // Cannot stop service, if it already stopped or failed
        // Double-check style
        if (state != ServiceState.STARTED) {
            return false;
        }
        synchronized (lock) {
            if (state != ServiceState.STARTED) {
                return false;
            }
            innerStop();
            return true;
        }
    }
}
