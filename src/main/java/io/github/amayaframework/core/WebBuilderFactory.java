package io.github.amayaframework.core;

import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.web.WebApplicationBuilder;

public interface WebBuilderFactory {

    WebApplicationBuilder create(GroupOptionSet options);

    WebApplicationBuilder create();
}
