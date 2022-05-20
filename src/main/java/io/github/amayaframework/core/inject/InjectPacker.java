package io.github.amayaframework.core.inject;

import com.github.romanqed.jeflect.ReflectUtil;
import com.github.romanqed.jeflect.lambdas.Lambda;
import com.github.romanqed.util.Action;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.controllers.Packer;
import io.github.amayaframework.core.util.InvalidFormatException;
import org.atteo.classindex.ClassIndex;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A class describing a packager that supports injection into method arguments.
 */
public class InjectPacker implements Packer {
    private static final Class<HttpRequest> REQUEST = HttpRequest.class;
    private static final Class<HttpResponse> RESPONSE = HttpResponse.class;
    private static final String MESSAGE = "Invalid route method: ";
    private static final Map<Class<?>, SourceClass> REQUESTS = findRequests();

    private static Map<Class<?>, SourceClass> findRequests() {
        Iterable<Class<?>> found = ClassIndex.getAnnotated(SourceRequest.class);
        Map<Class<?>, SourceClass> ret = new HashMap<>();
        ret.put(HttpRequest.class, SourceClass.create(HttpRequest.class));
        for (Class<?> clazz : found) {
            if (!REQUEST.isAssignableFrom(clazz)) {
                throw new IllegalStateException("The found class is not an HttpRequest implementation");
            }
            SourceRequest annotation = clazz.getAnnotation(SourceRequest.class);
            ret.put(annotation.value(), SourceClass.create(clazz));
        }
        return Collections.unmodifiableMap(ret);
    }

    private static Annotation extractAnnotation(Parameter parameter) {
        Annotation[] annotations = parameter.getDeclaredAnnotations();
        if (annotations.length == 0) {
            throw new IllegalStateException("No annotations found");
        }
        if (annotations.length > 1) {
            throw new IllegalStateException("More than one annotation is specified");
        }
        return annotations[0];
    }

    private static Getter makeGetter(Parameter parameter, Annotation annotation, SourceMethod method) throws Throwable {
        Lambda packed = ReflectUtil.packMethod(method.method);
        String[] parameters = method.parameters;
        if (parameters.length == 0) {
            return packed::call;
        }
        Object[] arguments = new Object[parameters.length];
        for (int i = 0; i < arguments.length; ++i) {
            Object argument = ReflectUtil.extractAnnotationValue(annotation, parameters[i]);
            if (argument instanceof String && ((String) argument).isEmpty() && parameter.isNamePresent()) {
                argument = parameter.getName();
            }
            arguments[i] = argument;
        }
        return source -> packed.call(source, arguments);
    }

    @Override
    public Action<HttpRequest, HttpResponse> pack(Object instance, Method method) throws Throwable {
        if (!RESPONSE.isAssignableFrom(method.getReturnType())) {
            throw new InvalidFormatException(MESSAGE + method);
        }
        Parameter[] parameters = method.getParameters();
        Class<?> requestType = parameters[0].getType();
        if (!REQUEST.isAssignableFrom(requestType)) {
            throw new InvalidFormatException(MESSAGE + method);
        }
        SourceClass source = REQUESTS.get(requestType);
        if (source == null) {
            throw new IllegalStateException("The specified HttpRequest implementation is not supported");
        }
        Getter[] getters = new Getter[parameters.length - 1];
        for (int i = 1; i < parameters.length; ++i) {
            Parameter parameter = parameters[i];
            Annotation annotation = extractAnnotation(parameter);
            SourceMethod sourceMethod = source.methods.get(annotation.annotationType());
            if (sourceMethod == null) {
                throw new IllegalStateException("The specified annotation was not found");
            }
            if (!sourceMethod.method.getReturnType().isAssignableFrom(parameter.getType())) {
                throw new IllegalStateException("The type of the argument != the type of the value for inject");
            }
            getters[i - 1] = makeGetter(parameter, annotation, sourceMethod);
        }
        Lambda packed = ReflectUtil.packMethod(method, instance);
        return new InjectedMethod(packed, getters);
    }
}
