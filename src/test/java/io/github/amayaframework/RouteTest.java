package io.github.amayaframework;

import io.github.amayaframework.core.config.AmayaConfig;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.controllers.HttpControllerFactory;
import io.github.amayaframework.core.methods.Get;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.methods.Post;
import io.github.amayaframework.core.routers.MethodRouter;
import io.github.amayaframework.core.util.DuplicateException;
import io.github.amayaframework.core.util.InvalidRouteFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.github.amayaframework.core.contexts.Responses.ok;

public class RouteTest extends Assertions {
    private static HttpControllerFactory FACTORY;
    @BeforeAll
    public static void config() {
        AmayaConfig config = new AmayaConfig();
        FACTORY = new HttpControllerFactory(config.getRouter(), config.getRoutePacker());
    }

    @Test
    public void testCorrect() throws Throwable {
        Controller correct = FACTORY.create("", new Correct());
        MethodRouter router = correct.getRouter();
        HttpResponse get = router.follow(HttpMethod.GET, "").getBody().execute(null);
        HttpResponse getWithId = router.follow(HttpMethod.GET, "/5").getBody().execute(null);
        HttpResponse post = router.follow(HttpMethod.POST, "").getBody().execute(null);
        HttpResponse postWithId = router.follow(HttpMethod.POST, "/5").getBody().execute(null);
        assertAll(
                () -> assertEquals(get.getBody(), "get"),
                () -> assertEquals(getWithId.getBody(), "getWithId"),
                () -> assertEquals(post.getBody(), "post"),
                () -> assertEquals(postWithId.getBody(), "postWithId")
        );
    }

    @Test
    public void testDuplicates() {
        assertThrows(DuplicateException.class, () -> FACTORY.create("", new Duplicates()));
    }

    @Test
    public void testBrokenPath() {
        Class<?> exceptionClass = Exception.class;
        try {
            FACTORY.create("", new BrokenPath());
        } catch (Throwable e) {
            exceptionClass = e.getClass();
        }
        assertEquals(exceptionClass, InvalidRouteFormatException.class);
    }

    public static class BrokenPath {
        @Get("//")
        public HttpResponse get(HttpRequest request) {
            return ok();
        }
    }

    public static class Correct {
        @Get
        public HttpResponse get(HttpRequest request) {
            return ok("get");
        }

        @Get("/{id}")
        public HttpResponse getWithId(HttpRequest request) {
            return ok("getWithId");
        }

        @Post
        public HttpResponse post(HttpRequest request) {
            return ok("post");
        }

        @Post("/{id}")
        public HttpResponse postWithId(HttpRequest request) {
            return ok("postWithId");
        }
    }

    public static class Duplicates {
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
}
