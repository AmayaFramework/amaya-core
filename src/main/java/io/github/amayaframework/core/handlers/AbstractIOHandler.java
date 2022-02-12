package io.github.amayaframework.core.handlers;

import com.github.romanqed.jutils.http.HttpCode;
import com.github.romanqed.jutils.util.Action;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.controllers.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public abstract class AbstractIOHandler implements IOHandler {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Action<Object, Object> input;
    protected final Action<Object, Object> output;
    protected final Controller controller;

    public AbstractIOHandler(Action<Object, Object> input, Action<Object, Object> output, Controller controller) {
        this.input = Objects.requireNonNull(input);
        this.output = Objects.requireNonNull(output);
        this.controller = Objects.requireNonNull(controller);
    }

    @Override
    public void handle(Session session) throws IOException {
        Objects.requireNonNull(session);
        HttpResponse response;
        try {
            response = session.handleInput(input);
        } catch (Exception e) {
            logger.error("Error at input", e);
            session.reject(e);
            return;
        }
        if (response == null) {
            logger.error("Response is null");
            session.reject(HttpCode.INTERNAL_SERVER_ERROR, "Response is null");
            return;
        }
        try {
            session.handleOutput(output, response);
        } catch (Exception e) {
            logger.error("Error at output", e);
            session.reject(e);
        }
    }

    @Override
    public Controller getController() {
        return controller;
    }
}
