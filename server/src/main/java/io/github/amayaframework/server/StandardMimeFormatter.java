package io.github.amayaframework.server;

import io.github.amayaframework.http.MimeData;

/**
 * Default implementation of {@link MimeFormatter}.
 * <p>
 * This formatter produces a string representation of a {@link MimeData}
 * using the {@link MimeData#toString()} method. The resulting string
 * is suitable for use in HTTP headers such as {@code Content-Type}
 * or {@code Accept}.
 * </p>
 */
public final class StandardMimeFormatter implements MimeFormatter {

    @Override
    public String format(MimeData data) {
        return data.toString();
    }
}
