package io.github.amayaframework.entities;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.controllers.HttpController;
import io.github.amayaframework.core.methods.Get;
import io.github.amayaframework.core.methods.Post;

import static io.github.amayaframework.core.contexts.Responses.ok;

public class Correct extends HttpController {
    @Get
    public HttpResponse get(HttpRequest request) {
        return ok("get");
    }

    @Get("/{id}")
    public HttpResponse getWithId(HttpRequest request) {
        return ok("getWithId");
    }

    @Post
    public HttpResponse post(HttpRequest request) {
        return ok("post");
    }

    @Post("/{id}")
    public HttpResponse postWithId(HttpRequest request) {
        return ok("postWithId");
    }
}
