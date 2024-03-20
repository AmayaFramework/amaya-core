package io.github.amayaframework.core.context;

import java.io.OutputStream;
import java.util.Locale;

public interface Response extends Transaction {
    OutputStream getOutputStream();

    void setContentLength(long length);

    void setLocale(Locale locale);

    boolean isSent();

    void reset();

    int getBufferSize();

    void setBufferSize(int size);

    void flushBuffer();

    void resetBuffer();
}
