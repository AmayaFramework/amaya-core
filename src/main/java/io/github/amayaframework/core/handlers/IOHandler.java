package io.github.amayaframework.core.handlers;

import com.github.romanqed.jutils.pipeline.Pipeline;
import com.github.romanqed.jutils.util.Action;
import io.github.amayaframework.core.controllers.Controller;

import java.io.IOException;

public interface IOHandler {
    void handle(Session session) throws IOException;

    /**
     * Returns action handles input
     *
     * @return {@link Pipeline}
     */
    Action<Object, Object> getInput();

    /**
     * Returns action handles output
     *
     * @return {@link Pipeline}
     */
    Action<Object, Object> getOutput();

    /**
     * Returns the controller bound to the handler.
     *
     * @return {@link Controller}
     */
    Controller getController();
}
