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
    }

    public void setField(Field field, Object value) {
        Objects.requireNonNull(field);
        fields.put(field, value);
        if (getDebug()) {
            logger.debug("Field " + field + " set with value " + value);
        }
    }

    public Object getField(Field field) {
        Objects.requireNonNull(field);
        return fields.get(field);
    }

    public Packer getRoutePacker() {
        return (Packer) fields.get(Field.ROUTE_PACKER);
    }

    public void setRoutePacker(Packer packer) {
        Objects.requireNonNull(packer);
        fields.put(Field.ROUTE_PACKER, packer);
    }

    public MethodRouter getRouter() {
        try {
            Class<?> routerClass = (Class<?>) fields.get(Field.ROUTER);
            return (MethodRouter) routerClass.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Can not instantiate Router!", e);
        }
    }

    public void setRouter(Class<? extends MethodRouter> routerClass) {
        Objects.requireNonNull(routerClass);
        this.setField(Field.ROUTER, routerClass);
    }

    public Charset getCharset() {
        return (Charset) fields.get(Field.CHARSET);
    }

    public void setCharset(Charset charset) {
        Objects.requireNonNull(charset);
        this.setField(Field.CHARSET, charset);
    }

    public Integer getBacklog() {
        return (Integer) fields.get(Field.BACKLOG);
    }

    public void setBacklog(int backlog) {
        this.setField(Field.BACKLOG, backlog);
    }

    public Boolean getDebug() {
        return (Boolean) fields.get(Field.DEBUG);
    }

    public void setDebug(boolean debug) {
        this.setField(Field.DEBUG, debug);
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
        DEBUG
    }
}
