package io.github.amayaframework.core.builder;

import com.github.romanqed.jconv.PipelineBuilder;
import io.github.amayaframework.core.Environment;
import io.github.amayaframework.di.ManualProviderBuilder;

public interface BuildContext {

    Environment getEnvironment();

    ManualProviderBuilder getProviderBuilder();

    PipelineBuilder<?> getPipelineBuilder();
}
