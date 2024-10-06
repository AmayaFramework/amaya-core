package io.github.amayaframework.web;

import io.github.amayaframework.application.ApplicationBuilderFactory;
import io.github.amayaframework.options.GroupOptionSet;

/**
 * An interface describing an abstract {@link WebApplicationBuilder} factory.
 */
public interface WebBuilderFactory extends ApplicationBuilderFactory<WebApplication> {

    @Override
    WebApplicationBuilder create(GroupOptionSet options);

    @Override
    WebApplicationBuilder create();
}
