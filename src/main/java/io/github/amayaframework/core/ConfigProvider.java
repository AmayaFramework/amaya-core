package io.github.amayaframework.core;

import io.github.amayaframework.core.config.AmayaConfig;
import io.github.amayaframework.core.config.Config;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Global repository of the framework configs.
 * <h3>Important!</h3>
 * <p>Do not load configs at any stage other than initialization,
 * <p>this may cause the config of another initialization of the framework to load</p>
 * <p>(if there were several initializations).</p>
 */
public final class ConfigProvider {
    private static final Class<?> AMAYA_CLASS = Amaya.class;
    private static final ConfigProvider INSTANCE = new ConfigProvider();

    private final Map<Class<?>, Config> body;

    private ConfigProvider() {
        body = new ConcurrentHashMap<>();
        body.put(AMAYA_CLASS, new AmayaConfig());
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
        if (clazz == AMAYA_CLASS) {
            throw new IllegalArgumentException("Can't modify config for Amaya!");
        }
        Objects.requireNonNull(config);
        return INSTANCE.body.put(clazz, config);
    }

    public static AmayaConfig getAmayaConfig() {
        return (AmayaConfig) INSTANCE.body.get(AMAYA_CLASS);
    }

    static void setAmayaConfig(AmayaConfig config) {
        Objects.requireNonNull(config);
        INSTANCE.body.put(AMAYA_CLASS, config);
    }
}
