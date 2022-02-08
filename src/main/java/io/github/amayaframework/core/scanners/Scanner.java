package io.github.amayaframework.core.scanners;

public interface Scanner<E> {
    E find() throws Exception;

    default E safetyFind() {
        try {
            return find();
        } catch (Exception e) {
            throw new IllegalStateException("Exception when scanning annotated", e);
        }
    }
}
