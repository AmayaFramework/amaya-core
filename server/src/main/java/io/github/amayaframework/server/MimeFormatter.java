package io.github.amayaframework.server;

import io.github.amayaframework.http.MimeData;

/**
 * An interface describing an abstract {@link MimeData} converter to the properly qualified string.
 */
public interface MimeFormatter {

    /**
     * Writes given {@link MimeData} as mime string (see rfc2045).
     *
     * @param data the specified {@link MimeData} instance to be written as string
     * @return a string containing mime qualifier
     */
    String format(MimeData data);
}
