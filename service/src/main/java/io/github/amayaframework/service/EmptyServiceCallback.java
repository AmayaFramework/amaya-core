package io.github.amayaframework.service;

import com.github.romanqed.jct.CancelToken;
import com.github.romanqed.jct.EmptyCancelToken;
import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Runnable1;

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
    public void exclusive(Runnable1<CancelToken> action) {
        // Simply fire action
        try {
            action.run(EmptyCancelToken.TOKEN);
        } catch (Throwable e) {
            Exceptions.throwAny(e);
        }
    }
}
