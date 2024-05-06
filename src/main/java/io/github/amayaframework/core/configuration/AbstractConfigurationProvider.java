package io.github.amayaframework.core.configuration;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractConfigurationProvider implements ConfigurationBuilder {
    protected final Set<KeyProvider> providers;
    protected Scheme scheme;

    protected AbstractConfigurationProvider() {
        this.providers = new LinkedHashSet<>();
    }

    protected abstract Configuration checkedBuild() throws Throwable;

    protected void reset() {
        this.providers.clear();
        this.scheme = null;
    }

    @Override
    public ConfigurationBuilder setScheme(Scheme scheme) {
        this.scheme = scheme;
        return this;
    }

    @Override
    public ConfigurationBuilder addProvider(KeyProvider provider) {
        Objects.requireNonNull(provider);
        providers.add(provider);
        return this;
    }

    @Override
    public ConfigurationBuilder removeProvider(KeyProvider provider) {
        Objects.requireNonNull(provider);
        providers.remove(provider);
        return this;
    }

    @Override
    public Configuration build() {
        try {
            var ret = checkedBuild();
            reset();
            return ret;
        } catch (Error | RuntimeException e) {
            reset();
            throw e;
        } catch (Throwable e) {
            reset();
            throw new RuntimeException(e);
        }
    }
}
