package io.github.amayaframework.core;

import io.github.amayaframework.core.config.AmayaConfig;
import io.github.amayaframework.core.config.ConfigProvider;
import io.github.amayaframework.core.configurators.AmayaConfigurator;
import io.github.amayaframework.core.configurators.Configurator;
import io.github.amayaframework.core.configurators.ConfiguratorWrapper;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.controllers.Endpoint;
import io.github.amayaframework.core.handlers.PipelineHandler;
import io.github.amayaframework.core.scanners.ControllerScanner;
import io.github.amayaframework.core.util.Handler;
import io.github.amayaframework.core.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBuilder<T> {
    private static final String DEFAULT_PREFIX = "io.github.amayaframework.core.actions";
    private static final Handler<PipelineHandler> handler = new AmayaConfigurator();
    protected final AmayaConfig config;
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Map<String, Controller> controllers;
    protected final List<ConfiguratorWrapper> configurators;
    protected Class<? extends Annotation> annotation;

    public AbstractBuilder(String pipelinePrefix) {
        controllers = new ConcurrentHashMap<>();
        configurators = new LinkedList<>();
        config = ConfigProvider.getConfig();
        resetValues();
    }

    public AbstractBuilder() {
        this(DEFAULT_PREFIX);
    }

    protected void resetValues() {
        annotation = Endpoint.class;
        controllers.clear();
        configurators.clear();
    }

    public AbstractBuilder<T> addConfigurator(Configurator configurator) {
        Objects.requireNonNull(configurator);
        configurators.add(new ConfiguratorWrapper(configurator));
        return this;
    }

    public AbstractBuilder<T> removeConfigurator(Class<? extends Configurator> clazz) {
        Objects.requireNonNull(clazz);
        configurators.removeIf(e -> e.getBody().getClass() == clazz);
        return this;
    }

    protected void configure(PipelineHandler handler, Controller controller) throws Exception {
        for (ConfiguratorWrapper configurator : configurators) {
            configurator.configure(handler, controller);
        }
    }

    public AbstractBuilder<T> addController(Controller controller) {
        Objects.requireNonNull(controller);
        String path = controller.getPath();
        Objects.requireNonNull(path);
        controllers.put(path, controller);
        if (config.isDebug()) {
            logger.debug("Add controller \"" + controller.getPath() + "\"=" + controller.getClass().getSimpleName());
        }
        return this;
    }

    public AbstractBuilder<T> removeController(String path) {
        Objects.requireNonNull(path);
        Controller controller = controllers.remove(path);
        if (config.isDebug()) {
            if (controller != null) {
                logger.debug("Remove controller \"" + controller.getPath() + "\"=" + controller.getClass().getSimpleName());
            } else {
                logger.debug("Nothing has been deleted");
            }
        }
        return this;
    }

    public AbstractBuilder<T> controllerAnnotation(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
        if (config.isDebug()) {
            logger.debug("Set controller annotation to" + annotation.getSimpleName());
        }
        return this;
    }

    protected void findControllers() {
        if (annotation == null) {
            return;
        }
        Set<Controller> controllers = new ControllerScanner(annotation).safetyFind();
        controllers.forEach(this::addController);
    }

    protected void printLogMessage() {
        logger.info("Amaya initialized successfully");
        logger.info("\n" + IOUtil.readLogo());
        logger.info("We are glad to welcome you, senpai!");
        logger.info("\n" + IOUtil.readArt());
    }

    public abstract T build() throws Exception;
}
