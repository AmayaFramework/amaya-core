package io.github.amayaframework.context;

import jakarta.servlet.ServletOutputStream;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 *
 */
public interface Response extends Transaction {

    /**
     * @return
     * @throws IOException
     */
    ServletOutputStream getOutputStream() throws IOException;

    /**
     * @return
     * @throws IOException
     */
    PrintWriter getWriter() throws IOException;

    /**
     * @param length
     */
    void setContentLength(long length);

    /**
     * @param locale
     */
    void setLocale(Locale locale);

    /**
     * @return
     */
    boolean isSent();

    /**
     *
     */
    void reset();

    /**
     * @return
     */
    int getBufferSize();

    /**
     * @param size
     */
    void setBufferSize(int size);

    /**
     * @throws IOException
     */
    void flushBuffer() throws IOException;

    /**
     *
     */
    void resetBuffer();
}
