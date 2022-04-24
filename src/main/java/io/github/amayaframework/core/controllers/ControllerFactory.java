package io.github.amayaframework.core.controllers;

import com.github.romanqed.util.Checks;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routers.MethodRouter;
import io.github.amayaframework.core.routes.MethodRoute;
import io.github.amayaframework.core.scanners.RouteScanner;
import io.github.amayaframework.core.util.DuplicateException;
import io.github.amayaframework.core.util.ParseUtil;
import io.github.amayaframework.core.util.ReflectUtil;
import io.github.amayaframework.core.wrapping.Packer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

public class ControllerFactory {
    private static final String DUPLICATE_PATTERN = "Method %s with path \"%s\" at controller %s";
    private final Callable<? extends MethodRouter> router;
    private final Packer packer;

    public ControllerFactory(Callable<? extends MethodRouter> router, Packer packer) {
        this.router = Objects.requireNonNull(router);
        this.packer = Objects.requireNonNull(packer);
    }

    private String normalizeRoute(String route) {
        Objects.requireNonNull(route);
        route = ParseUtil.normalizeRoute(route);
        ParseUtil.validateRoute(route);
        return route;
    }

    public HttpController createController(String route, Object toPack) throws Exception {
        Objects.requireNonNull(toPack);
        route = normalizeRoute(route);
        Class<?> clazz = toPack.getClass();
        Packer packer = Checks.requireNonNullElse(ReflectUtil.extractPacker(clazz), this.packer);
        RouteScanner scanner = new RouteScanner(toPack, packer);
        Map<HttpMethod, List<MethodRoute>> found = scanner.find();
        MethodRouter router = this.router.call();
        List<MethodRoute> toPut = new LinkedList<>();
        found.forEach((method, routes) -> routes.forEach(value -> {
            try {
                router.addRoute(method, value);
            } catch (DuplicateException e) {
                String error = String.format(DUPLICATE_PATTERN, method, value.route(), clazz.getSimpleName());
                throw new DuplicateException(error);
            }
        }));
        found.values().forEach(toPut::addAll);
        return new HttpController(route, router, toPut);
    }
}
