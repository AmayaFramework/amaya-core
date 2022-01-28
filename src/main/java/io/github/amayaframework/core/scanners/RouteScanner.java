package io.github.amayaframework.core.scanners;

import com.github.romanqed.jutils.structs.Pair;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routers.Route;
import io.github.amayaframework.core.util.ReflectUtils;
import io.github.amayaframework.core.wrapping.Packer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

public class RouteScanner implements Scanner<Map<HttpMethod, List<Route>>> {
    private final Controller instance;
    private final Class<? extends Controller> clazz;
    private final Packer packer;

    public RouteScanner(Controller controller, Packer packer) {
        this.instance = Objects.requireNonNull(controller);
        this.clazz = instance.getClass();
        this.packer = Objects.requireNonNull(packer);
    }

    public Map<HttpMethod, List<Route>> find() throws InvocationTargetException, IllegalAccessException {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        Map<HttpMethod, List<Route>> ret = new HashMap<>();
        List<Pair<HttpMethod, String>> found;
        for (Method method : declaredMethods) {
            found = ReflectUtils.extractMethodRoutes(method);
            if (!found.isEmpty()) {
                parseRoutes(method, found).forEach((httpMethod, routes) ->
                        ret.computeIfAbsent(httpMethod, key -> new ArrayList<>()).addAll(routes));
            }
        }
        return ret;
    }

    private Map<HttpMethod, List<Route>> parseRoutes(Method method, List<Pair<HttpMethod, String>> source) {
        Function<HttpRequest, HttpResponse> body = packer.checkedPack(instance, method);
        Map<HttpMethod, List<Route>> ret = new HashMap<>();
        for (Pair<HttpMethod, String> route : source) {
            ret.computeIfAbsent(route.getKey(), key -> new ArrayList<>()).
                    add(Route.compile(route.getValue(), body));
        }
        return ret;
    }
}
