package io.github.amayaframework.core;

import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.web.WebApplicationBuilder;

/**
 * Factory interface for creating instances of {@link WebApplicationBuilder}.
 */
public interface WebBuilderFactory {

    /**
     * Creates a new {@link WebApplicationBuilder} with given options pre-applied.
     *
     * @param options the group option set to initialize builder with, must be non-null
     * @return a new configured {@link WebApplicationBuilder} instance
     */
    WebApplicationBuilder create(GroupOptionSet options);

    /**
     * Creates a new {@link WebApplicationBuilder} with default settings.
     *
     * @return a new default {@link WebApplicationBuilder} instance
     */
    WebApplicationBuilder create();
}
