package io.github.amayaframework.core;

import com.github.romanqed.util.Handler;
import io.github.amayaframework.core.config.AmayaConfig;
import io.github.amayaframework.core.configurators.AmayaConfigurator;
import io.github.amayaframework.core.configurators.Configurator;
import io.github.amayaframework.core.configurators.ConfiguratorWrapper;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.controllers.ControllerFactory;
import io.github.amayaframework.core.controllers.Endpoint;
import io.github.amayaframework.core.handlers.PipelineHandler;
import io.github.amayaframework.core.scanners.ControllerScanner;
import io.github.amayaframework.core.scanners.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AmayaBuilder<T> {
    private static final String DEFAULT_PREFIX = "io.github.amayaframework.core.actions";
    protected final AmayaConfig config;
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Map<String, Controller> controllers;
    protected final List<ConfiguratorWrapper> configurators;
    protected final ControllerFactory factory;
    private final Handler<PipelineHandler> configurator;
    protected Class<? extends Annotation> annotation;

    protected AmayaBuilder(AmayaConfig config, String pipelinePrefix) {
        this.config = Objects.requireNonNull(config);
        this.factory = new ControllerFactory(config.getRouter(), config.getRoutePacker());
        configurator = new AmayaConfigurator(pipelinePrefix, config.isDebug());
        controllers = new ConcurrentHashMap<>();
        configurators = new LinkedList<>();
        if (config.isDebug()) {
            logger.debug(config.toString());
        }
        resetValues();
    }

    public AmayaBuilder(AmayaConfig config) {
        this(config, DEFAULT_PREFIX);
    }

    public AmayaBuilder() {
        this(new AmayaConfig(), DEFAULT_PREFIX);
    }

    protected void resetValues() {
        annotation = Endpoint.class;
        controllers.clear();
        configurators.clear();
    }

    public AmayaBuilder<T> addConfigurator(Configurator configurator) {
        Objects.requireNonNull(configurator);
        configurators.add(new ConfiguratorWrapper(configurator));
        return this;
    }

    public AmayaBuilder<T> removeConfigurator(Class<? extends Configurator> clazz) {
        Objects.requireNonNull(clazz);
        configurators.removeIf(e -> e.getBody().getClass() == clazz);
        return this;
    }

    protected void configure(PipelineHandler handler, Controller controller) {
        try {
            configurator.handle(handler);
            for (ConfiguratorWrapper configurator : configurators) {
                configurator.configure(handler, controller);
            }
        } catch (Throwable e) {
            throw new IllegalStateException("Exception when configure", e);
        }
    }

    public AmayaBuilder<T> addController(String route, Object object) throws Exception {
        Controller toPut = factory.createController(route, object);
        controllers.put(route, toPut);
        if (config.isDebug()) {
            logger.debug("Add controller \"" + toPut.getRoute() + "\"=" + toPut.getClass().getSimpleName());
        }
        return this;
    }

    public AmayaBuilder<T> removeController(String path) {
        Objects.requireNonNull(path);
        Controller controller = controllers.remove(path);
        if (config.isDebug()) {
            if (controller != null) {
                logger.debug("Remove controller \"" + controller.getRoute() + "\"=" + controller.getClass().getSimpleName());
            } else {
                logger.debug("Nothing has been deleted");
            }
        }
        return this;
    }

    public AmayaBuilder<T> controllerAnnotation(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
        if (config.isDebug()) {
            logger.debug("Set controller annotation to" + annotation.getSimpleName());
        }
        return this;
    }

    protected void findControllers() throws Throwable {
        if (annotation == null) {
            return;
        }
        Scanner<Map<String, Controller>> scanner = new ControllerScanner(annotation, factory);
        this.controllers.putAll(scanner.find());
    }

    /**
     * Builds and prepares the framework for launch.
     *
     * @return an {@link Amaya} instance ready to run.
     * @throws Exception if there are any exceptions during the build process.
     */
    public abstract Amaya<T> build() throws Exception;
}
