package io.github.amayaframework.entities;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.controllers.HttpController;
import io.github.amayaframework.core.methods.Get;

import static io.github.amayaframework.core.contexts.Responses.ok;

public class BrokenPath extends HttpController {
    @Get("//")
    public HttpResponse get(HttpRequest request) {
        return ok();
    }
}

