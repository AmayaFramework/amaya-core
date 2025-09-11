package io.github.amayaframework.examples;

import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.jetty.JettyServerFactory;
import io.github.amayaframework.server.ServerOptions;
import io.github.amayaframework.web.WebOptions;

public final class SimpleHelloWorld {
    public static void main(String[] args) throws Throwable {
        var app = WebBuilders.create()
                .configureOptions(opts -> {
                    var serverOpts = opts.ensureGroup(WebOptions.SERVER_GROUP);
                    serverOpts.set(ServerOptions.SEND_SERVER, true);
                    serverOpts.set(ServerOptions.SEND_POWERED_BY, true);
                })
                .withServerFactory(new JettyServerFactory())
                .build();
        app.bind(8080);
        app.run(ctx -> {
            ctx.response().writer().println("Hello from amaya");
        });
    }
}
