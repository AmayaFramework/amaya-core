package io.github.amayaframework.core.inject;

import com.github.romanqed.jeflect.lambdas.Lambda;
import com.github.romanqed.util.Action;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;

final class NoRequestMethod implements Action<HttpRequest, HttpResponse> {
    private final int length;
    private final Getter[] getters;
    private final Lambda body;

    NoRequestMethod(Lambda body, Getter[] getters) {
        this.body = body;
        this.getters = getters;
        this.length = getters.length;
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws Throwable {
        Object[] arguments = new Object[length];
        if (request == null) {
            return (HttpResponse) body.call(arguments);
        }
        for (int i = 0; i < length; ++i) {
            arguments[i] = getters[i].get(request);
        }
        return (HttpResponse) body.call(arguments);
    }
}
