package io.github.amayaframework.examples;

import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.jetty.JettyServerFactory;
import io.github.amayaframework.options.Options;
import io.github.amayaframework.server.ServerOptions;
import io.github.amayaframework.web.WebOptions;

public final class SimpleHelloWorld {
    public static void main(String[] args) throws Throwable {
        var opts = Options.createGrouped();
        var serverOpts = opts.ensureGroup(WebOptions.SERVER_GROUP);
        serverOpts.set(ServerOptions.SEND_SERVER, true);
        serverOpts.set(ServerOptions.SEND_POWERED_BY, true);
        var app = WebBuilders.create(opts)
                .withServerFactory(new JettyServerFactory())
                .build();
        app.configurer().add((ctx, next) -> {
            ctx.response().writer().println("Hello from amaya");
        });
        app.bind(8080);
        app.run();
    }
}
