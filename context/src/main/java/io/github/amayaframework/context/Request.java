package io.github.amayaframework.context;

import jakarta.servlet.ServletInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Locale;
import java.util.Map;

/**
 * An interface describing the abstract web protocol request.
 */
public interface Request extends Transaction, Attributable<String> {

    /**
     * Retrieves the body of the request as binary data using a {@link ServletInputStream}.
     * Either this method or {@link #getReader} may be called to read the body, not both.
     *
     * @return a {@link ServletInputStream} object containing the body of the request
     * @throws IllegalStateException if the {@link #getReader} method has already been called for this request
     * @throws IOException           if an input or output exception occurred
     */
    ServletInputStream getInputStream() throws IOException;

    /**
     * Retrieves the body of the request as character data using a {@link BufferedReader}.
     * The reader translates the character data according to the character encoding used on the body.
     * Either this method or {@link #getInputStream} may be called to read the body, not both.
     *
     * @return a {@link BufferedReader} containing the body of the request
     * @throws java.io.UnsupportedEncodingException if the character set encoding used is not supported
     *                                              and the text cannot be decoded
     * @throws IllegalStateException                if {@link #getInputStream} method has been called on this request
     * @throws IOException                          if an input or output exception occurred
     */
    BufferedReader getReader() throws IOException;

    /**
     * Gets {@link InetSocketAddress} instance containing fully qualified local address: hostname and port.
     *
     * @return the {@link InetSocketAddress} instance
     */
    InetSocketAddress getLocalAddress();

    /**
     * Returns the host name of the Internet Protocol (IP) interface on which the request was received.
     *
     * @return a string containing the host name of the IP on which the request was received.
     */
    String getLocalHost();

    /**
     * Gets {@link InetSocketAddress} instance containing fully qualified remote address: hostname and port.
     *
     * @return the {@link InetSocketAddress} instance
     */
    InetSocketAddress getRemoteAddress();

    /**
     * Returns the fully qualified name of the client or the last proxy that sent the request.
     * If the engine cannot or chooses not to resolve the hostname (to improve performance),
     * this method returns the dotted-string form of the IP address.
     *
     * @return a string containing the fully qualified name of the client
     */
    String getRemoteHost();

    /**
     * Gets a {@link Map} of the parameters of this request.
     * <p>
     * Request parameters are extra information sent with the request.
     *
     * @return an immutable java.util.Map containing parameter names as keys and parameter values as map values.
     * The keys in the parameter map are of type String. The values in the parameter map are of type String array.
     */
    Map<String, String[]> getParameters();

    /**
     * Checks if request parameters contains parameter with given name.
     *
     * @param name the specified parameter name
     * @return true if request contains parameter, false otherwise
     */
    boolean containsParameter(String name);

    /**
     * Gets value of request parameter with given name.
     *
     * @param name the specified parameter name
     * @return parameter value if it exists, null otherwise
     */
    String getParameter(String name);

    /**
     * Gets an array of {@link String} instances containing all the values the given request parameter has, or
     * null if the parameter does not exist.
     * <p>
     * If the parameter has a single value, the array has a length of 1.
     *
     * @param name the specified parameter name
     * @return an array of <code>String</code> objects containing the parameter's values
     */
    String[] getParameters(String name);

    /**
     * Returns an {@link Iterable} of {@link Locale} instances indicating, in decreasing order starting with the
     * preferred locale, the locales that are acceptable to the client based on the Accept-Language header.
     * If the client request doesn't provide an Accept-Language header, this method returns an {@link Iterable}
     * containing one {@link Locale}, the default locale for the server.
     *
     * @return an {@link Iterable} of preferred {@link Locale} instances for the client
     */
    Iterable<Locale> getLocales();
}
