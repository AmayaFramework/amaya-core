package io.github.amayaframework.web;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.application.AbstractApplication;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.server.HttpServerConfig;
import io.github.amayaframework.service.ServiceManager;

import java.net.InetSocketAddress;
import java.util.Objects;

/**
 *
 */
public abstract class AbstractWebApplication extends AbstractApplication<HttpContext> implements WebApplication {
    /**
     *
     */
    protected final HttpServer server;

    /**
     *
     * @param options
     * @param environment
     * @param manager
     * @param server
     */
    protected AbstractWebApplication(GroupOptionSet options,
                                     Environment environment,
                                     ServiceManager manager,
                                     HttpServer server) {
        super(options, environment, manager);
        this.server = server;
    }

    @Override
    public HttpServerConfig getServerConfig() {
        return server.getConfig();
    }

    @Override
    public void bind(InetSocketAddress address) {
        Objects.requireNonNull(address);
        server.getConfig().addAddress(address);
    }

    @Override
    public void bind(int port) {
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("Illegal port value: " + port);
        }
        server.getConfig().addAddress(new InetSocketAddress(port));
    }

    @Override
    protected void doStart(Runnable1<HttpContext> handler) throws Throwable {
        this.manager.start();
        this.server.setHandler(handler);
        this.server.start();
    }

    @Override
    protected void doStop() throws Throwable {
        this.server.stop();
        this.manager.stop();
    }
}
