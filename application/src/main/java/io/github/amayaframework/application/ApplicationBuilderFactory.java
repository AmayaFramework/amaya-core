package io.github.amayaframework.application;

import io.github.amayaframework.options.GroupOptionSet;

/**
 *
 * @param <T>
 */
public interface ApplicationBuilderFactory<T> {

    /**
     *
     * @param options
     * @return
     */
    ApplicationBuilder<T> create(GroupOptionSet options);

    /**
     *
     * @return
     */
    ApplicationBuilder<T> create();
}
