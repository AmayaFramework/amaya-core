package io.github.amayaframework.context;

import jakarta.servlet.ServletOutputStream;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * An interface describing the abstract web protocol response.
 */
public interface Response extends Transaction {

    /**
     * Returns a {@link ServletOutputStream} suitable for writing binary data in the response.
     * The servlet container does not encode the binary data.
     *
     * <p>
     * Calling flush() on the ServletOutputStream commits the response.
     * <p>
     * Either this method or {@link #getWriter} may be called to write the body, not both, except when {@link #reset}
     * has been called.
     *
     * @return a {@link ServletOutputStream} for writing binary data
     * @throws IllegalStateException if the <code>getWriter</code> method has been called on this response
     * @throws IOException           if an input or output exception occurred
     */
    ServletOutputStream getOutputStream() throws IOException;

    /**
     * Returns a <code>PrintWriter</code> object that can send character text to the client.
     * The <code>PrintWriter</code> uses the character encoding returned by {@link #getCharset()}.
     * If the response's character encoding has not been specified as described in <code>getCharacterEncoding</code>
     * (i.e., the method just returns the default value <code>ISO-8859-1</code>), <code>getWriter</code> updates it
     * to <code>ISO-8859-1</code>.
     * <p>
     * Calling flush() on the <code>PrintWriter</code> commits the response.
     * <p>
     * Either this method or {@link #getOutputStream} may be called to write the body, not both, except when
     * {@link #reset} has been called.
     *
     * @return a <code>PrintWriter</code> object that can return character data to the client
     * @throws java.io.UnsupportedEncodingException if the character encoding returned by
     *                                              <code>getCharacterEncoding</code> cannot be used
     * @throws IllegalStateException                if the <code>getOutputStream</code> method has already been called
     *                                              for this response object
     * @throws IOException                          if an input or output exception occurred
     */
    PrintWriter getWriter() throws IOException;

    /**
     * Sets the length of the content body in the response In HTTP servlets, this method sets the HTTP Content-Length
     * header.
     *
     * @param length a long specifying the length of the content being returned to the client;
     *               sets the Content-Length header
     */
    void setContentLength(long length);

    /**
     * Sets the locale of the response, if the response has not been committed yet.
     *
     * @param locale the locale of the response
     */
    void setLocale(Locale locale);

    /**
     * Returns a boolean indicating if the response has been sent.
     * Sent response has already had its status code and headers written.
     *
     * @return a boolean indicating if the response has been sent
     */
    boolean isSent();

    /**
     * Clears any data that exists in the buffer as well as the status code, headers. The state of calling
     * {@link #getWriter} or {@link #getOutputStream} is also cleared. It is legal, for instance, to call
     * {@link #getWriter}, reset and then {@link #getOutputStream}. If {@link #getWriter} or
     * {@link #getOutputStream} have been called before this method, then the corresponding returned
     * Writer or OutputStream will be staled and the behavior of using the stale object is undefined.
     *
     * @throws IllegalStateException if the response has already been committed
     */
    void reset();

    /**
     * Returns the actual buffer size used for the response. If no buffering is used, this method returns 0.
     *
     * @return the actual buffer size used
     */
    int getBufferSize();

    /**
     * Sets the preferred buffer size for the body of the response. The servlet container will use a buffer at least as
     * large as the size requested. The actual buffer size used can be found using <code>getBufferSize</code>.
     *
     * <p>
     * A larger buffer allows more content to be written before anything is actually sent, thus providing the servlet
     * with more time to set appropriate status codes and headers. A smaller buffer decreases server memory load and
     * allows the client to start receiving data more quickly.
     * <p>
     *
     * @param size the preferred buffer size
     * @throws IllegalStateException if this method is called after content has been written
     */
    void setBufferSize(int size);

    /**
     * Forces any content in the buffer to be written to the client.
     * A call to this method automatically commits the response,
     * meaning the status code and headers will be written.
     *
     * @throws IOException if the act of flushing the buffer cannot be completed.
     */
    void flushBuffer() throws IOException;

    /**
     * Clears the content of the underlying buffer in the response without clearing headers or status code.
     *
     * @throws IllegalStateException if the response has been committed
     */
    void resetBuffer();
}
