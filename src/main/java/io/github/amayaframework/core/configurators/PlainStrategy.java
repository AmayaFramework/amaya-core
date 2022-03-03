package io.github.amayaframework.core.configurators;

import com.github.romanqed.jutils.util.Action;
import io.github.amayaframework.core.pipeline.NamedPipeline;

public class PlainStrategy implements Action<NamedPipeline, NamedPipeline> {
    @Override
    public NamedPipeline execute(NamedPipeline pipeline) throws Exception {
        NamedPipeline ret = new NamedPipeline();
        ret.putAll(pipeline);
        return ret;
    }
}
