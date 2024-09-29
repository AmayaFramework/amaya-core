package io.github.amayaframework.server;

import com.github.romanqed.jconv.Task;
import io.github.amayaframework.context.HttpContext;

public interface HttpServer extends Server<HttpContext> {

    @Override
    Task<HttpContext> getHandler();

    @Override
    void setHandler(Task<HttpContext> handler);
}
