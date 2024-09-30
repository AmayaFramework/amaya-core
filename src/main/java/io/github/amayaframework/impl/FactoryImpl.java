package io.github.amayaframework.impl;

import com.github.romanqed.jconv.LinkedPipelineBuilder;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.core.ApplicationBuilder;
import io.github.amayaframework.core.ApplicationBuilderFactory;

public class FactoryImpl implements ApplicationBuilderFactory {

    @Override
    public ApplicationBuilder create() {
        var optionBuilder = new DeferredOptionSetBuilder();
        var managerBuilder = new PlainManagerBuilder();
        var handlerBuilder = new LinkedPipelineBuilder<HttpContext>();
        return new PlainApplicationBuilder(optionBuilder, managerBuilder, handlerBuilder);
    }
}
