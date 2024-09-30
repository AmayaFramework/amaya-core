package io.github.amayaframework.impl;

import io.github.amayaframework.service.ServiceHandler;
import io.github.amayaframework.service.WatchedService;

public final class PlainServiceHandler implements ServiceHandler {

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
