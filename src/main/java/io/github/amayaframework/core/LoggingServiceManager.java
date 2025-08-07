package io.github.amayaframework.core;

import com.github.romanqed.jct.CancelToken;
import com.github.romanqed.jct.Cancellation;
import io.github.amayaframework.service.AbstractServiceManager;
import io.github.amayaframework.service.Service;
import io.github.amayaframework.service.ServiceCallback;
import org.slf4j.Logger;

import java.util.HashMap;

final class LoggingServiceManager extends AbstractServiceManager {
    private final Logger logger;

    LoggingServiceManager(Logger logger) {
        super(new Object(), Cancellation.source(), HashMap::new, throwable ->
                logger.error("Critical failure during fail-stop phase. Initiating forced system halt", throwable)
        );
        this.logger = logger;
    }

    @Override
    protected void doStart(Service service, CancelToken token, ServiceCallback callback) throws Throwable {
        logger.info("Starting service: {}", service);
        try {
            service.start(token, callback);
            logger.info("Service started successfully: {}", service);
        } catch (Throwable e) {
            logger.error("Failed to start service: {}", service, e);
            throw e;
        }
    }

    @Override
    protected void doStop(Service service, CancelToken token) throws Throwable {
        logger.info("Stopping service: {}", service);
        try {
            service.stop(token);
            logger.info("Service stopped successfully: {}", service);
        } catch (Throwable e) {
            logger.error("Failed to stop service: {}", service, e);
            throw e;
        }
    }

    @Override
    protected void doDispose(Service service) {
        try {
            service.dispose();
            logger.debug("Service disposed: {}", service);
        } catch (Throwable e) {
            logger.warn("Exception while disposing service: {}", service, e);
        }
    }
}
