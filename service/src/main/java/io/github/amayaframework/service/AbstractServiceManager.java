package io.github.amayaframework.service;

import com.github.romanqed.jfunc.Runnable1;

import java.util.Collection;
import java.util.Collections;

public abstract class AbstractServiceManager extends AbstractService implements ServiceManager {
    protected final Collection<Service> services;
    protected final ManagedServiceCallback callback;
    protected Runnable1<Throwable> onFailure;
    protected Runnable1<Throwable> onHalt;

    protected AbstractServiceManager(Object lifecycleLock, Collection<Service> services) {
        super(lifecycleLock);
        this.services = services;
        this.callback = new ManagedServiceCallback(lifecycleLock);
    }

    protected abstract void doStart(Service service, ServiceCallback callback) throws Throwable;
    protected abstract void doStop(Service service) throws Throwable;
    protected abstract void doDispose(Service service) throws Throwable;

    @Override
    public Collection<Service> services() {
        return Collections.unmodifiableCollection(services);
    }

    @Override
    public void add(Service service) {
        var state = service.state();
        if (state == ServiceState.DISPOSED) {
            throw new IllegalArgumentException("Cannot add disposed service");
        }
        synchronized (lifecycleLock) {
            services.add(service);
            if (state == ServiceState.UNMANAGED) {
                return;
            }
            if (state == ServiceState.STOPPED) {
                try {
                    doStart(service, callback);
                } catch (Throwable e) {
                    throw new IllegalStateException("Service startup failed", e);
                }
            }
        }
    }

    @Override
    public void remove(Service service) {
        synchronized (lifecycleLock) {
            var state = service.state();
            services.remove(service);
            if (state == ServiceState.UNMANAGED) {
                return;
            }
            if (state == ServiceState.STARTED) {
                try {
                    doStop(service);
                } catch (Throwable e) {
                    throw new IllegalStateException("Service stopping failed", e);
                }
            }
        }
    }

    @Override
    public Runnable1<Throwable> onFailure() {
        return onFailure;
    }

    @Override
    public void onFailure(Runnable1<Throwable> action) {
        this.onFailure = action;
    }

    @Override
    public Runnable1<Throwable> onHalt() {
        return onHalt;
    }

    @Override
    public void onHalt(Runnable1<Throwable> action) {
        this.onHalt = action;
    }

    protected void handleOnFailureException(Throwable e) {
        // By default, do nothing
    }

    protected void handleFailure(Throwable e) {

    }

    protected final class ManagedServiceCallback extends AbstractServiceCallback {
        protected ManagedServiceCallback(Object lifecycleLock) {
            super(lifecycleLock);
        }

        @Override
        public void fail(Throwable cause) {
            if (state.get() == ServiceState.DISPOSED) {
                return;
            }
            if (inTransition) {
                throw new IllegalStateException("Service failed", cause);
            }
            synchronized (lifecycleLock) {
                handleFailure(cause);
            }
        }

        @Override
        public void halt(Throwable cause) {

        }
    }
}
