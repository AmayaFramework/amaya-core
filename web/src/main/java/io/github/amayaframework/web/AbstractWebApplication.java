package io.github.amayaframework.web;

import com.github.romanqed.jconv.Task;
import com.github.romanqed.jconv.TaskBuilder;
import com.github.romanqed.jct.CancelToken;
import io.github.amayaframework.application.AbstractApplication;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.http.HttpVersion;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.server.HttpServerConfig;
import io.github.amayaframework.service.ServiceCallback;
import io.github.amayaframework.service.ServiceManager;
import jakarta.servlet.ServletContext;

import java.net.InetSocketAddress;

/**
 * Base abstract implementation of the {@link WebApplication} interface.
 * <p>
 * Manages the lifecycle of a web application including configuration, binding,
 * and delegation to an underlying {@link HttpServer} and {@link ServiceManager}.
 * Provides convenience methods to bind the server to various addresses and ports,
 * with optional HTTP version specification.
 * <p>
 * Implements key lifecycle methods to start, stop, and dispose the server and service manager,
 * handling failure and halt conditions appropriately.
 */
public abstract class AbstractWebApplication extends AbstractApplication<HttpContext> implements WebApplication {
    /**
     * The underlying HTTP server instance used by this application.
     */
    protected final HttpServer server;

    /**
     * Constructs a new instance of {@code AbstractWebApplication}.
     *
     * @param options     the group of options configuring the application
     * @param environment the environment in which the application runs
     * @param manager     the service manager responsible for service lifecycle
     * @param builder     the task builder for processing HTTP contexts
     * @param server      the HTTP server instance backing this application
     */
    protected AbstractWebApplication(GroupOptionSet options,
                                     Environment environment,
                                     ServiceManager manager,
                                     TaskBuilder<HttpContext> builder,
                                     HttpServer server) {
        super(options, environment, manager, builder);
        this.server = server;
    }

    /**
     * Checks that the provided port number is within the valid range.
     *
     * @param port the port number to check
     * @throws IllegalArgumentException if the port is outside the 0-65535 range
     */
    protected static void checkPort(int port) {
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("Illegal port value: " + port);
        }
    }

    /**
     * Creates a new {@link InetSocketAddress} from the provided host and port,
     * validating the port value.
     *
     * @param host the hostname, may be {@code null} to bind to all interfaces
     * @param port the port number, must be between 0 and 65535 inclusive
     * @return the created {@link InetSocketAddress}
     * @throws IllegalArgumentException if the port is out of valid range
     */
    protected static InetSocketAddress createAddress(String host, int port) {
        checkPort(port);
        if (host == null) {
            return new InetSocketAddress(port);
        }
        return new InetSocketAddress(host, port);
    }

    @Override
    public ServletContext servletContext() {
        return server.servletContext();
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
    protected void onFailure(Throwable throwable) throws Throwable {
        server.stop(cancelSource.token());
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
