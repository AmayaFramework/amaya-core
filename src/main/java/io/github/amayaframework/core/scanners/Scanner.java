package io.github.amayaframework.core.scanners;

import java.util.Map;

public interface Scanner<K, V> {
    Map<K, V> find() throws Throwable;

    default Map<K, V> safetyFind() {
        try {
            return find();
        } catch (Throwable e) {
            throw new IllegalStateException("Exception when scanning", e);
        }
    }
}
