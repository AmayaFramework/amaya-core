package io.github.amayaframework;

import io.github.amayaframework.core.methods.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class HttpMethodTest extends Assertions {
    @Test
    public void testByName() {
        assertAll(
                () -> assertEquals(HttpMethod.GET, HttpMethod.fromName("GET")),
                () -> assertEquals(HttpMethod.HEAD, HttpMethod.fromName("HEAD")),
                () -> assertEquals(HttpMethod.OPTIONS, HttpMethod.fromName("OPTIONS")),
                () -> assertEquals(HttpMethod.DELETE, HttpMethod.fromName("DELETE")),
                () -> assertEquals(HttpMethod.PUT, HttpMethod.fromName("PUT")),
                () -> assertEquals(HttpMethod.PATCH, HttpMethod.fromName("PATCH")),
                () -> assertEquals(HttpMethod.POST, HttpMethod.fromName("POST"))
        );
    }

    @Test
    @Get
    @Head
    @Post
    @Put
    @Patch
    @Delete
    @Options
    public void testByAnnotation() throws NoSuchMethodException {
        Method method = HttpMethodTest.class.getDeclaredMethod("testByAnnotation");
        assertAll(
                () -> assertEquals(HttpMethod.GET, HttpMethod.fromAnnotation(method.getAnnotation(Get.class))),
                () -> assertEquals(HttpMethod.HEAD, HttpMethod.fromAnnotation(method.getAnnotation(Head.class))),
                () -> assertEquals(HttpMethod.OPTIONS, HttpMethod.fromAnnotation(method.getAnnotation(Options.class))),
                () -> assertEquals(HttpMethod.DELETE, HttpMethod.fromAnnotation(method.getAnnotation(Delete.class))),
                () -> assertEquals(HttpMethod.PUT, HttpMethod.fromAnnotation(method.getAnnotation(Put.class))),
                () -> assertEquals(HttpMethod.PATCH, HttpMethod.fromAnnotation(method.getAnnotation(Patch.class))),
                () -> assertEquals(HttpMethod.POST, HttpMethod.fromAnnotation(method.getAnnotation(Post.class)))
        );
    }
}
