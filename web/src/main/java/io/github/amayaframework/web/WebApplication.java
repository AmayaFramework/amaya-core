package io.github.amayaframework.web;

import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;
import io.github.amayaframework.application.Application;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.server.HttpServerConfig;

/**
 *
 */
public interface WebApplication extends Application<HttpContext> {

    /**
     *
     * @return
     */
    HttpServerConfig getServerConfig();

    /**
     *
     * @param runnable2
     */
    @Override
    void addHandler(Runnable2<HttpContext, Runnable1<HttpContext>> runnable2);

    /**
     *
     * @param runnable1
     * @throws Throwable
     */
    @Override
    void start(Runnable1<HttpContext> runnable1) throws Throwable;

    /**
     *
     * @param runnable1
     * @throws Throwable
     */
    @Override
    void run(Runnable1<HttpContext> runnable1) throws Throwable;
}
