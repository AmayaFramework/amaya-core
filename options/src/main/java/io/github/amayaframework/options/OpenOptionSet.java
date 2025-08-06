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
     * Constructs a new {@link OpenOptionSet} using the provided map supplier.
     * <p>
     * This constructor allows for deferred or customized map creation (e.g., concurrent map, pre-filled map, etc.).
     * The supplier must return a non-null map instance.
     * </p>
     *
     * @param supplier a supplier that provides the backing option map
     * @throws NullPointerException if the supplier returns {@code null}
     */
    public OpenOptionSet(Supplier<Map<String, Object>> supplier) {
        super(Objects.requireNonNull(supplier.get()));
    }

    /**
     * Constructs a new {@link OpenOptionSet} with an empty {@link HashMap} as the backing storage.
     */
    public OpenOptionSet() {
        super(new HashMap<>());
    }

    /**
     * Constructs a new {@link OpenOptionSet} using the given map as the backing storage.
     * Useful when external control over the storage map is required.
     *
     * @param map the map instance to use for storing option values
     * @throws NullPointerException if {@code map} is {@code null}
     */
    public OpenOptionSet(Map<String, Object> map) {
        super(Objects.requireNonNull(map));
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
