package io.github.amayaframework.options;

import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;

import java.util.Map;
import java.util.Set;

/**
 * An interface describing an abstract set of options.
 * <br>
 * It includes the basic operations: get, set, remove, contains.
 */
public interface OptionSet {

    /**
     * Gets the value associated with given key.
     *
     * @param key the specified key
     * @param <T> the type of requested value
     * @return the value associated with key or null
     */
    <T> T get(String key);

    /**
     * Gets the value associated with given key. If key not found, returns given default value.
     *
     * @param key the specified key
     * @param def the specified default value
     * @param <T> the type of requested value
     * @return the value associated with key or default value
     */
    default <T> T get(String key, T def) {
        T t;
        return ((t = get(key)) != null) || contains(key) ? t : def;
    }

    /**
     * Gets the value associated with given key.
     *
     * @param key the specified key
     * @param <T> the type of requested value
     * @return the value associated with key or null
     */
    default <T> T get(Key<T> key) {
        return get(key.getKey());
    }

    /**
     * Gets the value associated with given key. If key not found, returns given default value.
     *
     * @param key the specified key
     * @param def the specified default value
     * @param <T> the type of requested value
     * @return the value associated with key or default value
     */
    default <T> T get(Key<T> key, T def) {
        var k = key.getKey();
        T t;
        return ((t = get(k)) != null) || contains(k) ? t : def;
    }

    /**
     * Treats option entry only as a key entry.
     * <br>
     * The following heuristics are used:
     * <ul>
     *     <li>if value not found, then result is false;</li>
     *     <li>if value is 'true', then result is true;</li>
     *     <li>if value is 'false', then result if false;</li>
     *     <li>otherwise, if value is null or any other reference, then result is true.</li>
     * </ul>
     *
     * @param key the specified key
     * @return false, if value is 'false', true otherwise
     */
    boolean asKey(String key);

    /**
     * Treats option entry only as a key entry.
     * <br>
     * The following heuristics are used:
     * <ul>
     *     <li>if value not found, then result is false;</li>
     *     <li>if value is 'true', then result is true;</li>
     *     <li>if value is 'false', then result if false;</li>
     *     <li>otherwise, if value is null or any other reference, then result is true.</li>
     * </ul>
     *
     * @param key the specified key
     * @return false, if value is 'false', true otherwise
     */
    default boolean asKey(Key<?> key) {
        return asKey(key.getKey());
    }

    /**
     * Treats option entry as a boolean value.
     * <br>
     * The following heuristics are used:
     * <ul>
     *     <li>if value is 'true', then result is true;</li>
     *     <li>if value is 'false', then result is false;</li>
     *     <li>if value is null, then result is false;</li>
     *     <li>otherwise result is true.</li>
     * </ul>
     *
     * @param key the specified key
     * @return false, if value is 'false' or null, true otherwise
     */
    boolean asBool(String key);

    /**
     * Treats option entry as a boolean value.
     * <br>
     * The following heuristics are used:
     * <ul>
     *     <li>if value is 'true', then result is true;</li>
     *     <li>if value is 'false', then result is false;</li>
     *     <li>if value is null, then result is false;</li>
     *     <li>otherwise result is true.</li>
     * </ul>
     *
     * @param key the specified key
     * @return false, if value is 'false' or null, true otherwise
     */
    default boolean asBool(Key<?> key) {
        return asBool(key.getKey());
    }

    /**
     * Checks if set contains the specified key.
     *
     * @param key the specified key
     * @return true, if key exists, false otherwise
     */
    boolean contains(String key);

    /**
     * Checks if set contains the specified key.
     *
     * @param key the specified key
     * @return true, if key exists, false otherwise
     */
    default boolean contains(Key<?> key) {
        return contains(key.getKey());
    }

    /**
     * Sets given value to the specified key.
     *
     * @param key   the specified key, must be non-null
     * @param value the specified value
     * @return previous associated value, if it exists, null otherwise
     */
    Object set(String key, Object value);

    /**
     * Sets given value to the specified key.
     *
     * @param key   the specified key, must be non-null
     * @param value the specified value
     * @param <T>   the type of the provided value
     * @return previous associated value, if it exists, null otherwise
     */
    @SuppressWarnings("unchecked")
    default <T> T set(Key<T> key, T value) {
        return (T) set(key.getKey(), value);
    }

    /**
     * Removes the specified key from set.
     *
     * @param key the specified key
     * @return associated value, if it exists, null otherwise
     */
    Object remove(String key);

    /**
     * Removes the specified key from set.
     *
     * @param key the specified key
     * @param <T> the type of the removed value
     * @return associated value, if it exists, null otherwise
     */
    @SuppressWarnings("unchecked")
    default <T> T remove(Key<T> key) {
        return (T) remove(key.getKey());
    }

    /**
     * Checks if this {@link OptionSet} contains no options.
     *
     * @return true if {@link OptionSet} is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Gets set contains all known keys from this {@link OptionSet} instance.
     *
     * @return {@link Set} instance
     */
    Set<String> getKeys();

    /**
     * Gets all option entries from this {@link OptionSet} instance as {@link Map}.
     *
     * @return {@link Map} instance
     */
    Map<String, Object> asMap();

    /**
     * Performs given action for each key in this {@link OptionSet} instance.
     *
     * @param action the action to be performed, must be non-null
     */
    void forEach(Runnable1<String> action);

    /**
     * Performs given action for each key-value pair in this {@link OptionSet} instance.
     *
     * @param action the action to be performed, must be non-null
     */
    void forEach(Runnable2<String, Object> action);
}
