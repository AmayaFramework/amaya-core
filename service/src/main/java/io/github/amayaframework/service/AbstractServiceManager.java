package io.github.amayaframework.service;

import com.github.romanqed.jct.CancelSource;
import com.github.romanqed.jct.CancelToken;
import com.github.romanqed.jct.EmptyCancelToken;
import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Runnable1;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Base abstract implementation of {@link ServiceManager}.
 * <p>
 * Manages a collection of {@link Service} instances with lifecycle control,
 * propagating start, stop, and dispose operations to managed services.
 * <p>
 * Handles service addition/removal, failure and halt events, and exposes hooks
 * for customization via {@link #doStart(Service, CancelToken, ServiceCallback)},
 * {@link #doStop(Service, CancelToken)} and {@link #doDispose(Service)}.
 * <p>
 * Uses internal callbacks to coordinate service state changes and failure handling.
 */
public abstract class AbstractServiceManager extends AbstractService implements ServiceManager {
    /**
     * A supplier that creates a new {@link Map} binding each {@link Service}
     * to its corresponding {@link ManagedServiceCallback}. This is used for initialization
     * and recovery after failures.
     */
    protected final Supplier<Map<Service, ManagedServiceCallback>> supplier;

    /**
     * The base callback used to delegate management actions (such as halting or failing)
     * from individual services back to the service manager.
     */
    protected final ManagedCallbackBase callbackBase;

    /**
     * A handler for exceptions thrown during failure processing. It is invoked only
     * if an exception occurs while processing {@code onFailure}.
     * This handler must not throw.
     */
    protected final Consumer<Throwable> onFailureException;

    /**
     * A map of currently managed services and their associated callbacks.
     * This map is volatile and may be rebuilt on restart.
     */
    protected volatile Map<Service, ManagedServiceCallback> services;

    /**
     * A callback that is triggered when a managed service fails (transitions to {@code FAILED}).
     * Must not throw.
     */
    protected volatile Consumer<Throwable> onFailure;

    /**
     * A callback that is triggered when a managed service is halted due to stop or cancellation.
     * Must not throw.
     */
    protected volatile Consumer<Throwable> onHalt;

    /**
     * Constructs a new service manager with given synchronization primitives and callbacks.
     *
     * @param lifecycleLock      lock object used for lifecycle synchronization
     * @param cancelSource       cancellation source controlling cancellation tokens
     * @param supplier           supplier for the map of managed services and their callbacks
     * @param onFailureException consumer for exceptions thrown during failure handling
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected AbstractServiceManager(Object lifecycleLock,
                                     CancelSource cancelSource,
                                     Supplier<Map<Service, ?>> supplier,
                                     Consumer<Throwable> onFailureException) {
        super(lifecycleLock, cancelSource);
        this.supplier = (Supplier<Map<Service, ManagedServiceCallback>>) (Supplier) supplier;
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

    /**
     * Starts the given service with the specified cancellation token and callback.
     *
     * @param service  the service to start
     * @param token    the cancellation token to observe
     * @param callback the service callback for lifecycle events
     * @throws Throwable if starting the service fails
     */
    protected abstract void doStart(Service service, CancelToken token, ServiceCallback callback) throws Throwable;

    /**
     * Stops the given service with the specified cancellation token.
     *
     * @param service the service to stop
     * @param token   the cancellation token to observe
     * @throws Throwable if stopping the service fails
     */
    protected abstract void doStop(Service service, CancelToken token) throws Throwable;

    /**
     * Disposes the given service, releasing resources.
     *
     * @param service the service to dispose
     */
    protected abstract void doDispose(Service service);

    /**
     * Ensures the internal map of services is initialized.
     */
    protected void ensureServices() {
        if (services == null) {
            services = supplier.get();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Service> services() {
        var ret = services;
        if (ret == null) {
            return Collections.EMPTY_LIST;
        }
        return Collections.unmodifiableCollection(ret.keySet());
    }

    /**
     * Handles logic when adding a new service,
     * e.g. starting the service if manager is started and service is stopped.
     *
     * @param service  the added service
     * @param callback associated service callback
     */
    protected void handleAddingService(Service service, ManagedServiceCallback callback) {
        var serviceState = service.state();
        if (serviceState == ServiceState.UNMANAGED) {
            return;
        }
        if (serviceState.isStopped() && state == ServiceState.STARTED) {
            try {
                doStart(service, cancelSource.token(), callback);
            } catch (Throwable e) {
                callback.reset();
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
            if (services.containsKey(service)) {
                return;
            }
            var callback = new ManagedServiceCallback(callbackBase);
            handleAddingService(service, callback);
            services.put(service, callback);
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
                if (this.services.containsKey(service)) {
                    continue;
                }
                var callback = new ManagedServiceCallback(callbackBase);
                handleAddingService(service, callback);
                this.services.put(service, callback);
            }
        }
    }

    /**
     * Handles logic after removing a service,
     * e.g. stopping the service if it was started.
     *
     * @param service the removed service
     */
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
            if (callback != null) {
                callback.reset();
                handleRemovedService(service);
            }
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
                if (service == null) {
                    continue;
                }
                var callback = this.services.remove(service);
                if (callback != null) {
                    callback.reset();
                    handleRemovedService(service);
                }
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
            for (var callback : services.values()) {
                callback.reset();
            }
            var ret = services.keySet();
            services = null;
            try {
                if (state == ServiceState.STARTED) {
                    doStop(ret, cancelSource.token());
                }
            } catch (Throwable e) {
                throw new IllegalStateException("Services stopping failed", e);
            }
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
        try {
            for (var entry : services.entrySet()) {
                if (token.canceled()) {
                    break;
                }
                var service = entry.getKey();
                doStart(service, token, entry.getValue());
                started.add(service);
            }
        } catch (Throwable e) {
            state = ServiceState.FAILED;
            doStop(started, token);
            throw e;
        } finally {
            callbackBase.out();
        }
    }

    @Override
    protected void doStop(CancelToken token) throws Throwable {
        if (services == null || token.canceled()) {
            return;
        }
        callbackBase.in();
        try {
            doStop(services.keySet(), token);
        } finally {
            callbackBase.out();
        }
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

    /**
     * Handles failure events from managed services,
     * invokes failure listeners and attempts to stop all services.
     *
     * @param cause the cause of the failure
     */
    protected void handleFailure(Throwable cause) {
        try {
            if (onFailure != null) {
                onFailure.accept(cause);
            }
            doStop(services.keySet(), cancelSource.token());
            state = ServiceState.FAILED;
        } catch (Throwable e) {
            if (onFailureException != null) {
                onFailureException.accept(e);
            }
            doDispose();
            state = ServiceState.DISPOSED;
        }
    }

    /**
     * Handles halt events from managed services,
     * invokes halt listeners and disposes all services.
     *
     * @param cause the cause of the halt
     */
    protected void handleHalt(Throwable cause) {
        cancelSource.cancel();
        try {
            if (onHalt != null) {
                onHalt.accept(cause);
            }
        } catch (Throwable ignored) {
            // onHalt must not throw exceptions
        }
        doDispose();
        state = ServiceState.DISPOSED;
    }

    /**
     * Base interface for internal callback handling.
     */
    protected interface CallbackBase {

        /**
         * Executes an exclusive action with cancellation token.
         *
         * @param action action to run
         * @throws Throwable any exception thrown by the action
         */
        void doExclusive(Runnable1<CancelToken> action) throws Throwable;

        /**
         * Handles failure event with the given cause.
         *
         * @param cause failure cause
         */
        void handleFailure(Throwable cause);

        /**
         * Handles halt event with the given cause.
         *
         * @param cause halt cause
         */
        void handleHalt(Throwable cause);
    }

    /**
     * Empty implementation of {@link CallbackBase} performing no actions.
     */
    protected static final class EmptyCallbackBase implements CallbackBase {

        /**
         * Singleton instance of empty callback base.
         */
        public static final EmptyCallbackBase CALLBACK_BASE = new EmptyCallbackBase();

        @Override
        public void doExclusive(Runnable1<CancelToken> action) throws Throwable {
            action.run(EmptyCancelToken.TOKEN);
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

    /**
     * Callback base implementation that manages failure and halt propagation
     * to the {@link AbstractServiceManager} and user callbacks.
     * <p>
     * This class is reused by all managed services and handles lifecycle transitions
     * and callback forwarding in a thread-safe way. All logic is protected by the manager's
     * internal lock.
     */
    protected static final class ManagedCallbackBase implements CallbackBase {
        private final AbstractServiceManager manager;
        private volatile ServiceCallback parent;
        private volatile boolean disposed;
        private boolean inTransition;

        /**
         * Creates a new callback base for the given service manager.
         *
         * @param manager the manager to which this callback is bound
         */
        ManagedCallbackBase(AbstractServiceManager manager) {
            this.manager = manager;
        }

        /**
         * Signals entry into a managed service lifecycle phase,
         * associating this callback with the given {@link ServiceCallback}.
         * Sets the {@code inTransition} flag to {@code true}.
         *
         * @param parent the parent callback associated with a specific service
         */
        public void in(ServiceCallback parent) {
            this.parent = parent;
            this.inTransition = true;
        }

        /**
         * Signals entry into a transition phase without a parent callback.
         * Sets the {@code inTransition} flag to {@code true}.
         */
        public void in() {
            this.inTransition = true;
        }

        /**
         * Signals exit from a transition phase.
         * Resets the {@code inTransition} flag to {@code false}.
         */
        public void out() {
            this.inTransition = false;
        }

        /**
         * Disposes callback base.
         * Resets the {@code parent} to null and sets the {@code disposed} flag to {@code true}.
         */
        public void dispose() {
            this.disposed = true;
            this.parent = null;
        }

        @Override
        public void doExclusive(Runnable1<CancelToken> action) throws Throwable {
            synchronized (manager.lifecycleLock) {
                action.run(manager.cancelSource.token());
            }
        }

        @Override
        public void handleFailure(Throwable cause) {
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
                if (parent != null) {
                    parent.fail(cause);
                }
            }
        }

        @Override
        public void handleHalt(Throwable cause) {
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
                if (parent != null) {
                    parent.halt(cause);
                }
            }
        }
    }

    /**
     * Service callback implementation delegating to a {@link CallbackBase}.
     * <p>
     * This class wraps a callback base and exposes the {@link ServiceCallback} interface,
     * enabling callback forwarding to the manager. After reset, it becomes inert.
     */
    protected static final class ManagedServiceCallback implements ServiceCallback {
        private volatile CallbackBase base;

        /**
         * Creates a new wrapper over the given callback base.
         *
         * @param base the callback base to delegate to
         */
        public ManagedServiceCallback(CallbackBase base) {
            this.base = base;
        }

        /**
         * Disposes the current base and replaces it with a no-op implementation.
         * <p>
         * After reset, all subsequent operations on this instance become inert.
         */
        public void reset() {
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
        public void exclusive(Runnable1<CancelToken> action) {
            try {
                base.doExclusive(action);
            } catch (Throwable e) {
                Exceptions.throwAny(e);
            }
        }
    }
}
