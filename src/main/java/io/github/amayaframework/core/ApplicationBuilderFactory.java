package io.github.amayaframework.core;

import io.github.amayaframework.options.GroupOptionSet;

public interface ApplicationBuilderFactory {

    ApplicationBuilder create(GroupOptionSet options);

    ApplicationBuilder create();
}
