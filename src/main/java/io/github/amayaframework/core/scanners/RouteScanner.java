package io.github.amayaframework.core.scanners;

import com.github.romanqed.util.Action;
import com.github.romanqed.util.Checks;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routes.MethodRoute;
import io.github.amayaframework.core.wrapping.Packer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

import static com.github.romanqed.jeflect.ReflectUtil.extractAnnotationValue;
import static io.github.amayaframework.core.util.ReflectUtil.extractPacker;

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
        Map<HttpMethod, List<MethodRoute>> ret = new HashMap<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            processMethod(method, ret);
        }
        return ret;
    }

    private void processMethod(Method method, Map<HttpMethod, List<MethodRoute>> map) throws Throwable {
        Annotation[] annotations = method.getDeclaredAnnotations();
        Action<HttpRequest, HttpResponse> packed = null;
        for (Annotation annotation : annotations) {
            HttpMethod httpMethod = HttpMethod.fromAnnotation(annotation);
            if (httpMethod == null) {
                continue;
            }
            String route = extractAnnotationValue(annotation, String.class);
            if (packed == null) {
                packed = packMethod(method);
            }
            MethodRoute toPut = new MethodRoute(route, method, packed);
            map.computeIfAbsent(httpMethod, key -> new LinkedList<>()).add(toPut);
        }
    }

    private Action<HttpRequest, HttpResponse> packMethod(Method method) throws Throwable {
        Packer packer = Checks.requireNonNullElse(extractPacker(method), this.packer);
        return packer.pack(instance, method);
    }
}
