package io.github.amayaframework.core.inject;

import com.github.romanqed.jeflect.ReflectUtil;
import com.github.romanqed.jeflect.lambdas.Lambda;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.scanners.Scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Parameter;
import java.util.Map;

final class Util {
    static final Class<HttpRequest> REQUEST = HttpRequest.class;
    static final Scanner<Class<?>, SourceClass> REQUEST_SCANNER = new RequestScanner();
    private static final Scanner<Class<?>, Class<? extends HttpRequest>> ANNOTATION_SCANNER = new AnnotationScanner();
    private static final Map<Class<?>, Class<? extends HttpRequest>> ANNOTATIONS = ANNOTATION_SCANNER.safetyFind();

    static Getter makeGetter(Parameter parameter, Annotation annotation, SourceMethod method) throws Throwable {
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

    static Annotation extractParameterAnnotation(Parameter parameter) {
        Annotation[] annotations = parameter.getDeclaredAnnotations();
        if (annotations.length == 0) {
            throw new IllegalStateException("No annotations found");
        }
        if (annotations.length > 1) {
            throw new IllegalStateException("More than one annotation is specified");
        }
        return annotations[0];
    }

    static Class<? extends HttpRequest> extractRequestClass(AnnotatedElement element) {
        Annotation[] annotations = element.getAnnotations();
        for (Annotation annotation : annotations) {
            Class<? extends HttpRequest> request = ANNOTATIONS.get(annotation.annotationType());
            if (request != null) {
                return request;
            }
        }
        return null;
    }
}
