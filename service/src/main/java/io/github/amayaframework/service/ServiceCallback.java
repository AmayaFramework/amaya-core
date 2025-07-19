package io.github.amayaframework.service;

import com.github.romanqed.jfunc.Runnable0;

public interface ServiceCallback {

    void fail(Throwable cause);

    default void fail() {
        fail(null);
    }

    void halt(Throwable cause);

    default void halt() {
        halt(null);
    }

    void exclusive(Runnable0 action);
}
