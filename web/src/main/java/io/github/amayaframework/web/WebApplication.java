package io.github.amayaframework.web;

import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;
import io.github.amayaframework.application.Application;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.server.HttpServerConfig;

import java.net.InetSocketAddress;

/**
 *
 */
public interface WebApplication extends Application<HttpContext> {

    /**
     * @return
     */
    HttpServerConfig getServerConfig();

    /**
     * @param address
     */
    void bind(InetSocketAddress address);

    /**
     * @param port
     */
    void bind(int port);

    /**
     * @param runnable2
     */
    @Override
    void addHandler(Runnable2<HttpContext, Runnable1<HttpContext>> runnable2);

    /**
     * @param runnable1
     * @throws Throwable
     */
    @Override
    void start(Runnable1<HttpContext> runnable1) throws Throwable;

    /**
     * @param runnable1
     * @throws Throwable
     */
    @Override
    void run(Runnable1<HttpContext> runnable1) throws Throwable;
}
