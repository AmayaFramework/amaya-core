package io.github.amayaframework.core.filters;

/**
 * An interface representing a universal filter, which is the base for all
 * future implementations and is used to inherit child interfaces from it.
 */
public interface Filter {

    /**
     * Performs some operations on the object and returns its converted version/copy.
     * By default, it simply returns the passed object unchanged.
     *
     * @param source the object to be transformed
     * @return transformed object
     */
    default Object transform(Object source) {
        return source;
    }
}
