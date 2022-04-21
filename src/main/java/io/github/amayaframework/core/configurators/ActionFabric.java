package io.github.amayaframework.core.configurators;

import com.github.romanqed.jutils.lambdas.Action;

import java.util.Objects;

// TODO Add action constructors proxy
public final class ActionFabric {
    private final ClassLoader loader;
    private final String prefix;

    public ActionFabric(String prefix) {
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
