package io.github.amayaframework.core.wrapping;

import com.github.romanqed.util.Action;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.util.ParseUtil;
import io.github.amayaframework.filters.ContentFilter;

class MethodWrapper implements Action<HttpRequest, HttpResponse> {
    private final Argument[] arguments;
    private final Action<Object[], Object> body;

    MethodWrapper(Action<Object[], Object> body, Argument[] arguments) {
        this.body = body;
        this.arguments = arguments;
    }

    private Object[] makeParameters(HttpRequest request) {
        Object[] ret = new Object[arguments.length + 1];
        ret[0] = request;
        if (request == null || ret.length == 1) {
            return ret;
        }
        for (int i = 0; i < arguments.length; ++i) {
            Argument argument = arguments[i];
            ret[i + 1] = argument.filter.transform(request.view(argument.type), argument.name);
        }
        return ret;
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws Throwable {
        return (HttpResponse) body.execute(makeParameters(request));
    }

    protected static class Argument {
        private final ContentFilter filter;
        private final String type;
        private final String name;

        public Argument(String type, String name) {
            this.type = type;
            this.name = name;
            this.filter = ParseUtil.CONTENT_FILTERS.get(type);
        }
    }
}
