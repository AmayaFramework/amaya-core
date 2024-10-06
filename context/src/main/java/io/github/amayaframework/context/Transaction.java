package io.github.amayaframework.context;

import io.github.amayaframework.http.MimeData;
import io.github.amayaframework.http.MimeType;

import java.nio.charset.Charset;
import java.util.Locale;

/**
 * An interface describing the abstract web protocol transaction.
 */
public interface Transaction {

    /**
     * Gets the character set used in the transaction.
     *
     * @return the {@link Charset} instance representing the character set.
     */
    Charset getCharset();

    /**
     * Sets the character set for the transaction.
     *
     * @param charset the {@link Charset} instance to be set for the transaction, must be non-null
     */
    void setCharset(Charset charset);

    /**
     * Gets the content length of the transaction.
     *
     * @return the content length as a long value
     */
    long getContentLength();

    /**
     * Gets the MIME data associated with the transaction.
     *
     * @return the {@link MimeData} instance containing MIME-related information.
     */
    MimeData getMimeData();

    /**
     * Sets the MIME data for the transaction.
     *
     * @param data the {@link MimeData} instance to be set for the transaction.
     */
    void setMimeData(MimeData data);

    /**
     * Sets the MIME type for the transaction.
     *
     * @param type the {@link MimeType} object to be set for the transaction.
     */
    void setMimeType(MimeType type);

    /**
     * Gets the protocol used in the transaction.
     *
     * @return a string representing the protocol.
     */
    String getProtocol();

    /**
     * Gets the scheme of the transaction.
     *
     * @return a string representing the scheme.
     */
    String getScheme();

    /**
     * Gets the locale associated with the transaction.
     *
     * @return the {@link Locale} instance representing the locale.
     */
    Locale getLocale();
}
