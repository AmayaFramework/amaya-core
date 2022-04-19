package io.github.amayaframework.core.config;

import io.github.amayaframework.core.routers.MethodRouter;
import io.github.amayaframework.core.routers.RegexpRouter;
import io.github.amayaframework.core.wrapping.InjectPacker;
import io.github.amayaframework.core.wrapping.Packer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <p>A class representing the framework config.</p>
 * <p>All values are set by default, so if the user expects standard behavior, he should not change anything.</p>
 * <p>To learn more about the config values,
 * see the documentation in the project repository or javadoc in {@link AmayaConfig}</p>
 */
public class AmayaConfig extends Config {
    /**
     * The packer that will be used for each route found inside the controllers.
     * They may differ in the way the route method is called,
     * support/not support injecting values into the marked arguments,
     * etc.
     */
    public static final Field<Packer> ROUTE_PACKER = new Field<>("ROUTE_PACKER", Packer.class);
    /**
     * The router that will be used in the controllers.
     */
    public static final Field<Class<? extends MethodRouter>> ROUTER = new Field<>("ROUTER", Class.class);
    /**
     * The encoding that will be used when reading the request and sending the response.
     */
    public static final Field<Charset> CHARSET = new Field<>("CHARSET", Charset.class);
    /**
     * Determines whether debugging mode will be enabled
     */
    public static final Field<Boolean> DEBUG = new Field<>("DEBUG", Boolean.class);
    /**
     * Determines whether the native parameter names or those specified by the annotation will be used
     */
    public static final Field<Boolean> USE_NATIVE_NAMES = new Field<>("USE_NATIVE_NAMES", Boolean.class);
    /**
     * Determines whether asynchronous calls will be used.
     */
    public static final Field<Boolean> USE_ASYNC = new Field<>("USE_ASYNC", Boolean.class);

    public AmayaConfig() {
        setDebug(false);
        setRoutePacker(new InjectPacker());
        setRouter(RegexpRouter.class);
        setCharset(StandardCharsets.UTF_8);
        setUseNativeNames(true);
        setUseAsync(true);
    }

    public Packer getRoutePacker() {
        return getField(ROUTE_PACKER);
    }

    public void setRoutePacker(Packer packer) {
        setField(ROUTE_PACKER, packer);
    }

    public MethodRouter getRouter() {
        try {
            Class<? extends MethodRouter> routerClass = getField(ROUTER);
            return routerClass.newInstance();
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

    public boolean useAsync() {
        return getField(USE_ASYNC);
    }

    public void setUseAsync(boolean useAsync) {
        setField(USE_ASYNC, useAsync);
    }
}
