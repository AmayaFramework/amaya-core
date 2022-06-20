package io.github.amayaframework.core.spi;

import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.controllers.ControllerFactory;
import io.github.amayaframework.core.controllers.HttpControllerFactory;
import io.github.amayaframework.core.controllers.Packer;
import io.github.amayaframework.core.filters.Filter;
import io.github.amayaframework.core.routers.MethodRouter;
import io.github.amayaframework.core.scanners.ControllerScanner;
import io.github.amayaframework.core.scanners.FilterScanner;
import io.github.amayaframework.core.scanners.Scanner;

import java.util.concurrent.Callable;

final class DefaultServiceProvider implements ServiceProvider {
    @Override
    public Scanner<String, Controller> getControllerScanner(ControllerFactory factory) {
        return new ControllerScanner(factory);
    }

    @Override
    public Scanner<String, Filter> getFilterScanner() {
        return new FilterScanner();
    }

    @Override
    public ControllerFactory getControllerFactory(Callable<? extends MethodRouter> router, Packer packer) {
        return new HttpControllerFactory(router, packer);
    }
}
