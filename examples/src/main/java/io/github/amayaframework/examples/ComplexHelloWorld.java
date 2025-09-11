package io.github.amayaframework.examples;

import com.github.romanqed.jct.CancelToken;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.http.HttpCode;
import io.github.amayaframework.http.HttpMethod;
import io.github.amayaframework.http.HttpVersion;
import io.github.amayaframework.jetty.JettyServerFactory;
import io.github.amayaframework.service.AbstractService;
import io.github.amayaframework.service.ServiceCallback;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public final class ComplexHelloWorld {
    public static void main(String[] args) throws Throwable {
        var app = WebBuilders.create()
                .withServerFactory(new JettyServerFactory())
                .build();
        app.serverConfig().httpVersion(HttpVersion.HTTP_2_0);
        app.bind(8081, HttpVersion.HTTP_1_1);
        app.bind(8082);
        var counter = new CounterService();
        app.manager().add(counter);
        app.configurer()
                .mapWhen(get("/echo"), (HttpContext ctx) -> {
                    ctx.response().writer().println(ctx.request().<String>queryParam("msg"));
                })
                .mapWhen(get("/random"), (HttpContext ctx) -> {
                    ctx.response().writer().println(ThreadLocalRandom.current().nextInt());
                })
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

    static final class CounterService extends AbstractService {
        private AtomicInteger counter;
        private ServiceCallback callback;

        public int count() {
            var ret = counter.getAndIncrement();
            if (ret >= 5 && callback != null) {
                callback.fail(new IllegalStateException("Count >= 5"));
            }
            return ret;
        }

        @Override
        protected void doStart(CancelToken token, ServiceCallback callback) {
            if (counter == null) {
                counter = new AtomicInteger();
            }
            this.callback = callback;
        }

        @Override
        protected void doStop(CancelToken token) {
            if (counter != null) {
                counter.set(0);
            }
            callback = null;
        }

        @Override
        protected void doDispose() {
            counter = null;
            callback = null;
        }
    }
}
