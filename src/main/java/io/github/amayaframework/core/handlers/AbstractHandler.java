package io.github.amayaframework.core.handlers;

import com.github.romanqed.util.Action;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.http.HttpCode;

import java.io.IOException;
import java.util.Objects;

public abstract class AbstractHandler implements IOHandler {
    protected final EventManager manager;
    protected Action<Object, Object> inputAction;
    protected Action<Object, Object> outputAction;

    public AbstractHandler(EventManager manager) {
        Objects.requireNonNull(manager);
        this.manager = manager;
    }

    @Override
    public void handle(Session session) throws IOException {
        Objects.requireNonNull(session);
        HttpResponse response;
        try {
            response = session.handleInput(inputAction);
        } catch (Throwable e) {
            session.reject(e);
            manager.callEvent(Event.INPUT_ERROR, e);
            return;
        }
        if (response == null) {
            session.reject(HttpCode.INTERNAL_SERVER_ERROR, "Response is null");
            manager.callEvent(Event.INPUT_ERROR, null);
            return;
        }
        try {
            session.handleOutput(outputAction, response);
        } catch (Throwable e) {
            session.reject(e);
            manager.callEvent(Event.OUTPUT_ERROR, e);
        }
    }
}
