package io.github.amayaframework.core;

import io.github.amayaframework.core.OptionSetBuilder;
import io.github.amayaframework.core.OptionSetFactory;
import io.github.amayaframework.core.OptionSetHandler;
import io.github.amayaframework.options.GroupOptionSet;

class WrappedOptionBuilder implements OptionSetBuilder {
    private final OptionSetBuilder builder;

    WrappedOptionBuilder(OptionSetBuilder builder) {
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
    public OptionSetBuilder reset() {
        builder.reset();
        return this;
    }

    @Override
    public GroupOptionSet build() {
        throw new UnsupportedOperationException("The builder is managed by the application builder");
    }
}
