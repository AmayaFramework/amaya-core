package io.github.amayaframework.environment;

import io.github.amayaframework.options.Key;

public final class EnvOptions {
    private EnvOptions() {
    }

    /**
     * The string key for {@link io.github.amayaframework.options.OptionSet}
     * that allows you to set the mount point of the environment. By default, '.'.
     */
    public static final Key<String> ROOT = Key.of("root", String.class);

    /**
     * The boolean key for {@link io.github.amayaframework.options.OptionSet}
     * that allows you to set the initialization of the environment during creation. By default, 'true'.
     */
    public static final String INIT = "init";
}
