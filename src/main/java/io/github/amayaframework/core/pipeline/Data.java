package io.github.amayaframework.core.pipeline;

abstract class Data implements CompletableData {
    protected boolean finalized = false;

    protected void checkFinalized() {
        if (finalized) {
            throw new IllegalStateException("The data has already been finalized");
        }
    }

    @Override
    public void complete() {
        finalized = true;
    }

    @Override
    public boolean isCompleted() {
        return finalized;
    }
}
