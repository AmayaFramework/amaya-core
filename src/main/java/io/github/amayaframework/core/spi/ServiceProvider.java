package io.github.amayaframework.core.spi;

import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.controllers.ControllerFactory;
import io.github.amayaframework.core.controllers.Packer;
import io.github.amayaframework.core.filters.Filter;
import io.github.amayaframework.core.routers.MethodRouter;
import io.github.amayaframework.core.scanners.Scanner;

import java.util.concurrent.Callable;

/**
 * The supplier that creates the main factories of the framework.
 * Can be inherited and specified using the {@link Provider} annotation.
 */
public interface ServiceProvider {
    Scanner<String, Controller> getControllerScanner(ControllerFactory factory);

    Scanner<String, Filter> getFilterScanner();

    ControllerFactory getControllerFactory(Callable<? extends MethodRouter> router, Packer packer);
}
