package io.github.amayaframework.options;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 *
 */
public class OpenOptionSet extends AbstractOptionSet {

    /**
     * @param supplier
     */
    public OpenOptionSet(Supplier<Map<String, Object>> supplier) {
        super(supplier.get());
    }

    /**
     *
     */
    public OpenOptionSet() {
        super(new HashMap<>());
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
