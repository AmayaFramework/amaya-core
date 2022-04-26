package io.github.amayaframework.core.filters;

/**
 * An interface representing a filter for string conversion.
 */
public interface StringFilter extends Filter {
    /**
     * Performs some operations with a string and returns an
     * object created based on it, or a directly modified string.
     *
     * @param source the string to be transformed
     * @return the object obtained as a result of string transformation
     */
    Object transform(String source);
}
