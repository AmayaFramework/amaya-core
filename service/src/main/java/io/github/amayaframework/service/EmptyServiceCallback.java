package io.github.amayaframework.service;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Runnable0;

public final class EmptyServiceCallback implements ServiceCallback {
    public static final ServiceCallback CALLBACK = new EmptyServiceCallback();

    @Override
    public void fail(Throwable cause) {
        // Do nothing
    }

    @Override
    public void halt(Throwable cause) {
        // Do nothing
    }

    @Override
    public void exclusive(Runnable0 action) {
        // Simply fire action
        try {
            action.run();
        } catch (Throwable e) {
            Exceptions.throwAny(e);
        }
    }
}
