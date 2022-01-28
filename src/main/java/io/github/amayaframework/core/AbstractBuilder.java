package io.github.amayaframework.core;

import io.github.amayaframework.core.configurators.Configurator;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.controllers.Endpoint;
import io.github.amayaframework.core.scanners.ControllerScanner;
import io.github.amayaframework.core.util.AmayaConfig;
import io.github.amayaframework.core.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBuilder {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Configurator defaultConfigurator;
    protected final Map<String, Controller> controllers;
    protected final List<Configurator> configurators;
    protected Class<? extends Annotation> annotation;

    public AbstractBuilder(Configurator defaultConfigurator) {
        controllers = new ConcurrentHashMap<>();
        configurators = new LinkedList<>();
        this.defaultConfigurator = defaultConfigurator;
        resetValues();
    }

    protected void resetValues() {
        annotation = Endpoint.class;
        configurators.clear();
        configurators.add(defaultConfigurator);
        controllers.clear();
    }

    public AbstractBuilder pipelineConfigurators(Collection<Configurator> configurators) {
        Objects.requireNonNull(configurators);
        configurators.forEach(Objects::requireNonNull);
        this.configurators.clear();
        this.configurators.add(defaultConfigurator);
        this.configurators.addAll(configurators);
        if (AmayaConfig.INSTANCE.getDebug()) {
            logger.debug("Set pipeline configurators: " + configurators);
        }
        return this;
    }

    public AbstractBuilder addConfigurator(Configurator configurator) {
        Objects.requireNonNull(configurator);
        configurators.add(configurator);
        if (AmayaConfig.INSTANCE.getDebug()) {
            logger.debug("Add pipeline configurator: " + configurator.getClass().getName());
        }
        return this;
    }

    public AbstractBuilder addController(Controller controller) {
        Objects.requireNonNull(controller);
        String path = controller.getPath();
        Objects.requireNonNull(path);
        controllers.put(path, controller);
        if (AmayaConfig.INSTANCE.getDebug()) {
            logger.debug("Add controller \"" + controller.getPath() + "\"=" + controller.getClass().getSimpleName());
        }
        return this;
    }

    public AbstractBuilder removeController(String path) {
        Objects.requireNonNull(path);
        Controller controller = controllers.remove(path);
        if (AmayaConfig.INSTANCE.getDebug()) {
            if (controller != null) {
                logger.debug("Remove controller \"" + controller.getPath() + "\"=" + controller.getClass().getSimpleName());
            } else {
                logger.debug("Nothing has been deleted");
            }
        }
        return this;
    }

    public AbstractBuilder controllerAnnotation(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
        if (AmayaConfig.INSTANCE.getDebug()) {
            logger.debug("Set controller annotation to" + annotation.getSimpleName());
        }
        return this;
    }

    protected void findControllers() {
        if (annotation == null) {
            return;
        }
        Set<Controller> controllers;
        try {
            controllers = new ControllerScanner(annotation).find();
        } catch (Exception e) {
            throw new IllegalStateException("Exception when scanning controllers!", e);
        }
        controllers.forEach(this::addController);
    }

    protected void printLogMessage() {
        logger.info("Amaya initialized successfully");
        logger.info("\n" + IOUtil.readLogo());
        logger.info("We are glad to welcome you, senpai!");
        logger.info("\n" + IOUtil.readArt());
    }
}
