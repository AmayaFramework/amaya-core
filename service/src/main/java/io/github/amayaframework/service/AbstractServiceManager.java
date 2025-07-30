package io.github.amayaframework.service;

import com.github.romanqed.jct.CancelSource;
import com.github.romanqed.jct.CancelToken;
import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Runnable0;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractServiceManager extends AbstractService implements ServiceManager {
    protected final Supplier<Map<Service, ManagedServiceCallback>> supplier;
    protected final ManagedCallbackBase callbackBase;
    protected final Consumer<Throwable> onFailureException;
    protected volatile Map<Service, ManagedServiceCallback> services;
    protected volatile Consumer<Throwable> onFailure;
    protected volatile Consumer<Throwable> onHalt;

    protected AbstractServiceManager(Object lifecycleLock,
                                     CancelSource cancelSource,
                                     Supplier<Map<Service, ManagedServiceCallback>> supplier,
                                     Consumer<Throwable> onFailureException) {
        super(lifecycleLock, cancelSource);
        this.supplier = supplier;
        this.services = null;
        this.onFailureException = onFailureException;
        this.callbackBase = new ManagedCallbackBase(this);
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

    protected void ensureServices() {
        if (services == null) {
            services = supplier.get();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Service> services() {
        return services == null ? Collections.EMPTY_LIST : Collections.unmodifiableCollection(services.keySet());
    }

    protected void handleAddedService(Service service, ServiceCallback callback) {
        var serviceState = service.state();
        if (serviceState == ServiceState.UNMANAGED) {
            return;
        }
        if (serviceState == ServiceState.STOPPED && state == ServiceState.STARTED) {
            try {
                doStart(service, cancelSource.token(), callback);
            } catch (Throwable e) {
                throw new IllegalStateException("Service startup failed", e);
            }
        }
    }

    @Override
    public void add(Service service) {
        if (state == ServiceState.DISPOSED) {
            throw new IllegalArgumentException("Manager is disposed");
        }
        if (service == null) {
            return;
        }
        if (service.state() == ServiceState.DISPOSED) {
            throw new IllegalArgumentException("Cannot add disposed service");
        }
        synchronized (lifecycleLock) {
            ensureServices();
            var callback = new ManagedServiceCallback(callbackBase);
            services.put(service, callback);
            handleAddedService(service, callback);
        }
    }

    @Override
    public void add(Iterable<Service> services) {
        if (state == ServiceState.DISPOSED) {
            throw new IllegalArgumentException("Manager is disposed");
        }
        if (services == null) {
            return;
        }
        var iterator = services.iterator();
        if (!iterator.hasNext()) {
            return;
        }
        synchronized (lifecycleLock) {
            ensureServices();
            while (iterator.hasNext()) {
                var service = iterator.next();
                if (service.state() == ServiceState.DISPOSED) {
                    throw new IllegalArgumentException("Cannot add disposed service");
                }
                var callback = new ManagedServiceCallback(callbackBase);
                this.services.put(service, callback);
                handleAddedService(service, callback);
            }
        }
    }

    protected void handleRemovedService(Service service) {
        var serviceState = service.state();
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

    @Override
    public void remove(Service service) {
        if (state == ServiceState.DISPOSED) {
            throw new IllegalArgumentException("Manager is disposed");
        }
        if (service == null || services == null) {
            return;
        }
        synchronized (lifecycleLock) {
            if (services == null) {
                return;
            }
            var callback = services.remove(service);
            callback.reset();
            handleRemovedService(service);
        }
    }

    @Override
    public void remove(Iterable<Service> services) {
        if (state == ServiceState.DISPOSED) {
            throw new IllegalArgumentException("Manager is disposed");
        }
        if (services == null || this.services == null) {
            return;
        }
        var iterator = services.iterator();
        if (!iterator.hasNext()) {
            return;
        }
        synchronized (lifecycleLock) {
            if (this.services == null) {
                return;
            }
            while (iterator.hasNext()) {
                var service = iterator.next();
                var callback = this.services.remove(service);
                callback.reset();
                handleRemovedService(service);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Service> removeAll() {
        if (state == ServiceState.DISPOSED) {
            throw new IllegalArgumentException("Manager is disposed");
        }
        if (services == null) {
            return Collections.EMPTY_LIST;
        }
        synchronized (lifecycleLock) {
            if (services == null) {
                return Collections.EMPTY_LIST;
            }
            try {
                if (state == ServiceState.STARTED) {
                    doStop(services.keySet(), cancelSource.token());
                }
            } catch (Throwable e) {
                throw new IllegalStateException("Services stopping failed", e);
            }
            for (var callback : services.values()) {
                callback.reset();
            }
            var ret = services.keySet();
            services = null;
            return Collections.unmodifiableCollection(ret);
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
        if (services == null || token.canceled()) {
            return;
        }
        callbackBase.in(callback);
        var started = new LinkedList<Service>();
        for (var entry : services.entrySet()) {
            if (token.canceled()) {
                break;
            }
            var service = entry.getKey();
            try {
                doStart(service, token, entry.getValue());
            } catch (Throwable e) {
                state = ServiceState.FAILED;
                doStop(started, token);
                throw e;
            }
            started.add(service);
        }
        callbackBase.out();
    }

    @Override
    protected void doStop(CancelToken token) throws Throwable {
        if (services == null || token.canceled()) {
            return;
        }
        callbackBase.in();
        doStop(services.keySet(), token);
        callbackBase.out();
    }

    @Override
    protected void doDispose() {
        callbackBase.dispose();
        if (services == null) {
            return;
        }
        for (var service : services.keySet()) {
            doDispose(service);
        }
        services = null;
    }

    protected void handleFailure(Throwable cause) {
        try {
            if (onFailure != null) {
                onFailure.accept(cause);
            }
            doStop(services.keySet(), cancelSource.token());
        } catch (Throwable e) {
            if (onFailureException != null) {
                onFailureException.accept(e);
            }
        } finally {
            state = ServiceState.FAILED;
        }
    }

    protected void handleHalt(Throwable cause) {
        if (onHalt != null) {
            onHalt.accept(cause);
        }
        doDispose();
        state = ServiceState.DISPOSED;
    }

    protected interface CallbackBase {

        void dispose();

        void doExclusive(Runnable0 action) throws Throwable;

        void handleFailure(Throwable cause);

        void handleHalt(Throwable cause);
    }

    protected static final class EmptyCallbackBase implements CallbackBase {
        static final EmptyCallbackBase CALLBACK_BASE = new EmptyCallbackBase();

        @Override
        public void dispose() {
            // Do nothing
        }

        @Override
        public void doExclusive(Runnable0 action) throws Throwable {
            action.run();
        }

        @Override
        public void handleFailure(Throwable cause) {
            // Do nothing
        }

        @Override
        public void handleHalt(Throwable cause) {
            // Do nothing
        }
    }

    protected static final class ManagedCallbackBase implements CallbackBase {
        final AbstractServiceManager manager;
        volatile ServiceCallback parent;
        volatile boolean disposed;
        boolean inTransition;

        ManagedCallbackBase(AbstractServiceManager manager) {
            this.manager = manager;
        }

        void in(ServiceCallback parent) {
            this.parent = parent;
            this.inTransition = true;
        }

        void in() {
            this.inTransition = true;
        }

        void out() {
            this.inTransition = false;
        }

        @Override
        public void dispose() {
            this.disposed = true;
            this.parent = null;
        }

        @Override
        public void doExclusive(Runnable0 action) throws Throwable {
            synchronized (manager.lifecycleLock) {
                action.run();
            }
        }

        @Override
        public void handleFailure(Throwable cause) {
            if (parent != null) {
                parent.fail(cause);
            }
            if (disposed || manager.state.isStopped()) {
                return;
            }
            synchronized (manager.lifecycleLock) {
                if (disposed || manager.state.isStopped()) {
                    return;
                }
                if (inTransition) {
                    throw new IllegalStateException("Service failed", cause);
                }
                manager.handleFailure(cause);
            }
        }

        @Override
        public void handleHalt(Throwable cause) {
            if (parent != null) {
                parent.halt(cause);
            }
            if (disposed || manager.state.isStopped()) {
                return;
            }
            synchronized (manager.lifecycleLock) {
                if (disposed || manager.state.isStopped()) {
                    return;
                }
                if (inTransition) {
                    throw new IllegalStateException("Service halted", cause);
                }
                manager.handleHalt(cause);
            }
        }
    }

    protected static final class ManagedServiceCallback implements ServiceCallback {
        volatile CallbackBase base;

        ManagedServiceCallback(CallbackBase base) {
            this.base = base;
        }

        void reset() {
            this.base.dispose();
            this.base = EmptyCallbackBase.CALLBACK_BASE;
        }

        @Override
        public void fail(Throwable cause) {
            base.handleFailure(cause);
        }

        @Override
        public void halt(Throwable cause) {
            base.handleHalt(cause);
        }

        @Override
        public void exclusive(Runnable0 action) {
            try {
                base.doExclusive(action);
            } catch (Throwable e) {
                Exceptions.throwAny(e);
            }
        }
    }
}
