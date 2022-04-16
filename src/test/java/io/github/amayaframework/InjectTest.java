package io.github.amayaframework;

import io.github.amayaframework.core.config.AmayaConfig;
import io.github.amayaframework.core.config.ConfigProvider;
import io.github.amayaframework.core.contexts.AbstractHttpRequest;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routers.MethodRouter;
import io.github.amayaframework.core.wrapping.InjectPacker;
import io.github.amayaframework.entities.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.http.Cookie;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

public class InjectTest extends Assertions {
    private static final Map<String, List<String>> LIST_MAP;
    private static final Map<String, Object> PATH_PARAMETERS;
    private static final Map<String, Cookie> COOKIE_MAP;

    static {
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
    }

    @BeforeAll
    public static void config() {
        AmayaConfig config = ConfigProvider.getConfig();
        config.setUseNativeNames(false);
        config.setRoutePacker(new InjectPacker());
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
        Inject inject = new Inject(stream);
        MethodRouter router = inject.getRouter();
        HttpRequest request = makeRequest();
        router.follow(HttpMethod.GET, "/a").getBody().execute(request);
        router.follow(HttpMethod.GET, "/b").getBody().execute(request);
        router.follow(HttpMethod.GET, "/c").getBody().execute(request);
        String check = outSpy.toString().replace("\r", "");
        assertEquals(check, "1\na_a\na\n");
    }
}
