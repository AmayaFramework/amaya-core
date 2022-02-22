package io.github.amayaframework.core;

import io.github.amayaframework.core.config.AmayaConfig;
import io.github.amayaframework.core.config.ConfigProvider;
import io.github.amayaframework.core.configurators.AmayaConfigurator;
import io.github.amayaframework.core.configurators.PipelineConfigurator;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.controllers.Endpoint;
import io.github.amayaframework.core.scanners.ControllerScanner;
import io.github.amayaframework.core.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBuilder<T> {
    private static final String DEFAULT_PREFIX = "io.github.amayaframework.core.actions";
    protected final AmayaConfig config;
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final PipelineConfigurator defaultConfigurator;
    protected final Map<String, Controller> controllers;
    //    protected final List<PipelineConfigurator> configurators;
    protected Class<? extends Annotation> annotation;

    public AbstractBuilder(String pipelinePrefix) {
        controllers = new ConcurrentHashMap<>();
//        configurators = new LinkedList<>();
        defaultConfigurator = new AmayaConfigurator(pipelinePrefix);
        config = ConfigProvider.getConfig();
        resetValues();
    }

    public AbstractBuilder() {
        this(DEFAULT_PREFIX);
    }

    protected void resetValues() {
        annotation = Endpoint.class;
//        configurators.clear();
//        configurators.add(defaultConfigurator);
        controllers.clear();
    }

//    public AbstractBuilder<T> pipelineConfigurators(Collection<PipelineConfigurator> configurators) {
//        Objects.requireNonNull(configurators);
//        configurators.forEach(Objects::requireNonNull);
//        this.configurators.clear();
//        this.configurators.add(defaultConfigurator);
//        this.configurators.addAll(configurators);
//        if (config.isDebug()) {
//            logger.debug("Set pipeline configurators: " + configurators);
//        }
//        return this;
//    }

//    public AbstractBuilder<T> addConfigurator(PipelineConfigurator configurator) {
//        Objects.requireNonNull(configurator);
//        configurators.add(configurator);
//        if (config.isDebug()) {
//            logger.debug("Add pipeline configurator: " + configurator.getClass().getName());
//        }
//        return this;
//    }

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
