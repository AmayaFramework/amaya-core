package io.github.amayaframework.core.handlers;


import com.github.romanqed.util.Action;
import com.github.romanqed.util.Handler;

/**
 * Base interface describing i/o handler with 2 {@link Action} - for input and output.
 */
public interface IOHandler extends Handler<Session> {

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
