package io.github.amayaframework.options;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * The open implementation of an {@link OptionSet} that allows you to add and remove any keys without restrictions.
 */
public class OpenOptionSet extends AbstractOptionSet {

    /**
     * Constructs {@link OpenOptionSet} instance with option map, provided by given supplier.
     *
     * @param supplier the specified option map supplier
     */
    public OpenOptionSet(Supplier<Map<String, Object>> supplier) {
        super(Objects.requireNonNull(supplier.get()));
    }

    /**
     * Constructs {@link OpenOptionSet} instance with {@link HashMap} as option map.
     */
    public OpenOptionSet() {
        super(new HashMap<>());
    }

    OpenOptionSet(Map<String, Object> map) {
        super(map);
    }

    @Override
    public Object set(String key, Object value) {
        Objects.requireNonNull(key);
        return body.put(key, value);
    }

    @Override
    public Object remove(String key) {
        return body.remove(key);
    }
}
