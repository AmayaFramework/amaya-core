package io.github.amayaframework.core;

import io.github.amayaframework.options.GroupOptionSet;

public interface OptionSetBuilder {

    OptionSetBuilder setFactory(OptionSetFactory factory);

    OptionSetBuilder addHandler(OptionSetHandler action);

    OptionSetBuilder set(String key, Object value);

    OptionSetBuilder remove(String key);

    GroupOptionSet build();
}
