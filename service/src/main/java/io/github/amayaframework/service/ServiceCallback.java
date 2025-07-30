package io.github.amayaframework.service;

import com.github.romanqed.jct.CancelToken;
import com.github.romanqed.jfunc.Runnable1;

public interface ServiceCallback {

    void fail(Throwable cause);

    default void fail() {
        fail(null);
    }

    void halt(Throwable cause);

    default void halt() {
        halt(null);
    }

    void exclusive(Runnable1<CancelToken> action);
}
