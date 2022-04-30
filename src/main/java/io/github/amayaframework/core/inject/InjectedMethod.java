package io.github.amayaframework.core.inject;

import com.github.romanqed.jeflect.Lambda;
import com.github.romanqed.util.Action;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;

class InjectedMethod implements Action<HttpRequest, HttpResponse> {
    private final Getter[] getters;
    private final Lambda body;

    InjectedMethod(Lambda body, Getter[] getters) {
        this.body = body;
        this.getters = getters;
    }

    private Object[] makeParameters(HttpRequest request) throws Throwable {
        Object[] ret = new Object[getters.length + 1];
        ret[0] = request;
        if (request == null || ret.length == 1) {
            return ret;
        }
        for (int i = 0; i < getters.length; ++i) {
            ret[i + 1] = getters[i].get(request);
        }
        return ret;
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws Throwable {
        return (HttpResponse) body.call(makeParameters(request));
    }
}
