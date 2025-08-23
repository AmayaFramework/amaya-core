package io.github.amayaframework.context;

import jakarta.servlet.http.HttpSession;

import java.util.Map;

/**
 * An interface describing access to the HTTP session and its attributes.
 * Provides methods for checking session existence, retrieving or creating
 * the session, manipulating its identifier, and accessing its attributes.
 */
public interface Sessionable {

    /**
     * Checks whether an HTTP session currently exists for this request.
     * This method never creates a new session.
     *
     * @return {@code true} if a session exists, {@code false} otherwise
     */
    boolean hasSession();

    /**
     * Returns the current {@link HttpSession} associated with this request,
     * or creates one if it does not already exist and {@code create} is true.
     *
     * @param create if {@code true}, creates a new session if one does not exist;
     *               if {@code false}, returns {@code null} if no session exists
     * @return the current {@link HttpSession}, or {@code null} if no session
     * exists and {@code create} is {@code false}
     */
    HttpSession session(boolean create);

    /**
     * Returns the current {@link HttpSession} associated with this request,
     * creating one if it does not exist.
     *
     * @return the current {@link HttpSession}, never {@code null}
     */
    HttpSession session();

    /**
     * Changes the session identifier for the current session while preserving
     * all existing session attributes. This can be used as a protection against
     * session fixation attacks.
     *
     * @return the new session identifier
     * @throws IllegalStateException if no session exists
     */
    String changeSessionId();

    /**
     * Returns the session ID specified by the client, for example via cookie
     * or URL rewriting.
     *
     * @return the requested session ID, or {@code null} if none was specified
     */
    String requestedSessionId();

    /**
     * Checks if the session ID specified by the client is valid in the context
     * of the current session management strategy.
     *
     * @return {@code true} if the requested session ID is valid, {@code false} otherwise
     */
    boolean requestedSessionIdValid();

    /**
     * Checks whether the session ID specified by the client was obtained
     * from the request URL.
     *
     * @return {@code true} if the session ID came from the URL, {@code false} otherwise
     */
    boolean requestedSessionIdFromUrl();

    /**
     * Checks whether the session ID specified by the client was obtained
     * from a cookie.
     *
     * @return {@code true} if the session ID came from a cookie, {@code false} otherwise
     */
    boolean requestedSessionIdFromCookie();

    /**
     * Returns all session attributes as a map, with attribute names as keys
     * and their corresponding values as map values.
     * <p>
     * Calling this method may create a new session if none exists.
     *
     * @return a map containing session attributes
     */
    Map<String, Object> sessionParams();

    /**
     * Retrieves the value of a session attribute by its name.
     * This method does not create a session if one does not exist.
     *
     * @param name the name of the session attribute
     * @param <T>  the type of the attribute value
     * @return the attribute value, or {@code null} if no session exists
     * or the attribute is not found
     */
    <T> T sessionParam(String name);

    /**
     * Sets a session attribute with the given name and value.
     * If no session exists, one will be created.
     *
     * @param name  the name of the session attribute
     * @param value the value to associate with the attribute;
     *              if {@code null}, the attribute is removed
     */
    void sessionParam(String name, Object value);
}
