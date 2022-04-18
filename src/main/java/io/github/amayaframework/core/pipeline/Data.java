package io.github.amayaframework.core.pipeline;

abstract class Data implements CompletableData {
    protected boolean completed = false;

    protected void checkFinalized() {
        if (completed) {
            throw new IllegalStateException("The data has already been finalized");
        }
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }
}
