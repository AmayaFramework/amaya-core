package io.github.amayaframework.core.wrapping;

import com.github.romanqed.util.Action;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;

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
        // FIXME
//        return ReflectUtil.packAction(method, instance);
        return null;
    }
}
