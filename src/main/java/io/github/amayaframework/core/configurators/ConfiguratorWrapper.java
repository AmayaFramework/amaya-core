package io.github.amayaframework.core.configurators;

import com.github.romanqed.util.pipeline.Pipeline;
import io.github.amayaframework.core.actions.InputStage;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.handlers.PipelineHandler;
import io.github.amayaframework.core.pipeline.NamedPipeline;

import java.lang.reflect.Method;
import java.util.Objects;

public final class ConfiguratorWrapper {
    private final Configurator body;

    public ConfiguratorWrapper(Configurator body) {
        this.body = Objects.requireNonNull(body);
    }

    public Configurator getBody() {
        return body;
    }

    private InsertPolicy extractInsert(Method method) {
        Insert insert = method.getAnnotation(Insert.class);
        if (insert == null) {
            return InsertPolicy.PLAIN;
        }
        return insert.value();
    }

    private void configureInput(Pipeline<String> pipeline) throws Throwable {
        Method method = body.getClass().getDeclaredMethod("configureInput", NamedPipeline.class);
        if (method.isAnnotationPresent(DirectAccess.class)) {
            body.configureInput(new NamedPipeline(pipeline));
            return;
        }
        NamedPipeline toProcess = new NamedPipeline();
        body.configureInput(toProcess);
        InsertPolicy insert = extractInsert(method);
        pipeline.insertBefore(InputStage.INVOKE_CONTROLLER, insert.execute(toProcess));
    }

    private void configureOutput(Pipeline<String> pipeline) throws Throwable {
        Method method = body.getClass().getDeclaredMethod("configureOutput", NamedPipeline.class);
        if (method.isAnnotationPresent(DirectAccess.class)) {
            body.configureOutput(new NamedPipeline(pipeline));
            return;
        }
        NamedPipeline toProcess = new NamedPipeline();
        body.configureOutput(toProcess);
        InsertPolicy insert = extractInsert(method);
        pipeline.insertFirst(insert.execute(toProcess));
    }

    public void configure(PipelineHandler handler, Controller controller) throws Throwable {
        body.configureController(controller);
        configureInput(handler.getInput());
        configureOutput(handler.getOutput());
    }
}
