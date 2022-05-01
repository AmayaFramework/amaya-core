package io.github.amayaframework.core.config;

import io.github.amayaframework.core.controllers.Packer;
import io.github.amayaframework.core.inject.InjectPacker;
import io.github.amayaframework.core.routers.MethodRouter;
import io.github.amayaframework.core.routers.RegexpRouter;
import io.github.amayaframework.core.util.ReflectUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

/**
 * <p>A class representing the framework config.</p>
 * <p>All values are set by default, so if the user expects standard behavior, he should not change anything.</p>
 * <p>To learn more about the config values,
 * see the documentation in the project repository or javadoc in {@link AmayaConfig}</p>
 */
public class AmayaConfig extends Config {
    /**
     * The packer that will be used for each getRoute found inside the controllers.
     * They may differ in the way the getRoute method is called,
     * support/not support injecting values into the marked arguments,
     * etc.
     */
    public static final Field<Packer> ROUTE_PACKER = new Field<>("ROUTE_PACKER", Packer.class);
    /**
     * The router that will be used in the controllers.
     */
    public static final Field<Callable<? extends MethodRouter>> ROUTER = new Field<>("ROUTER", Callable.class);
    /**
     * The encoding that will be used when reading the request and sending the response.
     */
    public static final Field<Charset> CHARSET = new Field<>("CHARSET", Charset.class);
    /**
     * Determines whether debugging mode will be enabled
     */
    public static final Field<Boolean> DEBUG = new Field<>("DEBUG", Boolean.class);
    /**
     * Determines whether debugging information about the received request will be output.
     */
    public static final Field<Boolean> DEBUG_PRINT_REQUEST = new Field<>("PRINT_REQUEST", Boolean.class);
    /**
     * Determines whether debugging information about the received response will be output.
     */
    public static final Field<Boolean> DEBUG_PRINT_RESPONSE = new Field<>("PRINT_RESPONSE", Boolean.class);
    /**
     * Determines whether asynchronous calls will be used.
     */
    public static final Field<Boolean> USE_ASYNC = new Field<>("USE_ASYNC", Boolean.class);

    public AmayaConfig() {
        setDebug(false);
        setPrintRequest(false);
        setPrintResponse(false);
        setRoutePacker(new InjectPacker());
        setCharset(StandardCharsets.UTF_8);
        setUseAsync(true);
        setRouter(RegexpRouter::new);
    }

    public Packer getRoutePacker() {
        return getField(ROUTE_PACKER);
    }

    public void setRoutePacker(Packer packer) {
        setField(ROUTE_PACKER, packer);
    }

    public Callable<? extends MethodRouter> getRouter() {
        return getField(ROUTER);
    }

    public void setRouter(Callable<? extends MethodRouter> router) {
        setField(ROUTER, router);
    }

    public void setRouter(Class<? extends MethodRouter> clazz) {
        try {
            Callable<? extends MethodRouter> toSet = ReflectUtil.findMethodRouter(clazz);
            setField(ROUTER, toSet);
        } catch (Throwable e) {
            throw new IllegalStateException("Can't instantiate this router", e);
        }
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

    public boolean useAsync() {
        return getField(USE_ASYNC);
    }

    public void setUseAsync(boolean useAsync) {
        setField(USE_ASYNC, useAsync);
    }

    public void setPrintRequest(boolean printRequest) {
        setField(DEBUG_PRINT_REQUEST, printRequest);
    }

    public void setPrintResponse(boolean printResponse) {
        setField(DEBUG_PRINT_RESPONSE, printResponse);
    }

    public boolean needDebugPrintRequest() {
        return getField(DEBUG_PRINT_REQUEST);
    }

    public boolean needDebugPrintResponse() {
        return getField(DEBUG_PRINT_RESPONSE);
    }
}
