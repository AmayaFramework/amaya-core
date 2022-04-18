package io.github.amayaframework.core.config;

import java.util.Objects;

public final class ConfigProvider {
    private static AmayaConfig config = new AmayaConfig();

    public static AmayaConfig getConfig() {
        return config;
    }

    public synchronized static void setConfig(AmayaConfig config) {
        ConfigProvider.config = Objects.requireNonNull(config);
    }
}
