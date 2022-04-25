package io.github.amayaframework.core.configurators;

import com.github.romanqed.util.Action;
import io.github.amayaframework.core.pipeline.NamedPipeline;

/**
 * An enumeration containing the available insert policies
 */
public enum InsertPolicy {
    /**
     * A policy that will package your actions into a pipeline and insert it as a single action.
     */
    PACK(new PackStrategy()),

    /**
     * A policy that simply sequentially inserts your actions.
     */
    PLAIN(new PlainStrategy()),

    /**
     * A policy that will recursively unclose nested pipelines until all actions are combined into one.
     */
    UNCLOSE(new UncloseStrategy());

    private final Action<NamedPipeline, NamedPipeline> action;

    InsertPolicy(Action<NamedPipeline, NamedPipeline> action) {
        this.action = action;
    }

    public NamedPipeline execute(NamedPipeline pipeline) throws Throwable {
        return action.execute(pipeline);
    }
}
