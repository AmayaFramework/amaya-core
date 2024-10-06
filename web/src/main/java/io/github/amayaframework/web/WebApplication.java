package io.github.amayaframework.web;

import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;
import io.github.amayaframework.application.Application;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.server.HttpServerConfig;

import java.net.InetSocketAddress;

/**
 * An interface describing an abstract web application that manages services.
 */
public interface WebApplication extends Application<HttpContext> {

    /**
     * Gets http server config. Any config changes are reflected on the server and vice versa.
     *
     * @return the {@link HttpServerConfig} instance
     */
    HttpServerConfig getServerConfig();

    /**
     * Binds web application to given address.
     *
     * @param address the specified address that the server will listen to, must be non-null
     */
    void bind(InetSocketAddress address);

    /**
     * Binds web application to given port.
     *
     * @param port the specified port that the server will listen to
     */
    void bind(int port);

    @Override
    void addHandler(Runnable2<HttpContext, Runnable1<HttpContext>> handler);

    @Override
    void start(Runnable1<HttpContext> handler) throws Throwable;

    @Override
    void run(Runnable1<HttpContext> handler) throws Throwable;
}
