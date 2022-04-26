package io.github.amayaframework.core.util;

import java.util.Map;

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
     * Puts the attachment in the attachment storage
     *
     * @param key   which is related to attachment
     * @param value which contains attachment
     */
    void setAttachment(String key, Object value);

    /**
     * Removes attachment from storage
     *
     * @param key which is related to attachment
     * @return removed attachment object
     */
    Object removeAttachment(String key);

    @SuppressWarnings("unchecked")
    default <T> T get(String key) {
        Object ret = getAttachment(key);
        if (ret == null) {
            return null;
        }
        try {
            return (T) ret;
        } catch (Exception e) {
            return null;
        }
    }

    default String getString(String key) {
        return get(key);
    }

    default boolean getBool(String key) {
        Boolean ret = get(key);
        if (ret == null) {
            return false;
        }
        return ret;
    }

    default int getInt(String key) {
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

    default char getChar(String key) {
        Character ret = get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }
}
