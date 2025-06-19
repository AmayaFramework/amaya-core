package io.github.amayaframework.options;

import com.github.romanqed.jtype.JType;

import java.util.Objects;

/**
 * A typed key representation used to uniquely identify options or configuration entries
 * with an associated type {@link T}.
 *
 * @param <T> the type of the value associated with this key
 */
public final class Key<T> {
    private final String key;
    private final JType<T> type;

    /**
     * Constructs a new {@link Key} with the specified string identifier and type.
     *
     * @param key  the unique string identifier
     * @param type the type associated with this key
     * @throws NullPointerException if {@code key} or {@code type} is null
     */
    public Key(String key, JType<T> type) {
        this.key = Objects.requireNonNull(key);
        this.type = Objects.requireNonNull(type);
    }

    /**
     * Creates a new {@link Key} using the given string identifier and type.
     *
     * @param key  the string identifier
     * @param type the class representing the type {@link T}
     * @param <T>  the type of the value
     * @return a new {@link Key} instance
     */
    public static <T> Key<T> of(String key, Class<T> type) {
        return new Key<>(key, JType.of(type));
    }

    /**
     * Creates a new {@link Key} using the given string identifier and {@link JType}.
     *
     * @param key  the string identifier
     * @param type the {@link JType} representing the type {@link T}
     * @param <T>  the type of the value
     * @return a new {@link Key} instance
     */
    public static <T> Key<T> of(String key, JType<T> type) {
        return new Key<>(key, type);
    }

    /**
     * Creates a new {@link Key} by prefixing the key with a group string.
     *
     * @param group the group name or namespace
     * @param key   the base key string
     * @param type  the class representing the type {@link T}
     * @param <T>   the type of the value
     * @return a new {@link Key} instance with a grouped key
     */
    public static <T> Key<T> of(String group, String key, Class<T> type) {
        return new Key<>(group + key, JType.of(type));
    }

    /**
     * Creates a new {@link Key} by prefixing the key with a group string.
     *
     * @param group the group name or namespace
     * @param key   the base key string
     * @param type  the {@link JType} representing the type {@link T}
     * @param <T>   the type of the value
     * @return a new {@link Key} instance with a grouped key
     */
    public static <T> Key<T> of(String group, String key, JType<T> type) {
        return new Key<>(group + key, type);
    }

    /**
     * Creates a new {@link Key} by prefixing an existing key with a group string.
     *
     * @param group the group name or namespace
     * @param key   the original {@link Key}
     * @param <T>   the type of the value
     * @return a new {@link Key} instance with a grouped key
     */
    public static <T> Key<T> of(String group, Key<T> key) {
        return new Key<>(group + key.key, key.type);
    }

    /**
     * Returns the string identifier of this key.
     *
     * @return the key string
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the {@link JType} associated with this key.
     *
     * @return the type information
     */
    public JType<T> getType() {
        return type;
    }

    /**
     * Creates a new {@link Key} with the same type but with the key prefixed by the given group string.
     *
     * @param group the group name or namespace
     * @return a new {@link Key} instance with a grouped key
     */
    public Key<T> withGroup(String group) {
        return new Key<>(group + key, type);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        var that = (Key<?>) o;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return "Key{" +
                "key='" + key + '\'' +
                ", type=" + type +
                '}';
    }
}
