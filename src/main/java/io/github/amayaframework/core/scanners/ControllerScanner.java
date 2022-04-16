package io.github.amayaframework.core.scanners;

import io.github.amayaframework.core.config.ConfigProvider;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.util.InvalidRouteFormatException;
import io.github.amayaframework.core.util.ParseUtil;
import io.github.amayaframework.core.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ControllerScanner implements Scanner<Set<Controller>> {
    private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);
    private final Class<? extends Annotation> annotationClass;

    public ControllerScanner(Class<? extends Annotation> annotationClass) {
        this.annotationClass = Objects.requireNonNull(annotationClass);
    }

    @Override
    public Set<Controller> find()
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Map<String, Controller> found =
                ReflectionUtil.findAnnotatedWithValue(annotationClass, Controller.class, String.class);
        for (Map.Entry<String, Controller> entry : found.entrySet()) {
            String path = entry.getKey();
            if (path.equals("/")) {
                path = "";
            }
            if (!path.isEmpty() && !ParseUtil.ROUTE.matcher(path).matches()) {
                logger.error("Invalid route format: " + path);
                throw new InvalidRouteFormatException(path);
            }
            entry.getValue().setPath(path);
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
                append(e.getPath()).
                append('"').
                append('=').
                append(e.getClass().getSimpleName()).
                append(", "));
        int position = message.lastIndexOf(", ");
        if (position > 0) {
            message.delete(position, position + 2);
        }
        logger.debug(message.toString());
    }
}
