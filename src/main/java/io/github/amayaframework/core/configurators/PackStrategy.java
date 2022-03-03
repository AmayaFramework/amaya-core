package io.github.amayaframework.core.configurators;


import com.github.romanqed.jutils.util.Action;
import io.github.amayaframework.core.pipeline.NamedPipeline;

public class PackStrategy implements Action<NamedPipeline, NamedPipeline> {
    @Override
    public NamedPipeline execute(NamedPipeline pipeline) {
        NamedPipeline ret = new NamedPipeline();
        ret.put(Integer.toString(pipeline.hashCode()), pipeline);
        return ret;
    }
}
