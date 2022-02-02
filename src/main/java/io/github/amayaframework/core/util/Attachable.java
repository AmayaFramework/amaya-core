package io.github.amayaframework.core.util;

import java.util.Map;
import java.util.Objects;

public interface Attachable {
    /**
     * Returns all stored attachments that could have been changed during the transaction processing transaction.
     *
     * @return {@link Map}
     */
    Map<String, Object> getAttachments();

    /**
     * Returns a specific attachment
     *
     * @param key which is related to attachment
     * @return {@link Object}
     */
    Object getAttachment(String key);

    /**
     * Puts the attachment in the attachment {@link Map}
     *
     * @param key   which is related to attachment
     * @param value which contains attachment
     */
    void setAttachment(String key, Object value);

    @SuppressWarnings("unchecked")
    default <T> T get(String key) {
        Objects.requireNonNull(key);
        try {
            return (T) getAttachment(key);
        } catch (Exception e) {
            return null;
        }
    }

    default String getString(String key) {
        return get(key);
    }

    default boolean getBoolean(String key) {
        Boolean ret = get(key);
        if (ret == null) {
            return false;
        }
        return ret;
    }

    default int getInteger(String key) {
        Integer ret = get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }

    default double getDouble(String key) {
        Double ret = get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }

    default char getCharacter(String key) {
        Character ret = get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }
}
