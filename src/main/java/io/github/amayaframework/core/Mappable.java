package io.github.amayaframework.core;

import java.util.Map;

public interface Mappable<K, V> extends Streamable<Map.Entry<K, V>> {

    Map<K, V> map();
}
