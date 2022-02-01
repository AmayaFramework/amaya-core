package io.github.amayaframework.core.util;


import com.github.romanqed.jutils.util.Pair;

public class Variable<K, V> extends Pair<K, V> {
    public Variable(K key, V value) {
        super(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Variable)) {
            return false;
        }
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
