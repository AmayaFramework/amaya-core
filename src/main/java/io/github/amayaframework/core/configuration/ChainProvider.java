package io.github.amayaframework.core.configuration;

import java.util.Objects;

public final class ChainProvider implements KeyProvider {
    private final KeyProvider provider;
    private KeyProvider next;

    public ChainProvider(KeyProvider provider, KeyProvider next) {
        this.provider = Objects.requireNonNull(provider);
        this.next = next;
    }

    public ChainProvider(KeyProvider provider) {
        this(provider, null);
    }

    public void setNext(KeyProvider next) {
        this.next = next;
    }

    @Override
    public Object invoke(Key key) throws Throwable {
        var ret = provider.invoke(key);
        if (ret != null) {
            return ret;
        }
        if (next == null) {
            return null;
        }
        return next.invoke(key);
    }
}
