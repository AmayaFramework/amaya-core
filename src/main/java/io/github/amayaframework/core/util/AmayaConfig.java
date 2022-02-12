package io.github.amayaframework.core.util;

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
 * <p>The values available for customization are listed in {@link Field} enum</p>
 * <p>All values are set by default, so if the user expects standard behavior, he should not change anything.</p>
 * <p>To learn more about the config values,
 * see the documentation in the project repository or javadoc in {@link Field} enum</p>
 */
public class AmayaConfig {
    public static final AmayaConfig INSTANCE;
    private static final Logger logger;

    static {
        logger = LoggerFactory.getLogger(AmayaConfig.class);
        INSTANCE = new AmayaConfig();
    }

    private final Map<Field, Object> fields;

    public AmayaConfig() {
        fields = new ConcurrentHashMap<>();
        setDebug(logger.isDebugEnabled());
        setRoutePacker(new InjectPacker());
        setRouter(RegexpRouter.class);
        setCharset(StandardCharsets.UTF_8);
        setBacklog(0);
        setUseNativeNames(true);
    }

    public void setField(Field field, Object value) {
        Objects.requireNonNull(field);
        Objects.requireNonNull(value);
        fields.put(field, value);
        if (isDebug()) {
            logger.debug("Field " + field + " set with value " + value);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getField(Field field) {
        Objects.requireNonNull(field);
        return (T) fields.get(field);
    }

    public Packer getRoutePacker() {
        return getField(Field.ROUTE_PACKER);
    }

    public void setRoutePacker(Packer packer) {
        setField(Field.ROUTE_PACKER, packer);
    }

    public MethodRouter getRouter() {
        try {
            Class<?> routerClass = getField(Field.ROUTER);
            return (MethodRouter) routerClass.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Can not instantiate Router!", e);
        }
    }

    public void setRouter(Class<? extends MethodRouter> routerClass) {
        this.setField(Field.ROUTER, routerClass);
    }

    public Charset getCharset() {
        return getField(Field.CHARSET);
    }

    public void setCharset(Charset charset) {
        setField(Field.CHARSET, charset);
    }

    public int getBacklog() {
        return getField(Field.BACKLOG);
    }

    public void setBacklog(int backlog) {
        setField(Field.BACKLOG, backlog);
    }

    public boolean isDebug() {
        return getField(Field.DEBUG);
    }

    public void setDebug(boolean debug) {
        setField(Field.DEBUG, debug);
    }

    public boolean useNativeNames() {
        return getField(Field.USE_NATIVE_NAMES);
    }

    public void setUseNativeNames(boolean useNativeNames) {
        setField(Field.USE_NATIVE_NAMES, useNativeNames);
    }

    public enum Field {
        /**
         * The packer that will be used for each route found inside the controllers.
         * They may differ in the way the route method is called,
         * support/not support injecting values into the marked arguments,
         * etc.
         */
        ROUTE_PACKER,
        /**
         * The router that will be used in the controllers.
         */
        ROUTER,
        /**
         * The encoding that will be used when reading the request and sending the response.
         */
        CHARSET,
        /**
         * The backlog value that will be passed to the sun server.
         */
        BACKLOG,
        /**
         * Determines whether debugging mode will be enabled
         */
        DEBUG,
        /**
         * Determines whether the native parameter names or those specified by the annotation will be used
         */
        USE_NATIVE_NAMES
    }
}
