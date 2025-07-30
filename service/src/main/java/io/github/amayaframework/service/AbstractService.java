package io.github.amayaframework.service;

import com.github.romanqed.jct.CancelSource;
import com.github.romanqed.jct.CancelToken;
import com.github.romanqed.jct.Cancellation;

/**
 * Abstract base implementation of {@link Service} managing lifecycle states,
 * cancellation tokens, and thread safety.
 * <p>
 * Provides skeleton implementations of {@link #start(CancelToken, ServiceCallback)},
 * {@link #stop(CancelToken)} and {@link #dispose()} with state transitions and
 * synchronization on an internal lifecycle lock.
 * <p>
 * Concrete subclasses must implement the actual start, stop and dispose logic
 * by overriding {@link #doStart(CancelToken, ServiceCallback)}, {@link #doStop(CancelToken)}
 * and {@link #doDispose()}.
 * <p>
 * Disposal must not throw exceptions and should be as fast as possible.
 */
public abstract class AbstractService implements Service {
    /** Lock object for synchronizing lifecycle state changes. */
    protected final Object lifecycleLock;
    /** Cancellation source controlling service cancellation tokens. */
    protected final CancelSource cancelSource;
    /** Current lifecycle state of the service. */
    protected volatile ServiceState state;

    /**
     * Constructs the service with a specified lifecycle lock and cancellation source.
     *
     * @param lifecycleLock the lock object for synchronization
     * @param cancelSource  the cancellation source for cancellation tokens
     */
    protected AbstractService(Object lifecycleLock, CancelSource cancelSource) {
        this.lifecycleLock = lifecycleLock;
        this.cancelSource = cancelSource;
        this.state = ServiceState.NEW;
    }

    /**
     * Constructs the service with default lifecycle lock and cancellation source.
     */
    protected AbstractService() {
        this(new Object(), Cancellation.source());
    }

    @Override
    public ServiceState state() {
        return state;
    }

    /**
     * Starts the service with the given cancellation token and callback.
     * <p>
     * Manages lifecycle state transitions from {@link ServiceState#NEW} or
     * {@link ServiceState#STOPPED} to {@link ServiceState#STARTED}.
     *
     * @param token    cancellation token to observe
     * @param callback service callback to notify lifecycle events
     * @throws Throwable if starting the service fails
     */
    protected abstract void doStart(CancelToken token, ServiceCallback callback) throws Throwable;

    /**
     * Stops the service with the given cancellation token.
     * <p>
     * Manages lifecycle state transitions from {@link ServiceState#STARTED} to
     * {@link ServiceState#STOPPED}.
     *
     * @param token cancellation token to observe
     * @throws Throwable if stopping the service fails
     */
    protected abstract void doStop(CancelToken token) throws Throwable;

    /**
     * Disposes the service and releases resources.
     * <p>
     * This method must complete quickly and must not throw exceptions.
     */
    protected abstract void doDispose();

    @Override
    public void start(CancelToken token, ServiceCallback callback) throws Throwable {
        if (state == ServiceState.DISPOSED || state == ServiceState.STARTED) {
            return;
        }
        synchronized (lifecycleLock) {
            if (state == ServiceState.DISPOSED || state == ServiceState.STARTED) {
                return;
            }
            cancelSource.reset();
            try {
                state = ServiceState.STARTING;
                var cancelToken = cancelSource.token();
                var combined = token == null ? cancelToken : Cancellation.combinedToken(cancelToken, token);
                if (combined.canceled()) {
                    return;
                }
                doStart(combined, callback);
                if (state == ServiceState.STARTING) {
                    state = ServiceState.STARTED;
                }
            } catch (Throwable e) {
                state = ServiceState.FAILED;
                throw e;
            }
        }
    }

    @Override
    public void stop(CancelToken token) throws Throwable {
        if (state.isStopped()) {
            return;
        }
        synchronized (lifecycleLock) {
            if (state.isStopped()) {
                return;
            }
            cancelSource.reset();
            try {
                state = ServiceState.STOPPING;
                var cancelToken = cancelSource.token();
                var combined = token == null ? cancelToken : Cancellation.combinedToken(cancelToken, token);
                if (combined.canceled()) {
                    return;
                }
                doStop(combined);
                if (state == ServiceState.STOPPING) {
                    state = ServiceState.STOPPED;
                }
            } catch (Throwable e) {
                state = ServiceState.FAILED;
                throw e;
            }
        }
    }

    @Override
    public void dispose() {
        if (state == ServiceState.DISPOSED) {
            return;
        }
        cancelSource.cancel();
        synchronized (lifecycleLock) {
            if (state == ServiceState.DISPOSED) {
                return;
            }
            state = ServiceState.DISPOSED;
            try {
                doDispose();
            } catch (Throwable ignored) {
                // Dispose must not throw exceptions
            }
        }
    }
}
