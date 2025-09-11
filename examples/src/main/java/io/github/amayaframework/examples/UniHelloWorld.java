package io.github.amayaframework.examples;

import com.github.romanqed.jconv.Task;
import com.github.romanqed.jsync.Futures;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.jetty.JettyServerFactory;

import java.util.concurrent.CompletableFuture;

public final class UniHelloWorld {
    public static void main(String[] args) throws Throwable {
        var app = WebBuilders.create()
//                .configureOptions(opts -> {
//                    opts.set(WebOptions.SERVER_GROUP, ServerOptions.PREFER_ASYNC, false);
//                })
                .withServerFactory(new JettyServerFactory())
                .build();
        app.bind(8080);
        app.run(new UniHelloTask());
    }

    static final class UniHelloTask implements Task<HttpContext> {

        @Override
        public void run(HttpContext ctx) throws Throwable {
            ctx.response().writer().println("Hello from sync, JVM = " + Runtime.version());
        }

        @Override
        public CompletableFuture<Void> runAsync(HttpContext ctx) {
            return Futures.run(() -> ctx.response().writer().println("Hello from async, JVM = " + Runtime.version()));
        }

        @Override
        public boolean isSync() {
            return true;
        }

        @Override
        public boolean isAsync() {
            return true;
        }

        @Override
        public boolean isUni() {
            return true;
        }
    }
}
