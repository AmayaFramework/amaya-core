package io.github.amayaframework.core.controllers;

import com.github.romanqed.util.Checks;
import io.github.amayaframework.core.ConfigProvider;
import io.github.amayaframework.core.config.AmayaConfig;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routers.MethodRouter;
import io.github.amayaframework.core.routes.MethodRoute;
import io.github.amayaframework.core.scanners.RouteScanner;
import io.github.amayaframework.core.util.DuplicateException;
import io.github.amayaframework.core.util.ParseUtil;
import io.github.amayaframework.core.wrapping.Packer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * <p>An abstract class that describes some necessary implementations for the
 * correct functioning of user controllers.</p>
 * <p>Automatically creates an internal router and scans the methods.</p>
 * <p>All user controllers should inherit from it.</p>
 */
public abstract class HttpController implements Controller {
    private static final String DUPLICATE_PATTERN = "Method %s with path \"%s\" at controller %s";
    private final Logger logger;
    private final MethodRouter router;
    private final List<MethodRoute> routes;
    private String route;

    public HttpController() {
        Class<?> clazz = getClass();
        logger = LoggerFactory.getLogger(clazz);
        AmayaConfig config = ConfigProvider.getConfig();
        router = config.getRouter();
        Packer packer = Checks.requireNonNullElse(Util.extractPacker(clazz), config.getRoutePacker());
        RouteScanner scanner = new RouteScanner(this, packer);
        Map<HttpMethod, List<MethodRoute>> found = scanner.safetyFind();
        routes = new LinkedList<>();
        found.forEach((method, routes) -> routes.forEach(route -> {
            try {
                router.addRoute(method, route);
            } catch (DuplicateException e) {
                String error = String.format(DUPLICATE_PATTERN, method, route.route(), clazz.getSimpleName());
                logger.error(error);
                throw new DuplicateException(error);
            }
        }));
        found.values().forEach(routes::addAll);
        if (config.isDebug()) {
            debugLog(found);
        }
    }

    private void debugLog(Map<HttpMethod, List<MethodRoute>> found) {
        StringBuilder message = new StringBuilder("Controller successfully initialized\nAdded methods: ");
        found.forEach((method, routes) -> {
            message.append(method).append('[');
            routes.forEach(route -> message.append('"').append(route.route()).append('"').append(", "));
            int position = message.lastIndexOf(", ");
            if (position > 0) {
                message.delete(position, position + 2);
            }
            message.append(']').append(',').append(' ');
        });
        int position = message.lastIndexOf(", ");
        if (position > 0) {
            message.delete(position, position + 2);
        }
        message.append("\nUsed router: ").append(router.getClass().getSimpleName());
        logger.debug(message.toString());
    }

    @Override
    public MethodRouter getRouter() {
        return router;
    }

    @Override
    public String getRoute() {
        return route;
    }

    @Override
    public void setRoute(String route) {
        Objects.requireNonNull(route);
        route = ParseUtil.normalizeRoute(route);
        ParseUtil.validateRoute(route);
        this.route = route;
    }

    @Override
    public Collection<MethodRoute> getRoutes() {
        return routes;
    }
}
