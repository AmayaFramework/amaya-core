package io.github.amayaframework.core.routes;

import java.util.regex.Pattern;

public interface Route {
    Pattern getPattern();

    String getRoute();

    boolean isRegexp();

    boolean matches(String route);
}
