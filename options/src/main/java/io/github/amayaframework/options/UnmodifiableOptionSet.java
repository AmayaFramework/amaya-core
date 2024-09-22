package io.github.amayaframework.options;

import java.util.Map;
import java.util.Objects;

/**
 * Read-only implementation of {@link OptionSet}.
 */
public class UnmodifiableOptionSet extends AbstractOptionSet {

    /**
     * Constructs {@link UnmodifiableOptionSet} instance with given {@link Map} instance.
     *
     * @param body the specified map, containing option entries
     */
    public UnmodifiableOptionSet(Map<String, Object> body) {
        super(Objects.requireNonNull(body));
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
