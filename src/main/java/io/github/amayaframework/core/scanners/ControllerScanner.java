package io.github.amayaframework.core.scanners;

import io.github.amayaframework.core.ConfigProvider;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ControllerScanner implements Scanner<Set<Controller>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerScanner.class);
    private final Class<? extends Annotation> annotationClass;

    public ControllerScanner(Class<? extends Annotation> annotationClass) {
        this.annotationClass = Objects.requireNonNull(annotationClass);
    }

    @Override
    public Set<Controller> find() throws InstantiationException, IllegalAccessException, NoSuchMethodException {
        Map<String, Controller> found = ReflectUtil.findAnnotatedWithValue(
                annotationClass, Controller.class, String.class
        );
        for (Map.Entry<String, Controller> entry : found.entrySet()) {
            entry.getValue().setRoute(entry.getKey());
        }
        Set<Controller> ret = new HashSet<>(found.values());
        if (ConfigProvider.getConfig().isDebug()) {
            debugPrint(ret);
        }
        return ret;
    }

    private void debugPrint(Set<Controller> controllers) {
        StringBuilder message = new StringBuilder("The scanner found controllers: \n");
        controllers.forEach(e -> message.append('"').
                append(e.getRoute()).
                append('"').
                append('=').
                append(e.getClass().getSimpleName()).
                append(", "));
        int position = message.lastIndexOf(", ");
        if (position > 0) {
            message.delete(position, position + 2);
        }
        LOGGER.debug(message.toString());
    }
}
