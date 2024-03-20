package io.github.amayaframework.core.builder;

import com.github.romanqed.jconv.PipelineBuilder;
import com.github.romanqed.jfunc.Runnable2;
import io.github.amayaframework.core.Environment;
import io.github.amayaframework.core.context.ApplicationContext;

public interface PipelineConfigurator extends Runnable2<Environment, PipelineBuilder<ApplicationContext>> {

    @Override
    void run(Environment environment, PipelineBuilder<ApplicationContext> builder) throws Throwable;
}
