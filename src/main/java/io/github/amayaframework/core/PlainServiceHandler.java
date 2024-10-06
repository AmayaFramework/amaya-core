package io.github.amayaframework.core;

import io.github.amayaframework.service.ServiceHandler;
import io.github.amayaframework.service.WatchedService;

final class PlainServiceHandler implements ServiceHandler {

    @Override
    public void start(Iterable<WatchedService> services) throws Throwable {
        for (var service : services) {
            service.start();
        }
    }

    @Override
    public void stop(Iterable<WatchedService> services) throws Throwable {
        for (var service : services) {
            service.stop();
        }
    }
}
