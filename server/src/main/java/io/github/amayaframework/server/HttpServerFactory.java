package io.github.amayaframework.server;

import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.options.OptionSet;

public interface HttpServerFactory extends ServerFactory<HttpContext> {

    @Override
    HttpServer create(OptionSet set);

    @Override
    HttpServer create();
}
