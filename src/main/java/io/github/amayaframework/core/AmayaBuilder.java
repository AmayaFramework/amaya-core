package io.github.amayaframework.core;

import com.github.romanqed.jutils.util.Handler;
import io.github.amayaframework.core.config.AmayaConfig;
import io.github.amayaframework.core.config.ConfigProvider;
import io.github.amayaframework.core.configurators.AmayaConfigurator;
import io.github.amayaframework.core.configurators.Configurator;
import io.github.amayaframework.core.configurators.ConfiguratorWrapper;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.controllers.Endpoint;
import io.github.amayaframework.core.handlers.PipelineHandler;
import io.github.amayaframework.core.scanners.ControllerScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AmayaBuilder<T> {
    private static final String DEFAULT_PREFIX = "io.github.amayaframework.core.actions";
    protected final AmayaConfig config;
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Map<String, Controller> controllers;
    protected final List<ConfiguratorWrapper> configurators;
    private final Handler<PipelineHandler> configurator;
    protected Class<? extends Annotation> annotation;

    public AmayaBuilder(String pipelinePrefix) {
        controllers = new ConcurrentHashMap<>();
        configurator = new AmayaConfigurator(pipelinePrefix);
        configurators = new LinkedList<>();
        config = ConfigProvider.getConfig();
        resetValues();
    }

    public AmayaBuilder() {
        this(DEFAULT_PREFIX);
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

    public AmayaBuilder<T> addController(Controller controller) {
        Objects.requireNonNull(controller);
        String path = controller.getPath();
        Objects.requireNonNull(path);
        controllers.put(path, controller);
        if (config.isDebug()) {
            logger.debug("Add controller \"" + controller.getPath() + "\"=" + controller.getClass().getSimpleName());
        }
        return this;
    }

    public AmayaBuilder<T> removeController(String path) {
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

    public AmayaBuilder<T> controllerAnnotation(Class<? extends Annotation> annotation) {
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

    public abstract Amaya<T> build() throws Exception;
}
