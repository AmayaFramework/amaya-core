package io.github.amayaframework.core.util;

public abstract class AbstractCompletableData implements CompletableData {
    protected boolean completed = false;

    protected void checkCompleted() {
        if (completed) {
            throw new IllegalStateException("The data has already been finalized");
        }
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }
}
