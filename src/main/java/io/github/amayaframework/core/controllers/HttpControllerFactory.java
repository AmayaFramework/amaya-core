package io.github.amayaframework.core.controllers;

import com.github.romanqed.util.Checks;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routers.MethodRouter;
import io.github.amayaframework.core.routes.MethodRoute;
import io.github.amayaframework.core.scanners.RouteScanner;
import io.github.amayaframework.core.util.DuplicateException;
import io.github.amayaframework.core.util.ParseUtil;
import io.github.amayaframework.core.util.ReflectUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * A factory that creates http controllers.
 */
public class HttpControllerFactory implements ControllerFactory {
    private static final String DUPLICATE_PATTERN = "Method %s with path \"%s\" at controller %s";
    private final Callable<? extends MethodRouter> router;
    private final Packer packer;

    public HttpControllerFactory(Callable<? extends MethodRouter> router, Packer packer) {
        this.router = Objects.requireNonNull(router);
        this.packer = Objects.requireNonNull(packer);
    }

    private String normalizeRoute(String route) {
        Objects.requireNonNull(route);
        route = ParseUtil.normalizeRoute(route);
        ParseUtil.validateRoute(route);
        return route;
    }

    private MethodRouter extractRouter(Class<?> clazz) throws Exception {
        UseRouter router = clazz.getAnnotation(UseRouter.class);
        if (router == null) {
            return this.router.call();
        }
        Class<? extends MethodRouter> type = router.value();
        Callable<? extends MethodRouter> ret = ReflectUtil.findMethodRouter(type);
        return ret.call();
    }

    public HttpController create(String route, Object source) throws Throwable {
        Objects.requireNonNull(source);
        // Prepare data
        route = normalizeRoute(route);
        Class<?> clazz = source.getClass();
        Packer packer = Checks.requireNonNullElse(ReflectUtil.extractPacker(clazz), this.packer);
        // Scan routes
        RouteScanner scanner = new RouteScanner(source, packer);
        Map<HttpMethod, List<MethodRoute>> found = scanner.find();
        // Create controller instance
        MethodRouter router = extractRouter(clazz);
        List<MethodRoute> toPut = new LinkedList<>();
        found.forEach((method, routes) -> routes.forEach(value -> {
            try {
                router.addRoute(method, value);
            } catch (DuplicateException e) {
                String error = String.format(DUPLICATE_PATTERN, method, value.getRoute(), clazz.getSimpleName());
                throw new DuplicateException(error);
            }
        }));
        found.values().forEach(toPut::addAll);
        return new HttpController(route, router, toPut);
    }
}
