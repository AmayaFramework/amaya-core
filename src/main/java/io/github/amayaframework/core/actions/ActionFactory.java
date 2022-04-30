package io.github.amayaframework.core.actions;

import com.github.romanqed.util.Action;
import io.github.amayaframework.core.WithConfig;
import io.github.amayaframework.core.config.AmayaConfig;

import java.util.Objects;

public final class ActionFactory {
    private final ClassLoader loader;
    private final String prefix;
    private final AmayaConfig config;

    public ActionFactory(String prefix, AmayaConfig config) {
        loader = ClassLoader.getSystemClassLoader();
        this.prefix = Objects.requireNonNull(prefix);
        this.config = Objects.requireNonNull(config);
    }

    @SuppressWarnings("unchecked")
    public Action<?, ?> makeAction(String name) throws Throwable {
        String path = name;
        if (!prefix.isEmpty()) {
            path = prefix + "." + path;
        }
        Class<? extends Action<?, ?>> clazz = (Class<? extends Action<?, ?>>) loader.loadClass(path);
        if (clazz.isAnnotationPresent(WithConfig.class)) {
            return clazz.getDeclaredConstructor(AmayaConfig.class).newInstance(config);
        }
        return clazz.getDeclaredConstructor().newInstance();
    }
}
