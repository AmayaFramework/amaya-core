package io.github.amayaframework.service;

import com.github.romanqed.jct.CancelToken;
import com.github.romanqed.jct.EmptyCancelToken;

/**
 * Defines a lifecycle contract for a manageable service.
 * A {@link Service} can be started and stopped with cancellation support and
 * reports its current {@link ServiceState}.
 * It extends {@link Disposable} to support resource cleanup.
 */
public interface Service extends Disposable {

    /**
     * Starts the service asynchronously or synchronously, reporting lifecycle events via callback.
     *
     * @param token cancellation token to cooperatively cancel the start operation
     * @param callback callback to notify about lifecycle events such as failure or halt
     * @throws Throwable if starting fails
     */
    void start(CancelToken token, ServiceCallback callback) throws Throwable;

    /**
     * Starts the service with a cancellation token and default empty callback.
     *
     * @param token cancellation token
     * @throws Throwable if starting fails
     */
    default void start(CancelToken token) throws Throwable {
        start(token, EmptyServiceCallback.CALLBACK);
    }

    /**
     * Starts the service with an empty cancellation token and the provided callback.
     *
     * @param callback lifecycle callback
     * @throws Throwable if starting fails
     */
    default void start(ServiceCallback callback) throws Throwable {
        start(EmptyCancelToken.TOKEN, callback);
    }

    /**
     * Starts the service without cancellation or callback.
     *
     * @throws Throwable if starting fails
     */
    default void start() throws Throwable {
        start(EmptyCancelToken.TOKEN, EmptyServiceCallback.CALLBACK);
    }

    /**
     * Stops the service, respecting the provided cancellation token.
     *
     * @param token cancellation token for cooperative stopping
     * @throws Throwable if stopping fails
     */
    void stop(CancelToken token) throws Throwable;

    /**
     * Stops the service without cancellation support.
     *
     * @throws Throwable if stopping fails
     */
    default void stop() throws Throwable {
        stop(EmptyCancelToken.TOKEN);
    }

    /**
     * Returns the current lifecycle state of this service.
     *
     * @return current {@link ServiceState}, default is {@link ServiceState#UNMANAGED}
     */
    default ServiceState state() {
        return ServiceState.UNMANAGED;
    }
}
