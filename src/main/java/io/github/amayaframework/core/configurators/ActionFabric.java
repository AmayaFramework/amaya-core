package io.github.amayaframework.core.configurators;

import com.github.romanqed.jutils.util.Action;

import java.util.Objects;

public class ActionFabric {
    private final ClassLoader loader;
    private final String prefix;

    public ActionFabric(String prefix) {
        loader = ClassLoader.getSystemClassLoader();
        this.prefix = Objects.requireNonNull(prefix);
    }

    public ActionFabric() {
        this("");
    }

    @SuppressWarnings("unchecked")
    public Action<?, ?> makeAction(String name)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String path = name;
        if (!prefix.isEmpty()) {
            path = prefix + "." + path;
        }
        Class<? extends Action<?, ?>> clazz = (Class<? extends Action<?, ?>>) loader.loadClass(path);
        return clazz.newInstance();
    }
}