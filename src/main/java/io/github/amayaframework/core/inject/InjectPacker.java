package io.github.amayaframework.core.inject;

import com.github.romanqed.jeflect.ReflectUtil;
import com.github.romanqed.jeflect.lambdas.Lambda;
import com.github.romanqed.util.Action;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.controllers.Packer;
import io.github.amayaframework.core.util.InvalidFormatException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import static io.github.amayaframework.core.inject.Util.*;

/**
 * A class describing a packager that supports injection into method arguments.
 */
public final class InjectPacker implements Packer {
    private static final Class<HttpResponse> RESPONSE = HttpResponse.class;
    private static final String MESSAGE = "Invalid route method: ";
    private static final Map<Class<?>, SourceClass> REQUESTS = REQUEST_SCANNER.safetyFind();
    private final Map<Class<?>, Class<? extends HttpRequest>> requests = new HashMap<>();

    private Class<? extends HttpRequest> extractRequestClass(Class<?> controller, Method method) {
        Class<? extends HttpRequest> ret = requests.computeIfAbsent(
                controller, value -> Util.extractRequestClass(controller)
        );
        if (ret == null) {
            Class<? extends HttpRequest> found = Util.extractRequestClass(method);
            return found == null ? REQUEST : found;
        }
        return ret;
    }

    private int foundStart(Parameter[] parameters) {
        if (parameters.length == 0) {
            return -1;
        }
        if (REQUEST.isAssignableFrom(parameters[0].getType())) {
            return 1;
        }
        return 0;
    }

    @Override
    public Action<HttpRequest, HttpResponse> pack(Object instance, Method method) throws Throwable {
        if (!RESPONSE.isAssignableFrom(method.getReturnType())) {
            throw new InvalidFormatException(MESSAGE + method);
        }
        Parameter[] parameters = method.getParameters();
        int start = foundStart(parameters);
        // No parameters
        if (start < 0) {
            Lambda packed = ReflectUtil.packMethod(method, instance);
            return request -> (HttpResponse) packed.call();
        }
        // Extract request type
        Class<?> requestType;
        if (start == 1) {
            requestType = parameters[0].getType();
        } else {
            requestType = extractRequestClass(instance.getClass(), method);
        }
        SourceClass source = REQUESTS.get(requestType);
        if (source == null) {
            throw new IllegalStateException("The specified HttpRequest implementation is not supported");
        }
        Getter[] getters = new Getter[parameters.length - start];
        for (int i = start; i < parameters.length; ++i) {
            Parameter parameter = parameters[i];
            Annotation annotation = extractParameterAnnotation(parameter);
            SourceMethod sourceMethod = source.methods.get(annotation.annotationType());
            if (sourceMethod == null) {
                throw new IllegalStateException("The specified annotation was not found");
            }
            if (!sourceMethod.method.getReturnType().isAssignableFrom(parameter.getType())) {
                throw new IllegalStateException("The type of the argument != the type of the value for inject");
            }
            getters[i - start] = makeGetter(parameter, annotation, sourceMethod);
        }
        Lambda packed = ReflectUtil.packMethod(method, instance);
        if (start == 0) {
            return new NoRequestMethod(packed, getters);
        }
        return new RequestMethod(packed, getters);
    }
}
