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
     * Puts the attachment in the attachment {@link Map}
     *
     * @param key   which is related to attachment
     * @param value which contains attachment
     */
    void setAttachment(String key, Object value);
}
