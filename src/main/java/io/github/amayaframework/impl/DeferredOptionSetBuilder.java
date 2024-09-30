package io.github.amayaframework.impl;

import io.github.amayaframework.core.OptionSetBuilder;
import io.github.amayaframework.core.OptionSetFactory;
import io.github.amayaframework.core.OptionSetHandler;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.options.Options;

import java.util.*;

final class DeferredOptionSetBuilder implements OptionSetBuilder {
    private OptionSetFactory factory;
    private List<OptionSetHandler> handlers;
    private Map<String, Object> options;

    DeferredOptionSetBuilder() {
        this.reset();
    }

    private void reset() {
        this.factory = null;
        this.handlers = new LinkedList<>();
        this.options = new HashMap<>();
    }

    @Override
    public OptionSetBuilder setFactory(OptionSetFactory factory) {
        this.factory = Objects.requireNonNull(factory);
        return this;
    }

    @Override
    public OptionSetBuilder addHandler(OptionSetHandler action) {
        handlers.add(Objects.requireNonNull(action));
        return this;
    }

    @Override
    public OptionSetBuilder set(String key, Object value) {
        options.put(Objects.requireNonNull(key), value);
        return this;
    }

    @Override
    public OptionSetBuilder remove(String key) {
        options.remove(key);
        return this;
    }

    private GroupOptionSet create() {
        if (factory == null) {
            return Options.createGrouped();
        }
        return factory.create();
    }

    private GroupOptionSet uncheckedBuild() throws Throwable {
        var ret = create();
        options.forEach(ret::set);
        for (var handler : handlers) {
            handler.run(ret);
        }
        return ret;
    }

    @Override
    public GroupOptionSet build() {
        try {
            return uncheckedBuild();
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            this.reset();
        }
    }
}
