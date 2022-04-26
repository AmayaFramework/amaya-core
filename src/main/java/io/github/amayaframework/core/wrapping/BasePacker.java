package io.github.amayaframework.core.wrapping;

import com.github.romanqed.util.Action;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.util.ReflectUtil;

import java.lang.reflect.Method;

/**
 * A class describing the implementation of a packer
 * that does not support injecting values into the marked getRoute arguments.
 */
public class BasePacker extends AbstractPacker {
    public BasePacker() {
        super(false);
    }

    @Override
    public Action<HttpRequest, HttpResponse> pack(Object instance, Method method) throws Throwable {
        checkParameters(method.getReturnType(), method.getParameters(), false);
        return ReflectUtil.packAction(method, instance);
    }
}
