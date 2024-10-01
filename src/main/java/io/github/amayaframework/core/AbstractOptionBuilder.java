package io.github.amayaframework.core;

import io.github.amayaframework.options.GroupOptionSet;

import java.util.*;

public abstract class AbstractOptionBuilder implements OptionSetBuilder {
    protected OptionSetFactory factory;
    protected List<OptionSetHandler> handlers;
    protected Map<String, Object> options;

    protected void innerReset() {
        this.factory = null;
        this.handlers = null;
        this.options = null;
    }

    @Override
    public OptionSetBuilder setFactory(OptionSetFactory factory) {
        this.factory = factory;
        return this;
    }

    @Override
    public OptionSetBuilder addHandler(OptionSetHandler action) {
        Objects.requireNonNull(action);
        if (handlers == null) {
            handlers = new LinkedList<>();
        }
        handlers.add(action);
        return this;
    }

    @Override
    public OptionSetBuilder set(String key, Object value) {
        Objects.requireNonNull(key);
        if (options == null) {
            options = new HashMap<>();
        }
        options.put(key, value);
        return this;
    }

    @Override
    public OptionSetBuilder remove(String key) {
        if (options == null) {
            return this;
        }
        options.remove(key);
        return this;
    }

    @Override
    public OptionSetBuilder reset() {
        this.innerReset();
        return this;
    }

    protected abstract GroupOptionSet createDefault();

    private GroupOptionSet create() {
        if (factory == null) {
            return createDefault();
        }
        return factory.create();
    }

    protected GroupOptionSet checkedBuild() throws Throwable {
        var ret = create();
        if (options != null) {
            options.forEach(ret::set);
        }
        if (handlers == null) {
            return ret;
        }
        for (var handler : handlers) {
            handler.run(ret);
        }
        return ret;
    }

    @Override
    public GroupOptionSet build() {
        try {
            return checkedBuild();
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            innerReset();
        }
    }
}
