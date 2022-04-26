package io.github.amayaframework.core.configurators;

import com.github.romanqed.util.Action;
import io.github.amayaframework.core.pipeline.NamedPipeline;

class PlainStrategy implements Action<NamedPipeline, NamedPipeline> {
    @Override
    public NamedPipeline execute(NamedPipeline pipeline) throws Exception {
        return pipeline;
    }
}
