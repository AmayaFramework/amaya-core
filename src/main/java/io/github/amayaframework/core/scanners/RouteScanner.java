package io.github.amayaframework.core.scanners;

import com.github.romanqed.util.Action;
import com.github.romanqed.util.Checks;
import com.github.romanqed.util.Pair;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routes.MethodRoute;
import io.github.amayaframework.core.util.ReflectUtil;
import io.github.amayaframework.core.wrapping.Packer;

import java.lang.reflect.Method;
import java.util.*;

public class RouteScanner implements Scanner<Map<HttpMethod, List<MethodRoute>>> {
    private final Object instance;
    private final Class<?> clazz;
    private final Packer packer;

    public RouteScanner(Object instance, Packer packer) {
        this.instance = Objects.requireNonNull(instance);
        this.clazz = instance.getClass();
        this.packer = Objects.requireNonNull(packer);
    }

    public Map<HttpMethod, List<MethodRoute>> find() throws Throwable {
        Method[] methods = clazz.getDeclaredMethods();
        Map<HttpMethod, List<MethodRoute>> ret = new HashMap<>();
        List<Pair<HttpMethod, String>> found;
        for (Method method : methods) {
            found = ReflectUtil.extractMethodRoutes(method);
            if (found.isEmpty()) {
                continue;
            }
            Map<HttpMethod, List<MethodRoute>> routes = parseRoutes(method, found);
            for (Map.Entry<HttpMethod, List<MethodRoute>> entry : routes.entrySet()) {
                ret.computeIfAbsent(entry.getKey(), key -> new ArrayList<>()).addAll(entry.getValue());
            }
        }
        return ret;
    }

    private Map<HttpMethod, List<MethodRoute>> parseRoutes(Method method, List<Pair<HttpMethod, String>> source)
            throws Throwable {
        Packer packer = Checks.requireNonNullElse(ReflectUtil.extractPacker(method), this.packer);
        Action<HttpRequest, HttpResponse> body = packer.pack(instance, method);
        Map<HttpMethod, List<MethodRoute>> ret = new HashMap<>();
        for (Pair<HttpMethod, String> route : source) {
            MethodRoute methodRoute = new MethodRoute(route.getValue(), method, body);
            ret.computeIfAbsent(route.getKey(), key -> new ArrayList<>()).add(methodRoute);
        }
        return ret;
    }
}
