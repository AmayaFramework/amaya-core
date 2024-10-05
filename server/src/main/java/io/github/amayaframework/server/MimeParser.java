package io.github.amayaframework.server;

import io.github.amayaframework.http.MimeData;

/**
 * An interface describing an abstract mime string parser.
 */
public interface MimeParser {

    /**
     * Reads given mime string (see rfc2045) as {@link MimeData} instance.
     *
     * @param data the specified mime string to be parsed, must be non-null
     * @return the {@link MimeData} instance
     * @throws IllegalMimeType if given mime type violates rfc2045 format
     */
    MimeData read(String data);
}
