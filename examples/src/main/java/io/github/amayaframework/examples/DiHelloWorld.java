package io.github.amayaframework.examples;

import com.github.romanqed.jct.CancelToken;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.http.HttpCode;
import io.github.amayaframework.http.HttpMethod;
import io.github.amayaframework.jetty.JettyServerFactory;
import io.github.amayaframework.service.AbstractService;
import io.github.amayaframework.service.ServiceCallback;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public final class DiHelloWorld {
    public static void main(String[] args) throws Throwable {
        var app = WebBuilders.create()
                .configureServices(cfg -> cfg.register(Counter.class, CounterService.class))
                .withServerFactory(new JettyServerFactory())
                .build();
        app.bind(8081);
        var counter = app.provider().get(Counter.class);
        app.configurer()
                .mapWhen(get("/count"), (HttpContext ctx) -> {
                    ctx.response().writer().println(counter.count());
                })
                .add((ctx, next) -> {
                    ctx.response().sendError(HttpCode.NOT_FOUND);
                });
        app.run();
    }

    static Predicate<HttpContext> get(String path) {
        return ctx -> {
            var req = ctx.request();
            return req.method() == HttpMethod.GET && path.equals(req.path());
        };
    }

    interface Counter {
        int count();
    }

    public static final class CounterService extends AbstractService implements Counter {
        private AtomicInteger counter;

        public int count() {
            return counter.getAndIncrement();
        }

        @Override
        protected void doStart(CancelToken token, ServiceCallback callback) {
            if (counter == null) {
                counter = new AtomicInteger();
            }
        }

        @Override
        protected void doStop(CancelToken token) {
            if (counter != null) {
                counter.set(0);
            }
        }

        @Override
        protected void doDispose() {
            counter = null;
        }
    }
}
