package io.github.amayaframework.core;

import com.github.romanqed.jconv.OpenedPipelineBuilder;
import com.github.romanqed.jconv.PipelineBuilder;
import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.server.HttpServerConfig;
import io.github.amayaframework.service.ServiceManager;

public abstract class AbstractApplication implements Application {
    protected static final int STARTED = 0;
    protected static final int STOPPED = 1;
    protected static final int SHUTDOWN = 2;
    protected final PipelineBuilder<HttpContext> builder;
    protected final GroupOptionSet options;
    protected final Environment environment;
    protected final ServiceManager manager;
    protected final HttpServer server;
    protected final Object lock;

    protected int state;
    protected Thread hook;

    protected AbstractApplication(GroupOptionSet options,
                                  Environment environment,
                                  ServiceManager manager,
                                  HttpServer server) {
        this.builder = new OpenedPipelineBuilder<>();
        this.options = options;
        this.environment = environment;
        this.manager = manager;
        this.server = server;
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
    public HttpServerConfig getServerConfig() {
        return server.getConfig();
    }

    @Override
    public void addHandler(Runnable2<HttpContext, Runnable1<HttpContext>> node) {
        builder.add(node);
    }

    @Override
    public void reset() {
        builder.clear();
    }

    protected void innerStart(Runnable1<HttpContext> handler) throws Throwable {
        if (state == STARTED) {
            throw new IllegalStateException("Application already started");
        }
        if (state == SHUTDOWN) {
            throw new IllegalStateException("Application already shutdown");
        }
        this.server.setHandler(handler);
        this.manager.start();
        this.server.start();
        this.state = STARTED;
    }

    @Override
    public void start() throws Throwable {
        synchronized (lock) {
            innerStart(builder.build());
        }
    }

    @Override
    public void start(Runnable1<HttpContext> handler) throws Throwable {
        synchronized (lock) {
            innerStart(handler);
            builder.clear();
        }
    }

    @Override
    public void stop() throws Throwable {
        synchronized (lock) {
            if (state == STOPPED) {
                throw new IllegalStateException("Application already stopped");
            }
            if (state == SHUTDOWN) {
                throw new IllegalStateException("Application already shutdown");
            }
            this.server.stop();
            this.manager.stop();
            this.state = STOPPED;
        }
    }

    protected void onShutdown() {
        try {
            this.server.stop();
            this.manager.stop();
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
        hook = new Thread(this::onShutdown);
        return hook;
    }

    protected void innerRun(Runnable1<HttpContext> handler) throws Throwable {
        if (state == STARTED) {
            throw new IllegalStateException("Application already started");
        }
        if (state == SHUTDOWN) {
            throw new IllegalStateException("Application already shutdown");
        }
        Runtime.getRuntime().addShutdownHook(getHook());
        this.server.setHandler(handler);
        this.manager.start();
        this.server.start();
        this.state = STARTED;
    }

    @Override
    public void run() throws Throwable {
        synchronized (lock) {
            innerRun(builder.build());
        }
    }

    @Override
    public void run(Runnable1<HttpContext> handler) throws Throwable {
        synchronized (lock) {
            innerRun(handler);
            builder.clear();
        }
    }

    @Override
    public void shutdown() throws Throwable {
        synchronized (lock) {
            if (state == SHUTDOWN) {
                throw new IllegalStateException("Application already shutdown");
            }
            if (state == STARTED) {
                this.server.stop();
                this.manager.stop();
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
