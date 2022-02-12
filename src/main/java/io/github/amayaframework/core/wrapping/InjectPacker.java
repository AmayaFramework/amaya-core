package io.github.amayaframework.core.wrapping;

import com.github.romanqed.jutils.util.Action;
import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.Paranamer;
import io.github.amayaframework.core.config.ConfigProvider;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.util.ReflectUtils;
import net.sf.cglib.reflect.FastClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A class describing the implementation of a packer
 * that supports injecting values into the marked route arguments.
 */
public class InjectPacker extends AbstractPacker {
    private MethodWrapper.Argument findParameterAnnotation(Parameter parameter, String nativeName)
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
                value = ReflectUtils.extractAnnotationValue(annotation, String.class);
            } catch (NoSuchElementException e) {
                value = nativeName;
            }
            if (nativeName != null) {
                value = nativeName;
            }
        }
        if (found == null) {
            throw new IllegalStateException("Not annotated parameter!");
        }
        return new MethodWrapper.Argument(found.getFilter(), value);
    }

    private MethodWrapper.Argument[] findAnnotatedParameters(Parameter[] parameters, String[] nativeNames)
            throws InvocationTargetException, IllegalAccessException {
        List<MethodWrapper.Argument> ret = new ArrayList<>();
        for (int i = 1; i < parameters.length; ++i) {
            String nativeName = null;
            if (nativeNames != null) {
                nativeName = nativeNames[i];
            }
            ret.add(findParameterAnnotation(parameters[i], nativeName));
        }
        return ret.toArray(new MethodWrapper.Argument[0]);
    }

    @Override
    public Action<HttpRequest, HttpResponse> pack(Object instance, Method method)
            throws InvocationTargetException, IllegalAccessException {
        Objects.requireNonNull(instance);
        Objects.requireNonNull(method);
        method.setAccessible(true);
        Parameter[] parameters = method.getParameters();
        checkParameters(method.getReturnType(), parameters, true);
        String[] nativeNames = null;
        if (ConfigProvider.getConfig().useNativeNames()) {
            Paranamer paranamer = new BytecodeReadingParanamer();
            nativeNames = paranamer.lookupParameterNames(method);
        }
        MethodWrapper.Argument[] arguments = findAnnotatedParameters(parameters, nativeNames);
        FastClass fastClass = FastClass.create(instance.getClass());
        return new MethodWrapper(instance, fastClass.getMethod(method), arguments);
    }
}
