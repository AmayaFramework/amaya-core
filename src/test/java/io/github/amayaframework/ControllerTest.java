package io.github.amayaframework;

import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.controllers.AbstractController;
import io.github.amayaframework.core.methods.Get;
import io.github.amayaframework.core.methods.HttpMethod;
import io.github.amayaframework.core.methods.Post;
import io.github.amayaframework.core.routers.DuplicateException;
import io.github.amayaframework.core.routers.InvalidRouteFormatException;
import io.github.amayaframework.core.routers.Router;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.github.amayaframework.core.contexts.Responses.ok;

public class ControllerTest extends Assertions {
    @Test
    public void testCorrect() {
        Correct correct = new Correct();
        Router router = correct.router();
        HttpResponse get = router.follow(HttpMethod.GET, "").apply(null);
        HttpResponse getWithId = router.follow(HttpMethod.GET, "/5").apply(null);
        HttpResponse post = router.follow(HttpMethod.POST, "").apply(null);
        HttpResponse postWithId = router.follow(HttpMethod.POST, "/5").apply(null);
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

class Correct extends AbstractController {
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

class Duplicates extends AbstractController {
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

class BrokenPath extends AbstractController {
    @Get("//")
    public HttpResponse get(HttpRequest request) {
        return ok();
    }
}
