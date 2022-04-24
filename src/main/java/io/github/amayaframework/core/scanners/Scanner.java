package io.github.amayaframework.core.scanners;

public interface Scanner<E> {
    E find() throws Throwable;

    default E safetyFind() {
        try {
            return find();
        } catch (Throwable e) {
            throw new IllegalStateException("Exception when scanning annotated", e);
        }
    }
}
