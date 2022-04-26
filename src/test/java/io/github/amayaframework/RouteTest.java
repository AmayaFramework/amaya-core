package io.github.amayaframework;

import io.github.amayaframework.core.routes.HttpRoute;
import io.github.amayaframework.core.routes.Route;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RouteTest extends Assertions {
    @Test
    public void compileRouteTest() {
        Route plain = HttpRoute.compile("/a");
        Route regexp = HttpRoute.compile("/{a}/{b}");
        assertAll(
                () -> assertFalse(plain.isRegexp()),
                () -> assertEquals("/a", plain.getRoute()),
                () -> assertTrue(regexp.isRegexp()),
                () -> assertEquals("/{a}/{b}", regexp.getRoute())
        );
    }
}
