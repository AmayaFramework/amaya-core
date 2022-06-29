package io.github.amayaframework.core.util;

/**
 * An interface describing a universal object whose mutability can be permanently terminated.
 */
public interface Completable {
    /**
     * Explicitly indicates that further data modification is not possible.
     */
    void complete();

    /**
     * @return completion status: true if completed, false if not
     */
    boolean isCompleted();
}
