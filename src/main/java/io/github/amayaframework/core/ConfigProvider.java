package io.github.amayaframework.core;

import io.github.amayaframework.core.config.AmayaConfig;
import io.github.amayaframework.core.config.Config;
import io.github.amayaframework.core.config.ConfigRepository;

/**
 * Global repository of the framework configs.
 * <h3>Important!</h3>
 * <p>Do not load configs at any stage other than initialization,
 * <p>this may cause the config of another initialization of the framework to load</p>
 * <p>(if there were several initializations).</p>
 */
public final class ConfigProvider {
    public static final Object LOCK = new Object();
    private static final Class<?> AMAYA_CLASS = Amaya.class;
    private static final ConfigRepository INSTANCE;

    static {
        INSTANCE = new ConfigRepository();
        INSTANCE.put(AMAYA_CLASS, new AmayaConfig());
    }

    private ConfigProvider() {
    }

    public static Config getConfig(Class<?> clazz) {
        return INSTANCE.get(clazz);
    }

    public static Config setConfig(Class<?> clazz, Config config) {
        synchronized (LOCK) {
            return INSTANCE.put(clazz, config);
        }
    }

    public static Runnable lockConfig(Class<?> clazz) {
        synchronized (LOCK) {
            return INSTANCE.lock(clazz);
        }
    }

    public static AmayaConfig getConfig() {
        return (AmayaConfig) INSTANCE.get(AMAYA_CLASS);
    }

    static Runnable lockConfig() {
        return lockConfig(AMAYA_CLASS);
    }

    static AmayaConfig setConfig(AmayaConfig config) {
        return (AmayaConfig) setConfig(AMAYA_CLASS, config);
    }
}
