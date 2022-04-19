package io.github.amayaframework.core.config;

import io.github.amayaframework.core.Amaya;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class ConfigProvider {
    private static final Class<?> AMAYA_CLASS = Amaya.class;
    private static final ConfigProvider INSTANCE;

    static {
        INSTANCE = new ConfigProvider();
        INSTANCE.body.put(AMAYA_CLASS, new AmayaConfig());
    }

    private final Map<Class<?>, Config> body;

    private ConfigProvider() {
        body = new ConcurrentHashMap<>();
    }

    /**
     * Searches for the config associated with the class.
     *
     * @param clazz search class
     * @return found config or null
     */
    public static Config getConfig(Class<?> clazz) {
        Objects.requireNonNull(clazz);
        return INSTANCE.body.get(clazz);
    }

    /**
     * Sets the config and binds it to the class.
     *
     * @param clazz  bind class
     * @param config config to be bind
     * @return config previously bound to this class, or null
     */
    public static Config setConfig(Class<?> clazz, Config config) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(config);
        return INSTANCE.body.put(clazz, config);
    }

    public static AmayaConfig getAmayaConfig() {
        return (AmayaConfig) INSTANCE.body.get(AMAYA_CLASS);
    }
}
