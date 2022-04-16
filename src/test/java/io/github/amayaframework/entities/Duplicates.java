package io.github.amayaframework.entities;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.controllers.HttpController;
import io.github.amayaframework.core.methods.Get;
import io.github.amayaframework.core.methods.Post;

import static io.github.amayaframework.core.contexts.Responses.ok;

public class Duplicates extends HttpController {
    @Get
    @Post
    public HttpResponse get(HttpRequest request) {
        return ok();
    }

    @Post
    public HttpResponse post(HttpRequest request) {
        return ok();
    }
}