package io.github.amayaframework.core.pipeline;

interface CompletableData {
    /**
     *
     */
    void complete();

    /**
     * @return
     */
    boolean isCompleted();
}
