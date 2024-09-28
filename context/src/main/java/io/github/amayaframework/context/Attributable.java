package io.github.amayaframework.context;

import java.util.Map;

/**
 * An interface describing an abstract attribute holder.
 *
 * @param <K> the type of attribute key
 */
public interface Attributable<K> {

    /**
     * Gets {@link Map} instance containing all held attributes.
     *
     * @return {@link Map} instance
     */
    Map<K, Object> getAttributes();

    /**
     * Gets attribute value associated with given key.
     *
     * @param key the specified attribute key
     * @param <V> the type of attribute value
     * @return attribute value if one exists, null otherwise
     */
    <V> V get(K key);

    /**
     * Sets value for attribute by given key.
     *
     * @param key   the specified attribute key, must be non-null
     * @param value the specified attribute value
     */
    void set(K key, Object value);

    /**
     * Removes attribute by given key.
     *
     * @param key the specified attribute key
     * @return the value of removed attribute or null
     */
    Object remove(K key);

    /**
     * Checks if collection contains attribute with given key.
     *
     * @param key the specified attribute key
     * @return true if attribute exists, false otherwise
     */
    boolean contains(K key);

    /**
     * Gets attribute by given key as {@link String}.
     *
     * @param key the specified attribute key
     * @return the {@link String} attribute value if one exists, null otherwise
     */
    default String getString(K key) {
        return get(key);
    }

    /**
     * Gets attribute by given key as boolean.
     *
     * @param key the specified attribute key
     * @return the boolean attribute value if one exists, false otherwise
     */
    default boolean getBool(K key) {
        var ret = this.<Boolean>get(key);
        if (ret == null) {
            return false;
        }
        return ret;
    }

    /**
     * Gets attribute by given key as char.
     *
     * @param key the specified attribute key
     * @return the char attribute value if one exists, 0 otherwise
     */
    default char getChar(K key) {
        var ret = this.<Character>get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }

    /**
     * Gets attribute by given key as byte.
     *
     * @param key the specified attribute key
     * @return the byte attribute value if one exists, 0 otherwise
     */
    default byte getByte(K key) {
        var ret = this.<Byte>get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }

    /**
     * Gets attribute by given key as short.
     *
     * @param key the specified attribute key
     * @return the short attribute value if one exists, 0 otherwise
     */
    default short getShort(K key) {
        var ret = this.<Short>get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }

    /**
     * Gets attribute by given key as int.
     *
     * @param key the specified attribute key
     * @return the int attribute value if one exists, 0 otherwise
     */
    default int getInt(K key) {
        var ret = this.<Integer>get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }

    /**
     * Gets attribute by given key as long.
     *
     * @param key the specified attribute key
     * @return the long attribute value if one exists, 0 otherwise
     */
    default long getLong(K key) {
        var ret = this.<Long>get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }

    /**
     * Gets attribute by given key as float.
     *
     * @param key the specified attribute key
     * @return the float attribute value if one exists, 0 otherwise
     */
    default float getFloat(K key) {
        var ret = this.<Float>get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }

    /**
     * Gets attribute by given key as double.
     *
     * @param key the specified attribute key
     * @return the double attribute value if one exists, 0 otherwise
     */
    default double getDouble(K key) {
        var ret = this.<Double>get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }
}
