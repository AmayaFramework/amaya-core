package io.github.amayaframework.impl;

import io.github.amayaframework.core.OptionSetBuilder;
import io.github.amayaframework.core.OptionSetFactory;
import io.github.amayaframework.core.OptionSetHandler;
import io.github.amayaframework.options.GroupOptionSet;

class WrappedOptionSetBuilder implements OptionSetBuilder {
    private final OptionSetBuilder builder;

    WrappedOptionSetBuilder(OptionSetBuilder builder) {
        this.builder = builder;
    }

    @Override
    public OptionSetBuilder setFactory(OptionSetFactory factory) {
        builder.setFactory(factory);
        return this;
    }

    @Override
    public OptionSetBuilder addHandler(OptionSetHandler action) {
        builder.addHandler(action);
        return this;
    }

    @Override
    public OptionSetBuilder set(String key, Object value) {
        builder.set(key, value);
        return this;
    }

    @Override
    public OptionSetBuilder remove(String key) {
        builder.remove(key);
        return this;
    }

    @Override
    public GroupOptionSet build() {
        throw new UnsupportedOperationException();
    }
}
