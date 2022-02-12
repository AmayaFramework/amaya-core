package io.github.amayaframework.core.config;

import java.util.Objects;

public class ConfigProvider {
    private static AmayaConfig config;

    public static AmayaConfig getConfig() {
        if (config == null) {
            config = new AmayaConfig();
        }
        return config;
    }

    public static void setConfig(AmayaConfig config) {
        ConfigProvider.config = Objects.requireNonNull(config);
    }
}
