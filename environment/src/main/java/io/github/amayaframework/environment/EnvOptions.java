package io.github.amayaframework.environment;

import io.github.amayaframework.options.Key;

/**
 * Defines the option keys used for configuring an {@link Environment}.
 * These keys are used with the {@link io.github.amayaframework.options.OptionSet}.
 */
public final class EnvOptions {
    private EnvOptions() {
    }

    /**
     * The string key for {@link io.github.amayaframework.options.OptionSet}
     * that allows you to set the mount point of the environment. Default is {@code "."}.
     */
    public static final Key<String> ROOT = Key.of("root", String.class);

    /**
     * The string key for {@link io.github.amayaframework.options.OptionSet}
     * that allows you to control whether the environment should be initialized upon creation.
     * Default is {@code "true"}.
     */
    public static final String INIT = "init";
}
