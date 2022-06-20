package io.github.amayaframework.core.spi;

import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.controllers.ControllerFactory;
import io.github.amayaframework.core.controllers.Packer;
import io.github.amayaframework.core.filters.Filter;
import io.github.amayaframework.core.routers.MethodRouter;
import io.github.amayaframework.core.scanners.Scanner;
import org.atteo.classindex.ClassIndex;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

public final class ServiceRepository {
    private static final ServiceProvider PROVIDER = findProvider();

    private static ServiceProvider findProvider() {
        Iterable<Class<?>> found = ClassIndex.getAnnotated(Provider.class);
        List<Class<?>> classes = new LinkedList<>();
        found.forEach(classes::add);
        if (classes.isEmpty()) {
            return new DefaultServiceProvider();
        }
        if (classes.size() > 1) {
            throw new IllegalStateException("More than one provider found");
        }
        Class<?> clazz = classes.get(0);
        if (!ServiceProvider.class.isAssignableFrom(clazz)) {
            throw new IllegalStateException("The annotated class with @Provider is not a subtype of ServiceProvider");
        }
        try {
            return (ServiceProvider) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to instantiate ServiceProvider due to", e);
        }
    }

    public static Scanner<String, Controller> getControllerScanner(ControllerFactory factory) {
        return PROVIDER.getControllerScanner(factory);
    }

    public static Scanner<String, Filter> getFilterScanner() {
        return PROVIDER.getFilterScanner();
    }

    public static ControllerFactory getControllerFactory(Callable<? extends MethodRouter> router, Packer packer) {
        return PROVIDER.getControllerFactory(router, packer);
    }
}
