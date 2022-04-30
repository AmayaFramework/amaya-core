package io.github.amayaframework.core.scanners;

import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.controllers.HttpControllerFactory;
import io.github.amayaframework.core.util.ReflectUtil;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ControllerScanner implements Scanner<Map<String, Controller>> {
    private final Class<? extends Annotation> annotationClass;
    private final HttpControllerFactory factory;

    public ControllerScanner(Class<? extends Annotation> annotationClass, HttpControllerFactory factory) {
        this.annotationClass = Objects.requireNonNull(annotationClass);
        this.factory = Objects.requireNonNull(factory);
    }

    @Override
    public Map<String, Controller> find() throws Throwable {
        Map<String, Object> found = ReflectUtil.findAnnotatedWithValue(annotationClass, Object.class);
        Map<String, Controller> ret = new HashMap<>();
        for (Map.Entry<String, Object> entry : found.entrySet()) {
            String route = entry.getKey();
            ret.put(route, factory.create(route, entry.getValue()));
        }
        return ret;
    }
}
