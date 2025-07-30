package io.github.amayaframework.service;

import com.github.romanqed.jct.CancelToken;
import com.github.romanqed.jct.EmptyCancelToken;
import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Runnable1;

/**
 * A no-op implementation of {@link ServiceCallback}.
 * <p>
 * All callback methods perform no operations.
 * The {@link #exclusive(Runnable1)} method simply runs the given action
 * with an empty cancellation token.
 * <p>
 * Useful as a default or placeholder callback.
 */
public final class EmptyServiceCallback implements ServiceCallback {
    /**
     * Singleton instance of the empty service callback.
     */
    public static final ServiceCallback CALLBACK = new EmptyServiceCallback();

    @Override
    public void fail(Throwable cause) {
        // No operation
    }

    @Override
    public void halt(Throwable cause) {
        // No operation
    }

    @Override
    public void exclusive(Runnable1<CancelToken> action) {
        try {
            action.run(EmptyCancelToken.TOKEN);
        } catch (Throwable e) {
            Exceptions.throwAny(e);
        }
    }
}
