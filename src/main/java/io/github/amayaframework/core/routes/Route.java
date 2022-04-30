package io.github.amayaframework.core.routes;

import io.github.amayaframework.core.filters.Filter;
import io.github.amayaframework.core.util.Variable;

import java.util.List;
import java.util.regex.Pattern;

/**
 * An interface describing a universal route.
 */
public interface Route {
    /**
     * @return a pattern, if the route does not contain parameters, the pattern will contain the route itself
     */
    Pattern getPattern();

    /**
     * @return raw form of the route set by the user
     */
    String getRoute();

    /**
     * @return true if the route contains parameters/uses regular expressions for dynamic processing.
     */
    boolean isRegexp();

    /**
     * Compares a route with a specific path.
     *
     * @param path path to be compared
     * @return result of comparing, true - if matches, false - if no.
     */
    boolean matches(String path);

    /**
     * @return returns all parameters contained in the route.
     */
    List<Variable<String, Filter>> getParameters();
}
