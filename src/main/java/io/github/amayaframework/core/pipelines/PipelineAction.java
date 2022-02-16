package io.github.amayaframework.core.pipelines;

import com.github.romanqed.jutils.pipeline.InterruptException;
import com.github.romanqed.jutils.util.Action;

/**
 * <p>An abstract class containing supporting code for implementing an action.
 * All custom pipeline actions should inherit from this class.</p>
 * <p>@param {@link T} type of receiving parameter</p>
 * <p>@param {@link R} type of parameter to be returned</p>
 */
public abstract class PipelineAction<T, R> implements Action<T, R> {
    /**
     * <p>A method that allows you to break the pipeline with the given result.</p>
     * <p>Note: Java does not recognize it as return or throw, so in some cases it is necessary to make an
     * additional return after the call, but this code will never be executed, since execution will
     * terminate immediately after calling this method.</p>
     *
     * @param ret {@link Object} the object to be returned
     */
    protected void interrupt(Object ret) {
        throw new InterruptException(ret);
    }
}
