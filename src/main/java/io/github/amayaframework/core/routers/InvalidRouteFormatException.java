package io.github.amayaframework.core.routers;

public class InvalidRouteFormatException extends InvalidFormatException {
    public InvalidRouteFormatException() {
        super();
    }

    public InvalidRouteFormatException(String route) {
        super("Route " + route + " does not match the form /path/path1/path2");
    }
}
