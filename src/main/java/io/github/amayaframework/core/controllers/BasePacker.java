package io.github.amayaframework.core.controllers;

import com.github.romanqed.util.Action;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.util.InvalidFormatException;
import io.github.amayaframework.core.util.ReflectUtil;

import java.lang.reflect.Method;

/**
 * A class describing the implementation of a packer
 * that does not support injecting values into the marked getRoute arguments.
 */
public final class BasePacker implements Packer {
    private static final Class<HttpRequest> REQUEST = HttpRequest.class;
    private static final Class<HttpResponse> RESPONSE = HttpResponse.class;

    @Override
    public Action<HttpRequest, HttpResponse> pack(Object instance, Method method) throws Throwable {
        Class<?> returnType = method.getReturnType();
        Class<?> argumentType = method.getParameterTypes()[0];
        if (!(RESPONSE.isAssignableFrom(returnType) && REQUEST.isAssignableFrom(argumentType))) {
            throw new InvalidFormatException("Invalid route method: " + method);
        }
        return ReflectUtil.packAction(method, instance);
    }
}
