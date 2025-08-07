package io.github.amayaframework.web;

/**
 * A utility class containing constant names for configuration option groups
 * and default values used by the web application module.
 * <p>
 * These constants are intended to standardize keys for configuration options
 * across the web application framework.
 */
public final class WebOptions {
    private WebOptions() {
    }

    /**
     * The name of the environment options group.
     */
    public static final String ENVIRONMENT_GROUP = "env";
    /**
     * The name of the server options group.
     */
    public static final String SERVER_GROUP = "server";
    /**
     * The name of the service manager options group.
     */
    public static final String MANAGER_GROUP = "manager";
    /**
     * The default name of the web application environment.
     */
    public static final String DEFAULT_ENVIRONMENT_NAME = "webapp";
}
