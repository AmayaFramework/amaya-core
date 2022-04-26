package io.github.amayaframework.core.filters;

/**
 * An interface representing a filter designed to extract some
 * object from the passed object that has a string key.
 */
public interface ContentFilter extends Filter {

    /**
     * Processes the passed object and tries to transform it/extract some object
     * based on the passed string.
     *
     * @param source object to be transformed
     * @param name   a string containing information about the object to be extracted
     * @return transformed object/extracted object
     */
    Object transform(Object source, String name);
}
