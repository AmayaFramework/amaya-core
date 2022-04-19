package io.github.amayaframework;

import io.github.amayaframework.core.ConfigProvider;
import io.github.amayaframework.core.config.AmayaConfig;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.routers.MethodRouter;
import io.github.amayaframework.core.util.DuplicateException;
import io.github.amayaframework.core.util.InvalidRouteFormatException;
import io.github.amayaframework.core.wrapping.BasePacker;
import io.github.amayaframework.entities.BrokenPath;
import io.github.amayaframework.entities.Correct;
import io.github.amayaframework.entities.Duplicates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RouteTest extends Assertions {
    @BeforeAll
    public static void config() {
        AmayaConfig config = ConfigProvider.getAmayaConfig();
        config.setUseNativeNames(false);
        config.setRoutePacker(new BasePacker());
    }

    @Test
    public void testCorrect() throws Throwable {
        Correct correct = new Correct();
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
        assertThrows(DuplicateException.class, Duplicates::new);
    }

    @Test
    public void testBrokenPath() {
        Class<?> exceptionClass = Exception.class;
        try {
            new BrokenPath();
        } catch (IllegalStateException e) {
            exceptionClass = e.getCause().getClass();
        }
        assertEquals(exceptionClass, InvalidRouteFormatException.class);
    }
}
