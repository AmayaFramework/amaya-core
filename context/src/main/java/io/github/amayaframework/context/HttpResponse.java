package io.github.amayaframework.context;

import io.github.amayaframework.http.HttpCode;
import jakarta.servlet.http.Cookie;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;

/**
 * An interface describing the abstract http protocol response.
 */
public interface HttpResponse extends Response, HttpTransaction {

    /**
     * Sets a cookie in the HTTP response.
     *
     * @param cookie the {@link Cookie} instance to be set in the response.
     */
    void setCookie(Cookie cookie);

    /**
     * Sets a header in the HTTP response with a specified name and {@link Object} value that will be
     * translated to {@link String} by {@link Object#toString()}.
     *
     * @param name  the name of the header to set
     * @param value the value of the header
     */
    void setHeader(String name, Object value);

    /**
     * Sets a header in the HTTP response with a specified name and string value.
     *
     * @param name  the name of the header to set
     * @param value the string value of the header
     */
    void setHeader(String name, String value);

    /**
     * Sets a header in the HTTP response with a specified name and date value.
     *
     * @param name the name of the header to set
     * @param date the date value of the header
     */
    void setHeader(String name, Date date);

    /**
     * Sets a header in the HTTP response with a specified name and long date value (epoch time).
     *
     * @param name the name of the header to set
     * @param date the long date value of the header (epoch time)
     */
    void setHeader(String name, long date);

    /**
     * Gets the current HTTP status code of this response.
     *
     * @return the current {@link HttpCode} representing the status of the response.
     */
    HttpCode getStatus();

    /**
     * Sets the HTTP status code for this response.
     *
     * @param code the {@link HttpCode} to be set as the response status
     */
    void setStatus(HttpCode code);

    /**
     * <p>
     * Sends an error response to the client using the specified status and clears the buffer. The server defaults to
     * creating the response to look like an HTML-formatted server error page containing the specified message, setting
     * the content type to "text/html". The caller is <strong>not</strong> responsible for escaping or re-encoding
     * the message to ensure it is safe with respect to the current response encoding and content type.
     * This aspect of safety is the responsibility of the container, as it is generating the error page containing
     * the message. The server will preserve cookies and may clear or update any headers needed to serve the error page
     * as a valid response.
     * </p>
     *
     * <p>
     * If an error-page declaration has been made for the web application corresponding to the status code passed in, it
     * will be served back in preference to the suggested msg parameter and the msg parameter will be ignored.
     * </p>
     *
     * <p>
     * If the response has already been committed, this method throws an IllegalStateException.
     * After using this method, the response should be considered to be committed and should not be written to.
     *
     * @param code    the error status code
     * @param message the descriptive message
     * @throws IOException           if an input or output exception occurs
     * @throws IllegalStateException if the response was committed
     */
    void sendError(HttpCode code, String message) throws IOException;

    /**
     * Sends an error response to the client using the specified status code and clears the buffer.
     * <p>
     * The server will preserve cookies and may clear or update any headers needed to serve the error page as a valid
     * response.
     * <p>
     * If an error-page declaration has been made for the web application corresponding to the status code passed in, it
     * will be served back the error page
     *
     * <p>
     * If the response has already been committed, this method throws an IllegalStateException. After using this method,
     * the response should be considered to be committed and should not be written to.
     *
     * @param code the error status code
     * @throws IOException           if an input or output exception occurs
     * @throws IllegalStateException if the response was committed before this method call
     */
    void sendError(HttpCode code) throws IOException;

    /**
     * Sends a temporary redirect response to the client using the specified redirect location URL
     * and clears the buffer. The buffer will be replaced with the data set by this method. Calling this method sets
     * the status code to {@link HttpCode#FOUND}. This method can accept relative URLs;the servlet container must
     * convert the relative URL to an absolute URL before sending the response to the client.
     * If the location is relative without a leading '/' the container interprets it as relative to the current request
     * URI. If the location is relative with a leading '/' the container interprets it as relative to the servlet
     * container root. If the location is relative with two leading '/' the container interprets it as a network-path
     * reference (see <a href="http://www.ietf.org/rfc/rfc3986.txt"> RFC 3986: Uniform Resource Identifier (URI):
     * Generic Syntax</a>, section 4.2 &quot;Relative Reference&quot;).
     * <p>
     * If the response has already been committed, this method throws an IllegalStateException. After using this method,
     * the response should be considered to be committed and should not be written to.
     *
     * @param location the redirect location URL
     * @param encode   the flag determines either url will be encoded with current session id
     * @throws IOException           if an input or output exception occurs
     * @throws IllegalStateException if the response was committed or if a partial URL is given and cannot be converted
     *                               into a valid URL
     */
    void sendRedirect(String location, boolean encode) throws IOException;

    /**
     * Sends a temporary redirect response to the client using the specified redirect location URL
     * and clears the buffer. The buffer will be replaced with the data set by this method. Calling this method sets
     * the status code to {@link HttpCode#FOUND}. This method can accept relative URLs;the servlet container must
     * convert the relative URL to an absolute URL before sending the response to the client.
     * If the location is relative without a leading '/' the container interprets it as relative to the current request
     * URI. If the location is relative with a leading '/' the container interprets it as relative to the servlet
     * container root. If the location is relative with two leading '/' the container interprets it as a network-path
     * reference (see <a href="http://www.ietf.org/rfc/rfc3986.txt"> RFC 3986: Uniform Resource Identifier (URI):
     * Generic Syntax</a>, section 4.2 &quot;Relative Reference&quot;).
     * <p>
     * If the response has already been committed, this method throws an IllegalStateException. After using this method,
     * the response should be considered to be committed and should not be written to.
     * <p>
     * Does not encode redirect url.
     *
     * @param location the redirect location URL
     * @throws IOException           if an input or output exception occurs
     * @throws IllegalStateException if the response was committed or if a partial URL is given and cannot be converted
     *                               into a valid URL
     */
    default void sendRedirect(String location) throws IOException {
        sendRedirect(location, false);
    }

    /**
     * Gets the supplier of trailer headers.
     *
     * @return {@link Supplier} of trailer headers
     */
    Supplier<Map<String, String>> getTrailerFields();

    /**
     * Sets the supplier of trailer headers.
     *
     * <p>
     * The trailer header field value is defined as a comma-separated list (see Section 3.2.2 and Section 4.1.2 of RFC
     * 7230).
     * </p>
     *
     * <p>
     * The supplier will be called within the scope of whatever thread/call causes the response content to be completed.
     * Typically this will be any thread calling close() on the output stream or writer.
     * </p>
     *
     * <p>
     * The trailers that run afoul of the provisions of section 4.1.2 of RFC 7230 are ignored.
     * </p>
     *
     * <p>
     * The RFC requires the name of every key that is to be in the supplied Map is included in the comma separated
     * list that is the value of the "Trailer" response header. The application is responsible for ensuring this
     * requirement is met.
     * Failure to do so may lead to interoperability failures.
     * </p>
     *
     * @param supplier the supplier of trailer headers
     * @throws IllegalStateException if it is invoked after the response has been committed, or the trailer is not
     *                               supported in the request, for instance, the underlying protocol is HTTP 1.0,
     *                               or the response is not in chunked encoding in HTTP 1.1.
     */
    void setTrailerFields(Supplier<Map<String, String>> supplier);
}
