package io.github.amayaframework.options;

import com.github.romanqed.jfunc.Runnable2;
import com.github.romanqed.jfunc.Runnable3;

import java.util.Map;
import java.util.Set;

/**
 * An interface extending {@link OptionSet} interface with basic option group operations.
 * <br>
 * It includes the basic group operations: getGroup, setGroup, containsGroup, removeGroup.
 * <br>
 * Every single key operation splits key by 2 parts: group identifier and option key identifier.
 * After that use group identifier to try access {@link OptionSet} instance related to requested group.
 * If set can be accessed, interacts with value from it by option key.
 */
public interface GroupOptionSet extends OptionSet {

    /**
     * Gets the {@link OptionSet} instance associated with given group name.
     *
     * @param group the specified group name
     * @return the {@link OptionSet} instance associated with group name or null
     */
    OptionSet getGroup(String group);

    /**
     * Ensures the {@link OptionSet} for the given group name exists and returns it.
     * <br>
     * If no set is associated with the group, a new one is created and stored.
     *
     * @param group the specified group name
     * @return the associated or newly created {@link OptionSet} instance
     */
    OptionSet ensureGroup(String group);

    /**
     * Checks if set contains group with the specified name.
     *
     * @param group the specified group name
     * @return true, if group exists, false otherwise
     */
    boolean containsGroup(String group);

    /**
     * Sets given {@link OptionSet} instance to the specified group name.
     *
     * @param group the specified group name, must be non-null
     * @param set   the specified {@link OptionSet} instance, may be null
     * @return previous associated instance, if it exists, null otherwise
     */
    OptionSet setGroup(String group, OptionSet set);

    /**
     * Removes the specified group from set.
     *
     * @param group the specified group name
     * @return associated {@link OptionSet} instance, if it exists, null otherwise
     */
    OptionSet removeGroup(String group);

    // Grouped methods

    /**
     * Retrieves a value associated with the specified key within the given group.
     *
     * @param group the group name
     * @param key the option key
     * @param <T> the type of requested value
     * @return the value associated with the key, or null if not found
     */
    <T> T get(String group, String key);

    /**
     * Retrieves a value associated with the specified key within the given group.
     * <p>
     * This overload accepts a typed {@link Key} and delegates to
     * {@link #get(String, String)} using the key's string representation.
     * </p>
     *
     * @param group the group name
     * @param key the typed option key
     * @param <T> the type of requested value
     * @return the value associated with the key, or null if not found
     * @see #get(String, String)
     */
    default <T> T get(String group, Key<T> key) {
        return get(group, key.getKey());
    }

    /**
     * Retrieves a value associated with the specified key within the given group,
     * returning the default value if not found.
     *
     * @param group the group name
     * @param key the option key
     * @param def the default value to return if the key is not found
     * @param <T> the type of requested value
     * @return the value associated with the key, or the default value
     */
    <T> T get(String group, String key, T def);

    /**
     * Retrieves a value associated with the specified key within the given group,
     * returning the default value if not found.
     * <p>
     * This overload accepts a typed {@link Key} and delegates to
     * {@link #get(String, String, Object)} using the key's string representation.
     * </p>
     *
     * @param group the group name
     * @param key the typed option key
     * @param def the default value to return if the key is not found
     * @param <T> the type of requested value
     * @return the value associated with the key, or the default value
     * @see #get(String, String, Object)
     */
    default <T> T get(String group, Key<T> key, T def) {
        return get(group, key.getKey(), def);
    }

    /**
     * Treats the value associated with the given key in the specified group as a key flag.
     *
     * @param group the group name
     * @param key the option key
     * @return true if considered a key, false otherwise
     */
    boolean asKey(String group, String key);

    /**
     * Treats the value associated with the given key in the specified group as a key flag.
     * <p>
     * This overload accepts a typed {@link Key} and delegates to
     * {@link #asKey(String, String)} using the key's string representation.
     * </p>
     *
     * @param group the group name
     * @param key the typed option key
     * @return true if considered a key, false otherwise
     * @see #asKey(String, String)
     */
    default boolean asKey(String group, Key<?> key) {
        return asKey(group, key.getKey());
    }

    /**
     * Treats the value associated with the given key in the specified group as a boolean.
     *
     * @param group the group name
     * @param key the option key
     * @return boolean value interpretation
     */
    boolean asBool(String group, String key);

    /**
     * Treats the value associated with the given key in the specified group as a boolean.
     * <p>
     * This overload accepts a typed {@link Key} and delegates to
     * {@link #asBool(String, String)} using the key's string representation.
     * </p>
     *
     * @param group the group name
     * @param key the typed option key
     * @return boolean value interpretation
     * @see #asBool(String, String)
     */
    default boolean asBool(String group, Key<?> key) {
        return asBool(group, key.getKey());
    }

    /**
     * Checks if the specified key exists within the given group.
     *
     * @param group the group name
     * @param key the option key
     * @return true if the key exists, false otherwise
     */
    boolean contains(String group, String key);

    /**
     * Checks if the specified key exists within the given group.
     * <p>
     * This overload accepts a typed {@link Key} and delegates to
     * {@link #contains(String, String)} using the key's string representation.
     * </p>
     *
     * @param group the group name
     * @param key the typed option key
     * @return true if the key exists, false otherwise
     * @see #contains(String, String)
     */
    default boolean contains(String group, Key<?> key) {
        return contains(group, key.getKey());
    }

    /**
     * Sets the given value to the specified key within the given group.
     *
     * @param group the group name
     * @param key the option key
     * @param value the value to associate
     * @return the previous value associated with the key, or null
     */
    Object set(String group, String key, Object value);

    /**
     * Sets the given value to the specified key within the given group.
     * <p>
     * This overload accepts a typed {@link Key} and delegates to
     * {@link #set(String, String, Object)} using the key's string representation.
     * </p>
     *
     * @param group the group name
     * @param key the typed option key
     * @param value the value to associate
     * @param <T> the type of the provided value
     * @return the previous value associated with the key, or null
     * @see #set(String, String, Object)
     */
    @SuppressWarnings("unchecked")
    default <T> T set(String group, Key<T> key, T value) {
        return (T) set(group, key.getKey(), value);
    }

    /**
     * Removes the specified key from the given group.
     *
     * @param group the group name
     * @param key the option key
     * @return the value previously associated with the key, or null
     */
    Object remove(String group, String key);

    /**
     * Removes the specified key from the given group.
     * <p>
     * This overload accepts a typed {@link Key} and delegates to
     * {@link #remove(String, String)} using the key's string representation.
     * </p>
     *
     * @param group the group name
     * @param key the typed option key
     * @param <T> the type of the removed value
     * @return the value previously associated with the key, or null
     * @see #remove(String, String)
     */
    @SuppressWarnings("unchecked")
    default <T> T remove(String group, Key<T> key) {
        return (T) remove(group, key.getKey());
    }

    /**
     * Gets set contains all group names from this {@link OptionSet} instance.
     *
     * @return {@link Set} instance
     */
    @Override
    Set<String> keys();

    /**
     * Gets map contains all group entries from this {@link OptionSet} instance.
     *
     * @return {@link Map} instance
     */
    @Override
    Map<String, Object> asMap();

    /**
     * Performs given action for each key in each group in this {@link GroupOptionSet} instance.
     *
     * @param action the action to be performed, must be non-null
     */
    void forEachGroup(Runnable2<String, String> action);

    /**
     * Performs given action for each key-value pair in each group in this {@link OptionSet} instance.
     *
     * @param action the action to be performed, must be non-null
     */
    void forEachGroup(Runnable3<String, String, Object> action);
}
