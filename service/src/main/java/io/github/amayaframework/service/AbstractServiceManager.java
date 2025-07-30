package io.github.amayaframework.service;

import com.github.romanqed.jct.CancelSource;
import com.github.romanqed.jct.CancelToken;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.Consumer;

public abstract class AbstractServiceManager extends AbstractService implements ServiceManager {
    protected final Collection<Service> services;
    protected final ManagedServiceCallback managedCallback;
    protected final Consumer<Throwable> onFailureException;
    protected volatile Consumer<Throwable> onFailure;
    protected volatile Consumer<Throwable> onHalt;

    protected AbstractServiceManager(Object lifecycleLock,
                                     CancelSource cancelSource,
                                     Collection<Service> services,
                                     Consumer<Throwable> onFailureException) {
        super(lifecycleLock, cancelSource);
        this.services = services;
        this.onFailureException = onFailureException;
        this.managedCallback = new ManagedServiceCallback(lifecycleLock);
    }

    @Override
    public void start(ServiceCallback callback) throws Throwable {
        start(null, callback);
    }

    @Override
    public void start() throws Throwable {
        start(null, EmptyServiceCallback.CALLBACK);
    }

    @Override
    public void stop() throws Throwable {
        stop(null);
    }

    protected abstract void doStart(Service service, CancelToken token, ServiceCallback callback) throws Throwable;
    protected abstract void doStop(Service service, CancelToken token) throws Throwable;
    protected abstract void doDispose(Service service);

    @Override
    public Collection<Service> services() {
        return Collections.unmodifiableCollection(services);
    }

    @Override
    public void add(Service service) {
        var serviceState = service.state();
        if (serviceState == ServiceState.DISPOSED) {
            throw new IllegalArgumentException("Cannot add disposed service");
        }
        synchronized (lifecycleLock) {
            services.add(service);
            if (serviceState == ServiceState.UNMANAGED) {
                return;
            }
            if (serviceState == ServiceState.STOPPED && state == ServiceState.STARTED) {
                try {
                    doStart(service, cancelSource.token(), managedCallback);
                } catch (Throwable e) {
                    throw new IllegalStateException("Service startup failed", e);
                }
            }
        }
    }

    @Override
    public void remove(Service service) {
        synchronized (lifecycleLock) {
            var serviceState = service.state();
            services.remove(service);
            if (serviceState == ServiceState.UNMANAGED) {
                return;
            }
            if (serviceState == ServiceState.STARTED) {
                try {
                    doStop(service, cancelSource.token());
                } catch (Throwable e) {
                    throw new IllegalStateException("Service stopping failed", e);
                }
            }
        }
    }

    @Override
    public Consumer<Throwable> onFailure() {
        return onFailure;
    }

    @Override
    public void onFailure(Consumer<Throwable> action) {
        this.onFailure = action;
    }

    @Override
    public Consumer<Throwable> onHalt() {
        return onHalt;
    }

    @Override
    public void onHalt(Consumer<Throwable> action) {
        this.onHalt = action;
    }

    protected void handleFailure(Throwable cause) {
        try {
            if (onFailure != null) {
                onFailure.accept(cause);
            }
            doStop(cancelSource.token());
        } catch (Throwable e) {
            if (onFailureException != null) {
                onFailureException.accept(e);
            }
        }
    }

    protected void handleHalt(Throwable cause) {
        if (onHalt != null) {
            onHalt.accept(cause);
        }
        doDispose();
    }

    private void doStop(Collection<Service> services, CancelToken token) throws Throwable {
        for (var service : services) {
            if (token.canceled()) {
                break;
            }
            try {
                doStop(service, token);
            } catch (Throwable e) {
                state = ServiceState.DISPOSED;
                doDispose();
                throw e;
            }
        }
    }

    @Override
    protected void doStart(CancelToken token, ServiceCallback callback) throws Throwable {
        if (token.canceled()) {
            return;
        }
        managedCallback.parent = callback;
        managedCallback.inTransition = true;
        var started = new LinkedList<Service>();
        for (var service : services) {
            if (token.canceled()) {
                break;
            }
            try {
                doStart(service, token, managedCallback);
            } catch (Throwable e) {
                state = ServiceState.FAILED;
                doStop(started, token);
                throw e;
            }
            started.add(service);
        }
        managedCallback.inTransition = false;
    }

    @Override
    protected void doStop(CancelToken token) throws Throwable {
        if (token.canceled()) {
            return;
        }
        managedCallback.inTransition = true;
        doStop(services, token);
        managedCallback.inTransition = false;
    }

    @Override
    protected void doDispose() {
        managedCallback.disposed = true;
        for (var service : services) {
            doDispose(service);
        }
    }

    protected final class ManagedServiceCallback extends AbstractServiceCallback {
        volatile ServiceCallback parent;

        ManagedServiceCallback(Object lifecycleLock) {
            super(lifecycleLock);
        }

        @Override
        public void fail(Throwable cause) {
            if (parent != null) {
                parent.fail(cause);
            }
            if (disposed) {
                return;
            }
            if (state.isStopped()) {
                return;
            }
            synchronized (lifecycleLock) {
                if (disposed) {
                    return;
                }
                if (state.isStopped()) {
                    return;
                }
                if (inTransition) {
                    throw new IllegalStateException("Service failed", cause);
                }
                handleFailure(cause);
            }
        }

        @Override
        public void halt(Throwable cause) {
            if (parent != null) {
                parent.halt(cause);
            }
            if (disposed) {
                return;
            }
            if (state.isStopped()) {
                return;
            }
            synchronized (lifecycleLock) {
                if (disposed) {
                    return;
                }
                if (state.isStopped()) {
                    return;
                }
                if (inTransition) {
                    throw new IllegalStateException("Service halted", cause);
                }
                handleHalt(cause);
            }
        }
    }
}
