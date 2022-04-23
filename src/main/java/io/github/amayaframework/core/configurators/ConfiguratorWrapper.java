package io.github.amayaframework.core.configurators;

import com.github.romanqed.util.pipeline.Pipeline;
import io.github.amayaframework.core.actions.InputStage;
import io.github.amayaframework.core.actions.OutputStage;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.handlers.PipelineHandler;
import io.github.amayaframework.core.pipeline.NamedPipeline;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public final class ConfiguratorWrapper {
    private final static String INPUT_NAME = "configureInput";
    private final static String OUTPUT_NAME = "configureOutput";

    private final Configurator body;

    public ConfiguratorWrapper(Configurator body) {
        this.body = Objects.requireNonNull(body);
    }

    public Configurator getBody() {
        return body;
    }

    private Method findMethod(String name) throws NoSuchMethodException {
        Method[] methods = body.getClass().getDeclaredMethods();
        Method found = Arrays.stream(methods).filter(e -> e.getName().equals(name)).findFirst().orElse(null);
        if (found == null) {
            throw new NoSuchMethodException();
        }
        return found;
    }

    private AccessPolicy extractAccess(String name) throws NoSuchMethodException {
        Method method = findMethod(name);
        Access access = method.getAnnotation(Access.class);
        if (access == null) {
            return AccessPolicy.INDIRECT;
        }
        return access.value();
    }

    private InsertPolicy extractInsert(String name) throws NoSuchMethodException {
        Method method = findMethod(name);
        Insert insert = method.getAnnotation(Insert.class);
        if (insert == null) {
            return InsertPolicy.PLAIN;
        }
        return insert.value();
    }

    private void configureInput(Pipeline<String> pipeline) throws Throwable {
        AccessPolicy access = extractAccess(INPUT_NAME);
        if (access == AccessPolicy.DIRECT) {
            body.configureInput(new NamedPipeline(pipeline));
            return;
        }
        NamedPipeline toProcess = new NamedPipeline();
        body.configureInput(toProcess);
        InsertPolicy insert = extractInsert(INPUT_NAME);
        pipeline.insertBefore(InputStage.INVOKE_CONTROLLER, insert.execute(toProcess));
    }

    private void configureOutput(Pipeline<String> pipeline) throws Throwable {
        AccessPolicy access = extractAccess(OUTPUT_NAME);
        if (access == AccessPolicy.DIRECT) {
            body.configureOutput(new NamedPipeline(pipeline));
            return;
        }
        NamedPipeline toProcess = new NamedPipeline();
        body.configureOutput(toProcess);
        InsertPolicy insert = extractInsert(OUTPUT_NAME);
        pipeline.insertAfter(OutputStage.PROCESS_HEADERS, insert.execute(toProcess));
    }

    public void configure(PipelineHandler handler, Controller controller) throws Throwable {
        body.configureController(controller);
        configureInput(handler.getInput());
        configureOutput(handler.getOutput());
    }
}
