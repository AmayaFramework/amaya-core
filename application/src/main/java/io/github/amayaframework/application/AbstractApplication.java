package io.github.amayaframework.application;

import com.github.romanqed.jconv.OpenedPipelineBuilder;
import com.github.romanqed.jconv.PipelineBuilder;
import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.service.ServiceManager;

/**
 * A class that provides a skeletal implementation of the {@link Application}.
 *
 * @param <T> the type of application context
 */
public abstract class AbstractApplication<T> implements Application<T> {
    // Constants
    /**
     * Started state value.
     */
    protected static final int STARTED = 0;
    /**
     * Stopped state value.
     */
    protected static final int STOPPED = 1;
    /**
     * Shutdown state value.
     */
    protected static final int SHUTDOWN = 2;

    // Components
    /**
     * The {@link PipelineBuilder} containing middlewared context handlers.
     */
    protected final PipelineBuilder<T> builder;
    /**
     * The application options.
     */
    protected final GroupOptionSet options;
    /**
     * The application environment.
     */
    protected final Environment environment;
    /**
     * The application service manager.
     */
    protected final ServiceManager manager;
    /**
     * The application state change lock.
     */
    protected final Object lock;

    // Self-managed fields
    /**
     * Current application state.
     */
    protected int state;
    /**
     * Current application shutdown hook.
     */
    protected Thread hook;

    /**
     * Constructs an {@link AbstractApplication} instance with given options, environment and service manager.
     *
     * @param options     the specified {@link GroupOptionSet} instance, must be non-null
     * @param environment the specified {@link Environment} instance, must be non-null
     * @param manager     the specified {@link ServiceManager} instance, must be non-null
     */
    protected AbstractApplication(GroupOptionSet options, Environment environment, ServiceManager manager) {
        this.builder = new OpenedPipelineBuilder<>();
        this.options = options;
        this.environment = environment;
        this.manager = manager;
        this.lock = new Object();
        this.state = STOPPED;
    }

    @Override
    public GroupOptionSet getOptions() {
        return options;
    }

    @Override
    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public ServiceManager getManager() {
        return manager;
    }

    @Override
    public void addHandler(Runnable2<T, Runnable1<T>> handler) {
        builder.add(handler);
    }

    @Override
    public void reset() {
        builder.clear();
    }

    /**
     * Does application start, i.e. manage application services.
     *
     * @param handler the application context handler
     * @throws Throwable if any problems during application start occurred
     */
    protected abstract void doStart(Runnable1<T> handler) throws Throwable;

    @Override
    public void start(Runnable1<T> handler) throws Throwable {
        synchronized (lock) {
            if (state == STARTED) {
                throw new IllegalStateException("Application already started");
            }
            if (state == SHUTDOWN) {
                throw new IllegalStateException("Application already shutdown");
            }
            builder.clear();
            doStart(handler);
            this.state = STARTED;
        }
    }

    @Override
    public void start() throws Throwable {
        synchronized (lock) {
            if (state == STARTED) {
                throw new IllegalStateException("Application already started");
            }
            if (state == SHUTDOWN) {
                throw new IllegalStateException("Application already shutdown");
            }
            doStart(builder.build());
            this.state = STARTED;
        }
    }

    /**
     * Does application stop, i.e. manage application services.
     *
     * @throws Throwable if any problems during application stop occurred
     */
    protected abstract void doStop() throws Throwable;

    @Override
    public void stop() throws Throwable {
        synchronized (lock) {
            if (state == STOPPED) {
                throw new IllegalStateException("Application already stopped");
            }
            if (state == SHUTDOWN) {
                throw new IllegalStateException("Application already shutdown");
            }
            doStop();
            this.state = STOPPED;
            if (hook != null) {
                Runtime.getRuntime().removeShutdownHook(hook);
            }
            this.hook = null;
        }
    }

    /**
     * Does application shutdown. Used as shutdown hook body.
     */
    protected void doShutdown() {
        try {
            doStop();
            this.environment.close();
            this.state = SHUTDOWN;
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private Thread getHook() {
        if (hook != null) {
            return hook;
        }
        hook = new Thread(this::doShutdown);
        return hook;
    }

    @Override
    public void run(Runnable1<T> handler) throws Throwable {
        synchronized (lock) {
            if (state == STARTED) {
                throw new IllegalStateException("Application already started");
            }
            if (state == SHUTDOWN) {
                throw new IllegalStateException("Application already shutdown");
            }
            Runtime.getRuntime().addShutdownHook(getHook());
            builder.clear();
            doStart(handler);
            this.state = STARTED;
        }
    }

    @Override
    public void run() throws Throwable {
        synchronized (lock) {
            if (state == STARTED) {
                throw new IllegalStateException("Application already started");
            }
            if (state == SHUTDOWN) {
                throw new IllegalStateException("Application already shutdown");
            }
            Runtime.getRuntime().addShutdownHook(getHook());
            doStart(builder.build());
            this.state = STARTED;
        }
    }

    @Override
    public void shutdown() throws Throwable {
        synchronized (lock) {
            if (state == SHUTDOWN) {
                throw new IllegalStateException("Application already shutdown");
            }
            if (state == STARTED) {
                doStop();
            }
            this.environment.close();
            this.state = SHUTDOWN;
            if (hook != null) {
                Runtime.getRuntime().removeShutdownHook(hook);
            }
            this.hook = null;
        }
    }
}
