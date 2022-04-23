package io.github.amayaframework.core.wrapping;

import com.github.romanqed.util.Action;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.util.ReflectUtil;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * A class describing the implementation of a packer
 * that does not support injecting values into the marked route arguments.
 */
public class BasePacker extends AbstractPacker {
    @Override
    public Action<HttpRequest, HttpResponse> pack(Object instance, Method method) throws Throwable {
        Objects.requireNonNull(instance);
        Objects.requireNonNull(method);
        method.setAccessible(true);
        checkParameters(method.getReturnType(), method.getParameters(), false);
        return ReflectUtil.packAction(method, instance);
    }
}
