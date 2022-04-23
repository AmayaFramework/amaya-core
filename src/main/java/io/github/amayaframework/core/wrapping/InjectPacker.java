package io.github.amayaframework.core.wrapping;

import com.github.romanqed.util.Action;
import io.github.amayaframework.core.ConfigProvider;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.util.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class describing the implementation of a packer
 * that supports injecting values into the marked route arguments.
 */
public class InjectPacker extends AbstractPacker {

    private MethodWrapper.Argument findParameterAnnotation(Parameter parameter, boolean useNativeName)
            throws InvocationTargetException, IllegalAccessException {
        Content found = null;
        String value = null;
        for (Annotation annotation : parameter.getDeclaredAnnotations()) {
            Content content = Content.fromAnnotation(annotation);
            if (content == null) {
                continue;
            }
            if (found != null) {
                throw new IllegalStateException("Content annotation duplicate!");
            }
            found = content;
            try {
                value = ReflectionUtil.extractAnnotationValue(annotation, String.class);
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
        return new MethodWrapper.Argument(found.getFilter(), value);
    }

    private MethodWrapper.Argument[] findAnnotatedParameters(Parameter[] parameters, boolean useNativeNames)
            throws InvocationTargetException, IllegalAccessException {
        List<MethodWrapper.Argument> ret = new ArrayList<>();
        for (int i = 1; i < parameters.length; ++i) {
            ret.add(findParameterAnnotation(parameters[i], useNativeNames));
        }
        return ret.toArray(new MethodWrapper.Argument[0]);
    }

    @Override
    public Action<HttpRequest, HttpResponse> pack(Object instance, Method method) throws Throwable {
        Objects.requireNonNull(instance);
        Objects.requireNonNull(method);
        method.setAccessible(true);
        Parameter[] parameters = method.getParameters();
        checkParameters(method.getReturnType(), parameters, true);
        boolean useNativeNames = ConfigProvider.getConfig().useNativeNames();
        MethodWrapper.Argument[] arguments = findAnnotatedParameters(parameters, useNativeNames);
//        Action<Object[], Object> toWrap = MetaLambdas.packAnyMethod(method, instance, arguments.length + 1);
//        return new MethodWrapper(toWrap, arguments);
        // FIXME
        return null;
    }
}
