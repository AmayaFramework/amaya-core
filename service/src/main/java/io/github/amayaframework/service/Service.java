package io.github.amayaframework.service;

import com.github.romanqed.jct.CancelToken;
import com.github.romanqed.jct.EmptyCancelToken;

public interface Service extends Disposable {

    void start(CancelToken token, ServiceCallback callback) throws Throwable;

    default void start(CancelToken token) throws Throwable {
        start(token, EmptyServiceCallback.CALLBACK);
    }

    default void start(ServiceCallback callback) throws Throwable {
        start(EmptyCancelToken.TOKEN, callback);
    }

    default void start() throws Throwable {
        start(EmptyCancelToken.TOKEN, EmptyServiceCallback.CALLBACK);
    }

    void stop(CancelToken token) throws Throwable;

    default void stop() throws Throwable {
        stop(EmptyCancelToken.TOKEN);
    }

    default ServiceState state() {
        return ServiceState.UNMANAGED;
    }
}
