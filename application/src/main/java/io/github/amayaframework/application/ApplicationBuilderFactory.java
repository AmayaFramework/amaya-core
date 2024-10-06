package io.github.amayaframework.application;

import io.github.amayaframework.options.GroupOptionSet;

public interface ApplicationBuilderFactory<T> {

    ApplicationBuilder<T> create(GroupOptionSet options);

    ApplicationBuilder<T> create();
}
