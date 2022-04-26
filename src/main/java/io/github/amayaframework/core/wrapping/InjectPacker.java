package io.github.amayaframework.core.wrapping;

import com.github.romanqed.jeflect.Lambda;
import com.github.romanqed.jeflect.ReflectUtil;
import com.github.romanqed.util.Action;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * A class describing the implementation of a packer
 * that supports injecting values into the marked getRoute arguments.
 */
public class InjectPacker extends AbstractPacker {

    public InjectPacker(boolean useNativeNames) {
        super(useNativeNames);
    }

    public InjectPacker() {
        super(true);
    }

    private MethodWrapper.Argument findParameterAnnotation(Parameter parameter, boolean useNativeName) {
        String found = null;
        String value = null;
        for (Annotation annotation : parameter.getDeclaredAnnotations()) {
            String filter = Content.fromAnnotation(annotation);
            if (filter == null) {
                continue;
            }
            if (found != null) {
                throw new IllegalStateException("Content annotation duplicate!");
            }
            found = filter;
            try {
                value = ReflectUtil.extractAnnotationValue(annotation, String.class);
            } catch (NoSuchMethodException e) {
                value = parameter.getName();
            }
            if (useNativeName) {
                value = parameter.getName();
            }
        }
        if (found == null) {
            throw new IllegalStateException("Not annotated parameter!");
        }
        return new MethodWrapper.Argument(found, value);
    }

    private MethodWrapper.Argument[] findAnnotatedParameters(Parameter[] parameters, boolean useNativeNames) {
        List<MethodWrapper.Argument> ret = new ArrayList<>();
        for (int i = 1; i < parameters.length; ++i) {
            ret.add(findParameterAnnotation(parameters[i], useNativeNames));
        }
        return ret.toArray(new MethodWrapper.Argument[0]);
    }

    @Override
    public Action<HttpRequest, HttpResponse> pack(Object instance, Method method) throws Throwable {
        Parameter[] parameters = method.getParameters();
        checkParameters(method.getReturnType(), parameters, true);
        MethodWrapper.Argument[] arguments = findAnnotatedParameters(parameters, useNativeNames);
        Lambda toWrap = ReflectUtil.packMethod(method, instance);
        return new MethodWrapper(toWrap, arguments);
    }
}
