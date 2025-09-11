package io.github.amayaframework.examples;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jsync.Futures;
import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.jetty.JettyServerFactory;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public final class AsyncHelloWorld {
    public static void main(String[] args) throws Throwable {
        var app = WebBuilders.create()
                .withServerFactory(new JettyServerFactory())
                .build();
        app.bind(8080);
        app.configurer()
                .add((ctx, next) -> {
                    return next.runAsync(ctx).exceptionally(t -> {
                        if (t != null) {
                            try {
                                ctx.response().writer().println("Sorry, some problems");
                            } catch (IOException e) {
                                System.out.println("Rlly big problems");
                                Exceptions.throwAny(e);
                            }
                        }
                        return null;
                    });
                })
                .add((ctx, next) -> {
                    return Futures.run(() -> {
                        if (ThreadLocalRandom.current().nextBoolean()) {
                            throw new IllegalStateException("Unlucky");
                        }
                        ctx.response().writer().println("Hello, world!");
                    });
                });
        app.run();
    }
}
