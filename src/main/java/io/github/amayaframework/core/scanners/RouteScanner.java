package io.github.amayaframework.core.scanners;

import com.github.romanqed.jutils.util.Action;
import com.github.romanqed.jutils.util.Pair;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routes.MethodRoute;
import io.github.amayaframework.core.util.ReflectUtil;
import io.github.amayaframework.core.wrapping.Packer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class RouteScanner implements Scanner<Map<HttpMethod, List<MethodRoute>>> {
    private final Controller instance;
    private final Class<? extends Controller> clazz;
    private final Packer packer;

    public RouteScanner(Controller controller, Packer packer) {
        this.instance = Objects.requireNonNull(controller);
        this.clazz = instance.getClass();
        this.packer = Objects.requireNonNull(packer);
    }

    public Map<HttpMethod, List<MethodRoute>> find() throws InvocationTargetException, IllegalAccessException {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        Map<HttpMethod, List<MethodRoute>> ret = new HashMap<>();
        List<Pair<HttpMethod, String>> found;
        for (Method method : declaredMethods) {
            found = ReflectUtil.extractMethodRoutes(method);
            if (!found.isEmpty()) {
                parseRoutes(method, found).forEach((httpMethod, routes) ->
                        ret.computeIfAbsent(httpMethod, key -> new ArrayList<>()).addAll(routes));
            }
        }
        return ret;
    }

    private Map<HttpMethod, List<MethodRoute>> parseRoutes(Method method, List<Pair<HttpMethod, String>> source) {
        Action<HttpRequest, HttpResponse> body = packer.checkedPack(instance, method);
        Map<HttpMethod, List<MethodRoute>> ret = new HashMap<>();
        for (Pair<HttpMethod, String> route : source) {
            ret.computeIfAbsent(route.getKey(), key -> new ArrayList<>()).
                    add(new MethodRoute(route.getValue(), method, body));
        }
        return ret;
    }
}
