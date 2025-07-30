package io.github.amayaframework.service;

import com.github.romanqed.jct.CancelSource;
import com.github.romanqed.jct.CancelToken;
import com.github.romanqed.jct.Cancellation;

public abstract class AbstractService implements Service {
    protected final Object lifecycleLock;
    protected final CancelSource cancelSource;
    protected volatile ServiceState state;

    protected AbstractService(Object lifecycleLock, CancelSource cancelSource) {
        this.lifecycleLock = lifecycleLock;
        this.cancelSource = cancelSource;
        this.state = ServiceState.NEW;
    }

    protected AbstractService() {
        this(new Object(), Cancellation.source());
    }

    @Override
    public ServiceState state() {
        return state;
    }

    protected abstract void doStart(CancelToken token, ServiceCallback callback) throws Throwable;

    protected abstract void doStop(CancelToken token) throws Throwable;

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
        if (state == ServiceState.DISPOSED || state == ServiceState.STOPPED) {
            return;
        }
        synchronized (lifecycleLock) {
            if (state == ServiceState.DISPOSED || state == ServiceState.STOPPED) {
                return;
            }
            cancelSource.reset();
            try {
                state = ServiceState.STOPPING;
                var cancelToken = cancelSource.token();
                var combined = token == null ? cancelToken : Cancellation.combinedToken(cancelToken, token);
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
            doDispose();
        }
    }
}
