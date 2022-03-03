package io.github.amayaframework.core.configurators;

import com.github.romanqed.jutils.util.Action;
import io.github.amayaframework.core.pipeline.NamedPipeline;

import java.util.Objects;

public enum InsertPolicy {
    PACK(new PackStrategy()),
    PLAIN(new PlainStrategy()),
    UNCLOSE(new UncloseStrategy());

    private final Action<NamedPipeline, NamedPipeline> action;

    InsertPolicy(Action<NamedPipeline, NamedPipeline> action) {
        this.action = Objects.requireNonNull(action);
    }

    public NamedPipeline execute(NamedPipeline pipeline) throws Exception {
        return action.execute(pipeline);
    }
}
