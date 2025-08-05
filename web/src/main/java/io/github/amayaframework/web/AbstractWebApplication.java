package io.github.amayaframework.web;

import com.github.romanqed.jconv.Task;
import com.github.romanqed.jconv.TaskBuilder;
import com.github.romanqed.jct.CancelToken;
import com.github.romanqed.jfunc.Exceptions;
import io.github.amayaframework.application.AbstractApplication;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.http.HttpVersion;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.server.HttpServerConfig;
import io.github.amayaframework.service.ServiceCallback;
import io.github.amayaframework.service.ServiceManager;

import java.net.InetSocketAddress;

public abstract class AbstractWebApplication extends AbstractApplication<HttpContext> implements WebApplication {
    protected final HttpServer server;

    protected AbstractWebApplication(GroupOptionSet options,
                                     Environment environment,
                                     ServiceManager manager,
                                     TaskBuilder<HttpContext> builder,
                                     HttpServer server) {
        super(options, environment, manager, builder);
        this.server = server;
    }

    @Override
    public HttpServerConfig serverConfig() {
        return server.config();
    }

    @Override
    public void bind(InetSocketAddress address, HttpVersion version) {
        server.config().addAddress(address, version);
    }

    @Override
    public void bind(InetSocketAddress address) {
        server.config().addAddress(address);
    }

    protected static void checkPort(int port) {
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("Illegal port value: " + port);
        }
    }

    protected static InetSocketAddress createAddress(String host, int port) {
        checkPort(port);
        if (host == null) {
            return new InetSocketAddress(port);
        }
        return new InetSocketAddress(host, port);
    }

    @Override
    public void bind(String host, int port, HttpVersion version) {
        server.config().addAddress(createAddress(host, port), version);
    }

    @Override
    public void bind(String host, int port) {
        server.config().addAddress(createAddress(host, port));
    }

    @Override
    public void bind(int port, HttpVersion version) {
        checkPort(port);
        server.config().addAddress(new InetSocketAddress(port), version);
    }

    @Override
    public void bind(int port) {
        checkPort(port);
        server.config().addAddress(new InetSocketAddress(port));
    }

    @Override
    protected void onFailure(Throwable throwable) {
        try {
            server.stop();
        } catch (Throwable e) {
            Exceptions.throwAny(e);
        }
    }

    @Override
    protected void onHalt(Throwable throwable) {
        server.dispose();
    }

    @Override
    protected void doAppStart(Task<HttpContext> task, CancelToken token, ServiceCallback callback) throws Throwable {
        manager.start(token, callback);
        if (token.canceled()) {
            return;
        }
        server.handler(task);
        server.start(token, callback);
    }

    @Override
    protected void doAppStop(CancelToken token) throws Throwable {
        server.stop(token);
        manager.stop(token);
    }

    @Override
    protected void doAppDispose() {
        server.dispose();
        manager.dispose();
    }
}
