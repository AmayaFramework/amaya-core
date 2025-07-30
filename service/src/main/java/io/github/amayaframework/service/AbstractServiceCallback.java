package io.github.amayaframework.service;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jfunc.Runnable0;

public abstract class AbstractServiceCallback implements ServiceCallback {
    protected final Object lifecycleLock;
    protected volatile boolean disposed;
    protected boolean inTransition;

    protected AbstractServiceCallback(Object lifecycleLock) {
        this.lifecycleLock = lifecycleLock;
        this.inTransition = false;
        this.disposed = false;
    }

    @Override
    public void exclusive(Runnable0 action) {
        try {
            synchronized (lifecycleLock) {
                action.run();
            }
        } catch (Throwable e) {
            Exceptions.throwAny(e);
        }
    }
}
