package io.github.amayaframework.core.wrapping;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Function;

/**
 * A class describing the implementation of a packer
 * that does not support injecting values into the marked route arguments.
 */
public class BasePacker extends AbstractPacker {
    @Override
    public Function<HttpRequest, HttpResponse> pack(Object instance, Method method) {
        Objects.requireNonNull(instance);
        Objects.requireNonNull(method);
        method.setAccessible(true);
        checkParameters(method.getReturnType(), method.getParameters(), false);
        FastClass fastClass = FastClass.create(instance.getClass());
        FastMethod fastMethod = fastClass.getMethod(method);
        return request -> {
            try {
                return (HttpResponse) fastMethod.invoke(instance, new Object[]{request});
            } catch (Exception e) {
                return null;
            }
        };
    }
}
