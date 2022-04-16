package io.github.amayaframework.entities;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.controllers.HttpController;
import io.github.amayaframework.core.methods.Get;
import io.github.amayaframework.core.wrapping.HttpCookie;
import io.github.amayaframework.core.wrapping.Path;
import io.github.amayaframework.core.wrapping.Query;

import javax.servlet.http.Cookie;
import java.io.PrintStream;

public class Inject extends HttpController {
    private final PrintStream stream;

    public Inject(PrintStream stream) {
        this.stream = stream;
    }

    @Get("/{a}")
    public HttpResponse a(HttpRequest request, @Path("a") Integer a) {
        stream.println(a);
        return null;
    }

    @Get("/b")
    public HttpResponse b(HttpRequest request, @Query("a") String a) {
        stream.println(a);
        return null;
    }

    @Get("/c")
    public HttpResponse c(HttpRequest request, @HttpCookie("a") Cookie a) {
        stream.println(a.getName());
        return null;
    }
}
