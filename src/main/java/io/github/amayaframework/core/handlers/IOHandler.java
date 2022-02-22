package io.github.amayaframework.core.handlers;

import com.github.romanqed.jutils.util.Action;

import java.io.IOException;

/**
 * Base interface describing i/o handler with 2 {@link Action} - for input and output.
 */
public interface IOHandler {
    /**
     * Handles received session
     *
     * @param session to handle
     * @throws IOException which can be thrown away during processing
     */
    void handle(Session session) throws IOException;

    /**
     * Returns action handles input
     *
     * @return {@link Action}
     */
    Action<Object, Object> getInput();

    /**
     * Returns action handles output
     *
     * @return {@link Action}
     */
    Action<Object, Object> getOutput();
}
