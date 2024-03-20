package io.github.amayaframework.core.context;

import java.nio.charset.Charset;
import java.util.Locale;

public interface Transaction {
    Charset getCharset();

    void setCharset(Charset charset);

    long getContentLength();

    MIMEData getContentType();

    void setContentType(MIMEData type);

    String getProtocol();

    String getScheme();

    Locale getLocale();

    Iterable<Locale> getLocales();
}
