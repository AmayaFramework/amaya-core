package io.github.amayaframework.service;

import com.github.romanqed.jct.CancelToken;
import com.github.romanqed.jfunc.Runnable1;

/**
 * Callback interface to receive lifecycle event notifications from a {@link Service}.
 * Supports notifications for failure, halt, and exclusive execution with cancellation support.
 */
public interface ServiceCallback {

    /**
     * Notifies that the service has failed with an optional cause.
     *
     * @param cause the failure cause, may be {@code null}
     */
    void fail(Throwable cause);

    /**
     * Notifies that the service has failed without a specific cause.
     */
    default void fail() {
        fail(null);
    }

    /**
     * Notifies that the service has halted with an optional cause.
     *
     * @param cause the halt cause, may be {@code null}
     */
    void halt(Throwable cause);

    /**
     * Notifies that the service has halted without a specific cause.
     */
    default void halt() {
        halt(null);
    }

    /**
     * Executes the provided {@link Runnable1} exclusively,
     * passing a {@link CancelToken} to support cooperative cancellation.
     *
     * @param action the exclusive action to execute
     */
    void exclusive(Runnable1<CancelToken> action);
}
