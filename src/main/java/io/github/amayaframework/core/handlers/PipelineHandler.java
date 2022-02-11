package io.github.amayaframework.core.handlers;

import com.github.romanqed.jutils.http.HttpCode;
import com.github.romanqed.jutils.pipeline.Pipeline;
import io.github.amayaframework.core.configurators.PipelineConfigurator;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.pipelines.RequestData;
import io.github.amayaframework.core.util.AmayaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

public class PipelineHandler implements IOHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Pipeline input;
    private final Pipeline output;
    private final Controller controller;

    public PipelineHandler(Controller controller) {
        input = new Pipeline();
        output = new Pipeline();
        this.controller = Objects.requireNonNull(controller);
    }

    @Override
    public void handle(Session session) throws IOException {
        Objects.requireNonNull(session);
        RequestData requestData;
        try {
            requestData = session.handleInput(input);
        } catch (Exception e) {
            logger.error("Error at input", e);
            session.reject(e);
            return;
        }
        HttpResponse response;
        try {
            response = requestData.getRoute().getBody().execute(requestData.getRequest());
        } catch (Exception e) {
            logger.error("Error at controller invoking", e);
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
    public Pipeline getInput() {
        return input;
    }

    @Override
    public Pipeline getOutput() {
        return output;
    }

    @Override
    public Controller getController() {
        return controller;
    }

    public void configure(Collection<PipelineConfigurator> configurators) {
        Objects.requireNonNull(configurators);
        configurators.forEach(e -> e.accept(this));
        if (AmayaConfig.INSTANCE.getDebug()) {
            String message = "Handler pipelines have been successfully configured\n" +
                    "Pipeline: " + input + "\n";
            logger.debug(message);
        }
    }
}
