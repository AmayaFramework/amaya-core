package io.github.amayaframework.context;

import io.github.amayaframework.http.MimeData;
import io.github.amayaframework.http.MimeType;

import java.nio.charset.Charset;
import java.util.Locale;

/**
 *
 */
public interface Transaction {

    /**
     * @return
     */
    Charset getCharset();

    /**
     * @param charset
     */
    void setCharset(Charset charset);

    /**
     * @return
     */
    long getContentLength();

    /**
     * @return
     */
    MimeData getMimeData();

    /**
     * @param data
     */
    void setMimeData(MimeData data);

    /**
     * @param type
     */
    void setMimeType(MimeType type);

    /**
     * @return
     */
    String getProtocol();

    /**
     * @return
     */
    String getScheme();

    /**
     * @return
     */
    Locale getLocale();
}
