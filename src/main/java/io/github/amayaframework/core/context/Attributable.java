package io.github.amayaframework.core.context;

import io.github.amayaframework.core.Mappable;

public interface Attributable<K> extends Mappable<K, Object> {

    <V> V get(K key);

    void set(K key, Object value);

    <V> V remove(K key);

    boolean contains(K key);

    default String getString(K key) {
        return get(key);
    }

    default boolean getBool(K key) {
        var ret = this.<Boolean>get(key);
        if (ret == null) {
            return false;
        }
        return ret;
    }

    default char getChar(K key) {
        var ret = this.<Character>get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }

    default byte getByte(K key) {
        var ret = this.<Byte>get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }

    default short getShort(K key) {
        var ret = this.<Short>get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }

    default int getInt(K key) {
        var ret = this.<Integer>get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }

    default long getLong(K key) {
        var ret = this.<Long>get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }

    default float getFloat(K key) {
        var ret = this.<Float>get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }

    default double getDouble(K key) {
        var ret = this.<Double>get(key);
        if (ret == null) {
            return 0;
        }
        return ret;
    }
}
