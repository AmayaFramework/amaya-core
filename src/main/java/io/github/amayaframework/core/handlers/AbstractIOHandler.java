package io.github.amayaframework.core.handlers;

import com.github.romanqed.util.Action;
import com.github.romanqed.util.http.HttpCode;
import io.github.amayaframework.core.ConfigProvider;
import io.github.amayaframework.core.contexts.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public abstract class AbstractIOHandler implements IOHandler {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Action<Object, Object> input;
    protected final Action<Object, Object> output;

    public AbstractIOHandler(Action<Object, Object> input, Action<Object, Object> output) {
        Objects.requireNonNull(input);
        Objects.requireNonNull(output);
        if (ConfigProvider.getConfig().useAsync()) {
            this.input = e -> input.async(e).get();
            this.output = e -> output.async(e).get();
        } else {
            this.input = input;
            this.output = output;
        }
    }

    @Override
    public void handle(Session session) throws IOException {
        Objects.requireNonNull(session);
        HttpResponse response;
        try {
            response = session.handleInput(input);
        } catch (Throwable e) {
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
        } catch (Throwable e) {
            logger.error("Error at output", e);
            session.reject(e);
        }
    }
}
