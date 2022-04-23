package io.github.amayaframework.core.configurators;

import com.github.romanqed.util.Action;

import java.util.Objects;

public final class ActionFactory {
    private final ClassLoader loader;
    private final String prefix;

    public ActionFactory(String prefix) {
        loader = ClassLoader.getSystemClassLoader();
        this.prefix = Objects.requireNonNull(prefix);
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
