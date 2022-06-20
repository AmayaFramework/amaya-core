package io.github.amayaframework.core.scanners;

import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.controllers.ControllerFactory;
import io.github.amayaframework.core.controllers.Endpoint;
import io.github.amayaframework.core.util.ReflectUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ControllerScanner implements Scanner<String, Controller> {
    private final ControllerFactory factory;

    public ControllerScanner(ControllerFactory factory) {
        this.factory = Objects.requireNonNull(factory);
    }

    @Override
    public Map<String, Controller> find() throws Throwable {
        Map<String, Object> found = ReflectUtil.findAnnotatedWithValue(Endpoint.class, Object.class);
        Map<String, Controller> ret = new HashMap<>();
        for (Map.Entry<String, Object> entry : found.entrySet()) {
            String route = entry.getKey();
            ret.put(route, factory.create(route, entry.getValue()));
        }
        return ret;
    }
}
