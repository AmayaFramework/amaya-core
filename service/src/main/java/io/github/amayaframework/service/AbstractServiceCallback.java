package io.github.amayaframework.service;

import com.github.romanqed.jfunc.Runnable0;

public abstract class AbstractServiceCallback implements ServiceCallback {
    protected final Object lifecycleLock;
    protected volatile boolean inTransition;

    protected AbstractServiceCallback(Object lifecycleLock) {
        this.lifecycleLock = lifecycleLock;
        this.inTransition = false;
    }

    @Override
    public void exclusive(Runnable0 action) {
        try {
            if (inTransition) {
                action.run();
                return;
            }
            synchronized (lifecycleLock) {
                action.run();
            }
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
