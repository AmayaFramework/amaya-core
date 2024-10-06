package io.github.amayaframework.core;

import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServerConfig;
import io.github.amayaframework.service.ServiceManager;

public interface Application extends Resettable {

    GroupOptionSet getOptions();

    Environment getEnvironment();

    ServiceManager getManager();

    HttpServerConfig getServerConfig();

    ServiceProvider getProvider();

    void addHandler(Runnable2<HttpContext, Runnable1<HttpContext>> handler);

    @Override
    void reset();

    void start(Runnable1<HttpContext> handler) throws Throwable;

    void start() throws Throwable;

    void stop() throws Throwable;

    void run(Runnable1<HttpContext> handler) throws Throwable;

    void run() throws Throwable;

    void shutdown() throws Throwable;
}
