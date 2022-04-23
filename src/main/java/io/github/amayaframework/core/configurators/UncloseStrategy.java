package io.github.amayaframework.core.configurators;

import com.github.romanqed.util.Action;
import com.github.romanqed.util.Node;
import com.github.romanqed.util.pipeline.Pipeline;
import io.github.amayaframework.core.pipeline.NamedPipeline;

class UncloseStrategy implements Action<NamedPipeline, NamedPipeline> {
    private static void uncloseActions(Pipeline<?> source, Pipeline<String> destination) {
        for (Node<?, Action<Object, Object>> node : source) {
            Action<?, ?> action = node.getValue();
            if (action instanceof Pipeline) {
                uncloseActions((Pipeline<?>) action, destination);
            } else {
                destination.put(node.getKey().toString(), action);
            }
        }
    }

    @Override
    public NamedPipeline execute(NamedPipeline pipeline) throws Exception {
        NamedPipeline ret = new NamedPipeline();
        uncloseActions(pipeline, ret);
        return ret;
    }
}
