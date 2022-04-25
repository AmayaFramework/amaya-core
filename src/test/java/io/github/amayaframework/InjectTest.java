package io.github.amayaframework;

import io.github.amayaframework.core.config.AmayaConfig;
import io.github.amayaframework.core.contexts.AbstractHttpRequest;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.controllers.HttpControllerFactory;
import io.github.amayaframework.core.methods.Get;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routers.MethodRouter;
import io.github.amayaframework.core.wrapping.HttpCookie;
import io.github.amayaframework.core.wrapping.Path;
import io.github.amayaframework.core.wrapping.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.http.Cookie;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

public class InjectTest extends Assertions {
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
    public void testCorrect() throws Throwable {
        ByteArrayOutputStream outSpy = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(outSpy);
        Controller inject = FACTORY.create("", new Inject(stream));
        MethodRouter router = inject.getRouter();
        HttpRequest request = makeRequest();
        router.follow(HttpMethod.GET, "/a").getBody().execute(request);
        router.follow(HttpMethod.GET, "/b").getBody().execute(request);
        router.follow(HttpMethod.GET, "/c").getBody().execute(request);
        String check = outSpy.toString().replace("\r", "");
        assertEquals(check, "1\na_a\na\n");
        assertEquals("", "");
    }

    public static class Inject {
        private final PrintStream stream;

        public Inject(PrintStream stream) {
            this.stream = stream;
        }

        @Get("/{a}")
        public HttpResponse a(HttpRequest request, @Path("test_a") Integer a) {
            stream.println(a);
            return null;
        }

        @Get("/b")
        public HttpResponse b(HttpRequest request, @Query("test_a") String a) {
            stream.println(a);
            return null;
        }

        @Get("/c")
        public HttpResponse c(HttpRequest request, @HttpCookie("test_a") Cookie a) {
            stream.println(a.getName());
            return null;
        }
    }
}
