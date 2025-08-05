package io.github.amayaframework.application;

import com.github.romanqed.jconv.Task;
import com.github.romanqed.jconv.TaskBuilder;
import com.github.romanqed.jconv.TaskConfigurer;
import com.github.romanqed.jct.CancelToken;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.service.*;

public abstract class AbstractApplication<T> extends AbstractService implements Application<T> {
    protected final GroupOptionSet options;
    protected final Environment environment;
    protected final ServiceManager manager;
    protected final TaskBuilder<T> builder;
    protected Task<T> task;
    protected volatile boolean shutdown;
    protected Thread hook;

    protected AbstractApplication(GroupOptionSet options,
                                  Environment environment,
                                  ServiceManager manager,
                                  TaskBuilder<T> builder) {
        this.options = options;
        this.environment = environment;
        this.manager = manager;
        this.builder = builder;
        this.task = null;
        this.shutdown = false;
        this.hook = null;
        registerEvents();
    }

    protected void registerEvents() {
        manager.onFailure(throwable -> {
            synchronized (lifecycleLock) {
                try {
                    onFailure(throwable);
                } finally {
                    removeHook();
                    state = ServiceState.FAILED;
                }
            }
        });
        manager.onHalt(throwable -> {
            cancelSource.cancel();
            synchronized (lifecycleLock) {
                try {
                    onHalt(throwable);
                } finally {
                    removeHook();
                    state = ServiceState.DISPOSED;
                }
            }
        });
    }

    protected abstract void onFailure(Throwable throwable);

    protected abstract void onHalt(Throwable throwable);

    @Override
    public GroupOptionSet options() {
        return options;
    }

    @Override
    public Environment environment() {
        return environment;
    }

    @Override
    public TaskConfigurer<T> configurer() {
        return builder;
    }

    @Override
    public ServiceManager manager() {
        return manager;
    }

    @Override
    public void reset() {
        synchronized (lifecycleLock) {
            this.task = null;
            this.builder.clear();
        }
    }

    protected abstract void doAppStart(Task<T> task, CancelToken token, ServiceCallback callback) throws Throwable;

    protected abstract void doAppStop(CancelToken token) throws Throwable;

    protected abstract void doAppDispose();

    @Override
    protected void doStart(CancelToken token, ServiceCallback callback) throws Throwable {
        if (token.canceled()) {
            return;
        }
        if (task == null) {
            task = builder.build();
        }
        doAppStart(task, token, callback);
    }

    protected void shutdown() {
        if (state.isStopped()) {
            return;
        }
        cancelSource.cancel();
        shutdown = true;
        synchronized (lifecycleLock) {
            if (state.isStopped()) {
                return;
            }
            state = ServiceState.DISPOSED;
            doAppDispose();
        }
    }

    protected void addHook() {
        if (shutdown) {
            return;
        }
        hook = new Thread(this::shutdown);
        Runtime.getRuntime().addShutdownHook(hook);
    }

    protected void removeHook() {
        if (!shutdown && hook != null) {
            Runtime.getRuntime().removeShutdownHook(hook);
            hook = null;
        }
    }

    @Override
    protected void doStop(CancelToken token) throws Throwable {
        if (token.canceled()) {
            return;
        }
        removeHook();
        doAppStop(token);
    }

    @Override
    protected void doDispose() {
        removeHook();
        doAppDispose();
    }

    @Override
    public void run(Task<T> task) throws Throwable {
        if (shutdown) {
            return;
        }
        if (state == ServiceState.DISPOSED || state == ServiceState.STARTED) {
            throw new IllegalStateException("Cannot run application from state " + state);
        }
        synchronized (lifecycleLock) {
            if (shutdown) {
                return;
            }
            if (state == ServiceState.DISPOSED || state == ServiceState.STARTED) {
                throw new IllegalStateException("Cannot run application from state " + state);
            }
            cancelSource.reset();
            addHook();
            try {
                var old = state;
                state = ServiceState.STARTING;
                var cancelToken = cancelSource.token();
                if (cancelToken.canceled()) {
                    state = old;
                    return;
                }
                doAppStart(task, cancelToken, EmptyServiceCallback.CALLBACK);
                if (shutdown) {
                    state = ServiceState.DISPOSED;
                } else if (cancelToken.canceled()) {
                    removeHook();
                    state = old;
                } else if (state == ServiceState.STARTING) {
                    state = ServiceState.STARTED;
                }
            } catch (Throwable e) {
                state = ServiceState.FAILED;
                throw e;
            }
        }
    }

    @Override
    public void run() throws Throwable {
        if (shutdown) {
            return;
        }
        if (state == ServiceState.DISPOSED || state == ServiceState.STARTED) {
            throw new IllegalStateException("Cannot run application from state " + state);
        }
        synchronized (lifecycleLock) {
            if (shutdown) {
                return;
            }
            if (state == ServiceState.DISPOSED || state == ServiceState.STARTED) {
                throw new IllegalStateException("Cannot run application from state " + state);
            }
            cancelSource.reset();
            addHook();
            try {
                var old = state;
                state = ServiceState.STARTING;
                var cancelToken = cancelSource.token();
                if (cancelToken.canceled()) {
                    state = old;
                    return;
                }
                if (task == null) {
                    task = builder.build();
                }
                doAppStart(task, cancelToken, EmptyServiceCallback.CALLBACK);
                if (shutdown) {
                    state = ServiceState.DISPOSED;
                } else if (cancelToken.canceled()) {
                    removeHook();
                    state = old;
                } else if (state == ServiceState.STARTING) {
                    state = ServiceState.STARTED;
                }
            } catch (Throwable e) {
                state = ServiceState.FAILED;
                throw e;
            }
        }
    }
}
