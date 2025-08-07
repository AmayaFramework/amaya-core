package io.github.amayaframework.application;

import com.github.romanqed.jconv.Task;
import com.github.romanqed.jconv.TaskBuilder;
import com.github.romanqed.jconv.TaskConfigurer;
import com.github.romanqed.jct.CancelToken;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.service.*;

import java.util.function.Supplier;

/**
 * A base implementation of the {@link Application} interface, providing a full lifecycle and
 * integration with options, environment, task builder, and service management infrastructure.
 * <p>
 * This class controls application execution flow, including shutdown handling, failure propagation,
 * and task startup. It uses a state machine defined in {@link AbstractService}.
 *
 * @param <T> the type of the input accepted by the application task
 */
public abstract class AbstractApplication<T> extends AbstractService implements Application<T> {
    /**
     * The full option set available to this application.
     */
    protected final GroupOptionSet options;

    /**
     * The environment associated with this application, used for resource lifecycle and context management.
     */
    protected final Environment environment;

    /**
     * The service manager coordinating lifecycle operations of all internal services.
     */
    protected final ServiceManager manager;

    /**
     * The builder used to construct the task that drives the application logic.
     */
    protected final TaskBuilder<T> builder;

    /**
     * The current task instance being run or scheduled to run. May be {@code null}.
     */
    protected Task<T> task;

    /**
     * Indicates that the application is shutting down or already shut down.
     */
    protected volatile boolean shutdown;

    /**
     * The registered JVM shutdown hook, if any.
     */
    protected Thread hook;

    /**
     * Constructs a new application instance with the given components.
     *
     * @param options     the application option set
     * @param environment the application environment
     * @param manager     the service manager
     * @param builder     the task builder
     */
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

    /**
     * Registers internal lifecycle listeners for service failures and halts,
     * binding them to proper application shutdown behavior.
     */
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
                    environment.close();
                } catch (Throwable ignored) {
                    // No exceptions while disposing
                } finally {
                    removeHook();
                    state = ServiceState.DISPOSED;
                }
            }
        });
    }

    /**
     * Called when a service failure occurs. Can be overridden to handle errors more precisely.
     *
     * @param throwable the exception that caused the failure
     */
    protected abstract void onFailure(Throwable throwable);

    /**
     * Called when the service manager halts execution, typically due to cancellation.
     *
     * @param throwable the exception or reason that triggered the halt
     */
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

    /**
     * Starts the application task with the provided cancellation token and lifecycle callback.
     * <p>
     * This method must not modify the application state directly. It should initialize the application task,
     * configure service logic, and start required subsystems.
     *
     * @param task     the application task to run
     * @param token    the token used to detect cancellation
     * @param callback the callback for service lifecycle events
     * @throws Throwable if the task or any subsystem fails to start
     */
    protected abstract void doAppStart(Task<T> task, CancelToken token, ServiceCallback callback) throws Throwable;

    /**
     * Stops the application by releasing any resources associated with the task.
     * <p>
     * This method is called during the {@code STOPPING} phase and must not change application state.
     *
     * @param token the token used to detect cancellation
     * @throws Throwable if shutdown fails or is incomplete
     */
    protected abstract void doAppStop(CancelToken token) throws Throwable;

    /**
     * Releases all resources and performs any final cleanup required by the application.
     * <p>
     * This method is called during {@code DISPOSING} and must not throw any exception.
     */
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

    /**
     * Initiates the application shutdown sequence.
     * <p>
     * Cancels the internal token, marks the application as disposed, and releases environment resources.
     * Can be triggered manually or automatically by a JVM shutdown hook.
     */
    protected void shutdown() {
        if (state == ServiceState.DISPOSED) {
            return;
        }
        cancelSource.cancel();
        shutdown = true;
        synchronized (lifecycleLock) {
            if (state == ServiceState.DISPOSED) {
                return;
            }
            state = ServiceState.DISPOSED;
            try {
                doAppDispose();
                environment.close();
            } catch (Throwable ignored) {
                // No exceptions while disposing
            }
        }
    }

    /**
     * Attempts to install a shutdown hook that triggers {@link #shutdown()} when the JVM terminates.
     *
     * @return {@code true} if the hook was added successfully, {@code false} otherwise
     */
    protected boolean addHook() {
        if (shutdown) {
            return false;
        }
        hook = new Thread(this::shutdown);
        try {
            Runtime.getRuntime().addShutdownHook(hook);
            return true;
        } catch (IllegalStateException e) {
            shutdown();
            return false;
        }
    }

    /**
     * Removes the JVM shutdown hook if it was previously registered and the application is not yet shut down.
     */
    protected void removeHook() {
        if (!shutdown && hook != null) {
            try {
                Runtime.getRuntime().removeShutdownHook(hook);
                hook = null;
            } catch (IllegalStateException e) {
                // Do nothing, hook already did all important things
            }
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
        try {
            doAppDispose();
            environment.close();
        } catch (Throwable ignored) {
            // No exceptions while disposing
        }
    }

    /**
     * Executes the application lifecycle, including task preparation, cancellation checks,
     * and controlled state transitions.
     * <p>
     * Ensures that startup only proceeds if the application is not already started or disposed,
     * and handles rollback on cancellation or failure.
     *
     * @param supplier a supplier that provides the application task
     * @throws Throwable if the startup fails
     */
    protected void run(Supplier<Task<T>> supplier) throws Throwable {
        if (shutdown) {
            return;
        }
        if (state == ServiceState.DISPOSED || !state.isStopped()) {
            throw new IllegalStateException("Cannot run application from state " + state);
        }
        synchronized (lifecycleLock) {
            if (shutdown) {
                return;
            }
            if (state == ServiceState.DISPOSED || state == ServiceState.STARTED) {
                throw new IllegalStateException("Cannot run application from state " + state);
            }
            if (!addHook()) {
                return;
            }
            cancelSource.reset();
            try {
                var old = state;
                state = ServiceState.STARTING;
                var cancelToken = cancelSource.token();
                if (shutdown) {
                    state = ServiceState.DISPOSED;
                    return;
                } else if (cancelToken.canceled()) {
                    removeHook();
                    state = old;
                    return;
                }
                doAppStart(supplier.get(), cancelToken, EmptyServiceCallback.CALLBACK);
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
    public void run(Task<T> task) throws Throwable {
        run(() -> task);
    }

    @Override
    public void run() throws Throwable {
        run(() -> {
            if (task == null) {
                task = builder.build();
            }
            return task;
        });
    }
}
