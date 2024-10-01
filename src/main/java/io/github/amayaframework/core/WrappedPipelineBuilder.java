package io.github.amayaframework.core;

import com.github.romanqed.jconv.PipelineBuilder;
import com.github.romanqed.jconv.Task;
import com.github.romanqed.jfunc.Runnable2;

final class WrappedPipelineBuilder<T> implements PipelineBuilder<T> {
    private final PipelineBuilder<T> builder;

    WrappedPipelineBuilder(PipelineBuilder<T> builder) {
        this.builder = builder;
    }

    @Override
    public PipelineBuilder<T> add(Runnable2<T, Task<T>> action) {
        builder.add(action);
        return this;
    }

    @Override
    public PipelineBuilder<T> remove() {
        builder.remove();
        return this;
    }

    @Override
    public PipelineBuilder<T> clear() {
        builder.clear();
        return this;
    }

    @Override
    public Task<T> build() {
        throw new UnsupportedOperationException("The builder is managed by the application builder");
    }
}
