package io.github.amayaframework.context;

import io.github.amayaframework.http.MimeData;

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
    Charset charset();

    /**
     * Sets the character set for the transaction.
     *
     * @param charset the {@link Charset} instance to be set for the transaction, must be non-null
     */
    void charset(Charset charset);

    /**
     * Gets the content length of the transaction.
     *
     * @return the content length as a long value
     */
    long contentLength();

    /**
     * Gets the MIME data associated with the transaction.
     *
     * @return the {@link MimeData} instance containing MIME-related information.
     */
    MimeData mimeData();

    /**
     * Gets the protocol used in the transaction.
     *
     * @return a string representing the protocol.
     */
    String protocol();

    /**
     * Gets the scheme of the transaction.
     *
     * @return a string representing the scheme.
     */
    String scheme();

    /**
     * Gets the locale associated with the transaction.
     *
     * @return the {@link Locale} instance representing the locale.
     */
    Locale locale();
}
