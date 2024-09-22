package io.github.amayaframework.options;

import com.github.romanqed.jfunc.Runnable1;

import java.util.*;
import java.util.function.Supplier;

/**
 *
 */
public class ClosedOptionSet extends AbstractOptionSet {
    private final Set<String> keys;

    /**
     * @param supplier
     * @param keys
     */
    public ClosedOptionSet(Supplier<Map<String, Object>> supplier, Set<String> keys) {
        super(supplier.get());
        this.keys = Collections.unmodifiableSet(keys);
    }

    /**
     * @param keys
     */
    public ClosedOptionSet(Set<String> keys) {
        super(new HashMap<>());
        this.keys = Collections.unmodifiableSet(keys);
    }

    @Override
    public Object set(String key, Object value) {
        Objects.requireNonNull(key);
        if (!keys.contains(key)) {
            throw new IllegalKeyException(key, keys);
        }
        return body.put(key, value);
    }

    @Override
    public Object remove(String key) {
        return body.remove(key);
    }

    @Override
    public Set<String> getKeys() {
        return keys;
    }

    @Override
    public void forEach(Runnable1<String> action) {
        try {
            for (var key : keys) {
                action.run(key);
            }
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
