package io.github.amayaframework.core;

import com.github.romanqed.jct.CancelToken;
import com.github.romanqed.jct.Cancellation;
import io.github.amayaframework.service.AbstractServiceManager;
import io.github.amayaframework.service.Service;
import io.github.amayaframework.service.ServiceCallback;

import java.util.HashMap;

final class PlainServiceManager extends AbstractServiceManager {

    PlainServiceManager() {
        super(new Object(), Cancellation.source(), HashMap::new, null);
    }

    @Override
    protected void doStart(Service service, CancelToken token, ServiceCallback callback) throws Throwable {
        service.start(token, callback);
    }

    @Override
    protected void doStop(Service service, CancelToken token) throws Throwable {
        service.stop(token);
    }

    @Override
    protected void doDispose(Service service) {
        try {
            service.dispose();
        } catch (Throwable ignored) {
            // Dispose must not throw exceptions
        }
    }
}
