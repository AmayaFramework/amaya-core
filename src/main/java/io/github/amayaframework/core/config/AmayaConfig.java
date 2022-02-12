package io.github.amayaframework.core.config;

import io.github.amayaframework.core.routers.MethodRouter;
import io.github.amayaframework.core.routers.RegexpRouter;
import io.github.amayaframework.core.wrapping.InjectPacker;
import io.github.amayaframework.core.wrapping.Packer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Singleton representing the main configuration of the framework.</p>
 * <p>All values are set by default, so if the user expects standard behavior, he should not change anything.</p>
 * <p>To learn more about the config values,
 * see the documentation in the project repository or javadoc in {@link AmayaConfig}</p>
 */
public class AmayaConfig {
    /**
     * The packer that will be used for each route found inside the controllers.
     * They may differ in the way the route method is called,
     * support/not support injecting values into the marked arguments,
     * etc.
     */
    public static final String ROUTE_PACKER = "ROUTE_PACKER";

    /**
     * The router that will be used in the controllers.
     */
    public static final String ROUTER = "ROUTER";

    /**
     * The encoding that will be used when reading the request and sending the response.
     */
    public static final String CHARSET = "CHARSET";

    /**
     * Determines whether debugging mode will be enabled
     */
    public static final String DEBUG = "DEBUG";

    /**
     * Determines whether the native parameter names or those specified by the annotation will be used
     */
    public static final String USE_NATIVE_NAMES = "USE_NATIVE_NAMES";

    private final Logger logger;
    private final Map<String, Object> fields;

    public AmayaConfig() {
        logger = LoggerFactory.getLogger(AmayaConfig.class);
        fields = new ConcurrentHashMap<>();
        setDebug(logger.isDebugEnabled());
        setRoutePacker(new InjectPacker());
        setRouter(RegexpRouter.class);
        setCharset(StandardCharsets.UTF_8);
        setUseNativeNames(true);
    }

    public void setField(String field, Object value) {
        Objects.requireNonNull(field);
        Objects.requireNonNull(value);
        fields.put(field, value);
        if (isDebug()) {
            logger.debug("Field " + field + " set with value " + value);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getField(String field) {
        Objects.requireNonNull(field);
        return (T) fields.get(field);
    }

    public Packer getRoutePacker() {
        return getField(ROUTE_PACKER);
    }

    public void setRoutePacker(Packer packer) {
        setField(ROUTE_PACKER, packer);
    }

    public MethodRouter getRouter() {
        try {
            Class<?> routerClass = getField(ROUTER);
            return (MethodRouter) routerClass.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Can not instantiate Router!", e);
        }
    }

    public void setRouter(Class<? extends MethodRouter> routerClass) {
        this.setField(ROUTER, routerClass);
    }

    public Charset getCharset() {
        return getField(CHARSET);
    }

    public void setCharset(Charset charset) {
        setField(CHARSET, charset);
    }

    public boolean isDebug() {
        return getField(DEBUG);
    }

    public void setDebug(boolean debug) {
        setField(DEBUG, debug);
    }

    public boolean useNativeNames() {
        return getField(USE_NATIVE_NAMES);
    }

    public void setUseNativeNames(boolean useNativeNames) {
        setField(USE_NATIVE_NAMES, useNativeNames);
    }
}
