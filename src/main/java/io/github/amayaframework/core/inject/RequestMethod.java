package io.github.amayaframework.core.inject;

import com.github.romanqed.jeflect.lambdas.Lambda;
import com.github.romanqed.util.Action;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;

final class RequestMethod implements Action<HttpRequest, HttpResponse> {
    private final int length;
    private final Getter[] getters;
    private final Lambda body;

    RequestMethod(Lambda body, Getter[] getters) {
        this.body = body;
        this.getters = getters;
        this.length = getters.length;
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws Throwable {
        Object[] arguments = new Object[length + 1];
        arguments[0] = request;
        if (request == null || length == 0) {
            return (HttpResponse) body.call(arguments);
        }
        for (int i = 0; i < length; ++i) {
            arguments[i + 1] = getters[i].get(request);
        }
        return (HttpResponse) body.call(arguments);
    }
}
