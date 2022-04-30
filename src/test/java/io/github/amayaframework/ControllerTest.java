package io.github.amayaframework;

import io.github.amayaframework.core.config.AmayaConfig;
import io.github.amayaframework.core.contexts.AbstractHttpRequest;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.contexts.Responses;
import io.github.amayaframework.core.controllers.*;
import io.github.amayaframework.core.inject.Header;
import io.github.amayaframework.core.inject.HttpCookie;
import io.github.amayaframework.core.inject.Path;
import io.github.amayaframework.core.inject.Query;
import io.github.amayaframework.core.methods.Get;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.methods.Post;
import io.github.amayaframework.core.routers.BaseRouter;
import io.github.amayaframework.core.routers.MethodRouter;
import io.github.amayaframework.core.util.DuplicateException;
import io.github.amayaframework.core.util.InvalidRouteFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.http.Cookie;
import java.util.*;

import static io.github.amayaframework.core.contexts.Responses.ok;

public class ControllerTest extends Assertions {
    private static HttpControllerFactory FACTORY;
    private static Map<String, List<String>> LIST_MAP;
    private static Map<String, Object> PATH_PARAMETERS;
    private static Map<String, Cookie> COOKIE_MAP;

    @BeforeAll
    public static void config() {
        List<String> a = Arrays.asList("a_a", "b_b", "c_c");
        Map<String, List<String>> listMap = new HashMap<>();
        listMap.put("a", a);
        LIST_MAP = Collections.unmodifiableMap(listMap);
        Map<String, Object> path = new HashMap<>();
        path.put("a", 1);
        PATH_PARAMETERS = Collections.unmodifiableMap(path);
        Map<String, Cookie> cookieMap = new HashMap<>();
        cookieMap.put("a", new Cookie("a", "a_1"));
        COOKIE_MAP = Collections.unmodifiableMap(cookieMap);
        AmayaConfig config = new AmayaConfig();
        FACTORY = new HttpControllerFactory(config.getRouter(), config.getRoutePacker());
    }

    public HttpRequest makeRequest() {
        HttpRequest request = new AbstractHttpRequest() {
            @Override
            public List<String> getHeaders(String key) {
                return LIST_MAP.getOrDefault(key, Collections.emptyList());
            }
        };
        request.setCookies(COOKIE_MAP);
        request.setPathParameters(PATH_PARAMETERS);
        request.setQuery(LIST_MAP);
        return request;
    }

    @Test
    public void testInject() throws Throwable {
        Controller inject = FACTORY.create("", new Inject());
        MethodRouter router = inject.getRouter();
        HttpRequest request = makeRequest();
        HttpResponse a = router.follow(HttpMethod.GET, "/a").execute(request);
        HttpResponse b = router.follow(HttpMethod.GET, "/b").execute(request);
        HttpResponse c = router.follow(HttpMethod.GET, "/c").execute(request);
        HttpResponse d = router.follow(HttpMethod.GET, "/d").execute(request);
        assertAll(
                () -> assertEquals(1, a.getBody()),
                () -> assertEquals("a_a", b.getBody()),
                () -> assertEquals("a", c.getBody()),
                () -> assertEquals("a_a", d.getBody())
        );
    }

    @Test
    public void testRoute() throws Throwable {
        Controller correct = FACTORY.create("", new Correct());
        MethodRouter router = correct.getRouter();
        HttpResponse get = router.follow(HttpMethod.GET, "").execute(null);
        HttpResponse getWithId = router.follow(HttpMethod.GET, "/5").execute(null);
        HttpResponse post = router.follow(HttpMethod.POST, "").execute(null);
        HttpResponse postWithId = router.follow(HttpMethod.POST, "/5").execute(null);
        assertAll(
                () -> assertEquals("get", get.getBody()),
                () -> assertEquals("getWithId", getWithId.getBody()),
                () -> assertEquals("post", post.getBody()),
                () -> assertEquals("postWithId", postWithId.getBody())
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
        assertEquals(InvalidRouteFormatException.class, exceptionClass);
    }

    @Test
    public void testCustoms() throws Throwable {
        Controller controller = FACTORY.create("/", new Custom());
        HttpResponse get = controller.getRouter().follow(HttpMethod.GET, "/{a}").execute(null);
        HttpResponse packer = controller.getRouter().follow(HttpMethod.GET, "").execute(null);
        assertAll(
                () -> assertEquals("get", get.getBody()),
                () -> assertEquals("packer", packer.getBody())
        );
    }

    @UseRouter(BaseRouter.class)
    public static class Custom {
        @Get("/{a}")
        public HttpResponse customGet(HttpRequest request) {
            return Responses.ok("get");
        }

        @Get
        @UsePacker(BasePacker.class)
        public HttpResponse customPacker(HttpRequest request) {
            return Responses.ok("packer");
        }
    }

    public static class Inject {
        @Get("/{a}")
        public HttpResponse a(HttpRequest request, @Path Integer a) {
            return Responses.ok(a);
        }

        @Get("/b")
        public HttpResponse b(HttpRequest request, @Query String a) {
            return Responses.ok(a);
        }

        @Get("/c")
        public HttpResponse c(HttpRequest request, @HttpCookie Cookie a) {
            return Responses.ok(a.getName());
        }

        @Get("/d")
        public HttpResponse d(HttpRequest request, @Header String a) {
            return Responses.ok(a);
        }
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
