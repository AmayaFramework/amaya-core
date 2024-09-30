package io.github.amayaframework.core;

import com.github.romanqed.jconv.PipelineBuilder;
import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.context.HttpContext;

public interface HandlerBuilderConsumer extends Runnable1<PipelineBuilder<HttpContext>> {

    @Override
    void run(PipelineBuilder<HttpContext> builder) throws Throwable;
}
