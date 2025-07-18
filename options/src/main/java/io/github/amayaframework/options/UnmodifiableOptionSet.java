package io.github.amayaframework.options;

import java.util.Collections;
import java.util.Map;

/**
 * Read-only implementation of {@link OptionSet}.
 */
public final class UnmodifiableOptionSet extends AbstractOptionSet {

    /**
     * Constructs {@link UnmodifiableOptionSet} instance with given {@link Map} instance.
     *
     * @param body the specified map, containing option entries
     */
    public UnmodifiableOptionSet(Map<String, Object> body) {
        super(Collections.unmodifiableMap(body));
    }

    @Override
    public Object set(String key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object remove(String key) {
        throw new UnsupportedOperationException();
    }
}
