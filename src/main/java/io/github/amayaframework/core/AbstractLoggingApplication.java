package io.github.amayaframework.core;

import com.github.romanqed.jconv.Task;
import com.github.romanqed.jconv.TaskBuilder;
import com.github.romanqed.jct.CancelToken;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.service.ServiceCallback;
import io.github.amayaframework.service.ServiceManager;
import io.github.amayaframework.web.AbstractWebApplication;
import org.slf4j.Logger;

public abstract class AbstractLoggingApplication extends AbstractWebApplication {
    protected final Logger logger;

    protected AbstractLoggingApplication(GroupOptionSet options,
                                         Environment environment,
                                         ServiceManager manager,
                                         TaskBuilder<HttpContext> builder,
                                         HttpServer server,
                                         Logger logger) {
        super(options, environment, manager, builder, server);
        this.logger = logger;
    }

    @Override
    protected void doAppStart(Task<HttpContext> task, CancelToken token, ServiceCallback callback) throws Throwable {
        logger.info("Starting application...");
        try {
            super.doAppStart(task, token, callback);
            logger.info("Application started successfully");
        } catch (Throwable e) {
            logger.error("Application failed to start", e);
            throw e;
        }
    }

    @Override
    protected void doAppStop(CancelToken token) throws Throwable {
        logger.info("Stopping application...");
        try {
            super.doAppStop(token);
            logger.info("Application stopped successfully");
        } catch (Throwable e) {
            logger.error("Application failed to stop", e);
            throw e;
        }
    }

    @Override
    protected void doAppDispose() {
        try {
            super.doAppDispose();
            logger.debug("Application resources disposed");
        } catch (Throwable e) {
            logger.warn("Exception while disposing application", e);
        }
    }
}
