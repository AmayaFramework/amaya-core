package io.github.amayaframework.options;

import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Skeletal implementation of {@link OptionSet}.
 * <br>
 * Contains the implementation of all basic methods except {@link OptionSet#set(String, Object)}
 * and {@link OptionSet#remove(String)}.
 */
public abstract class AbstractOptionSet implements OptionSet {
    /**
     * {@link Map} instance containing option values associated with key names.
     */
    protected final Map<String, Object> body;

    /**
     * Constructs instance of {@link OptionSet} with the given option map instance.
     *
     * @param body the specified {@link Map} instance
     */
    protected AbstractOptionSet(Map<String, Object> body) {
        this.body = body;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) body.get(key);
    }

    @Override
    public boolean asKey(String key) {
        if (!body.containsKey(key)) {
            return false;
        }
        return body.get(key) != Boolean.FALSE;
    }

    @Override
    public boolean asBool(String key) {
        var value = body.get(key);
        return value != null && value != Boolean.FALSE;
    }

    @Override
    public boolean contains(String key) {
        return body.containsKey(key);
    }

    @Override
    public boolean isEmpty() {
        return body.isEmpty();
    }

    @Override
    public Set<String> getKeys() {
        return Collections.unmodifiableSet(body.keySet());
    }

    @Override
    public Map<String, Object> asMap() {
        return Collections.unmodifiableMap(body);
    }

    @Override
    public void forEach(Runnable1<String> action) {
        Objects.requireNonNull(action);
        try {
            for (var key : body.keySet()) {
                action.run(key);
            }
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void forEach(Runnable2<String, Object> action) {
        Objects.requireNonNull(action);
        try {
            for (var entry : body.entrySet()) {
                action.run(entry.getKey(), entry.getValue());
            }
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Options " + body;
    }
}
