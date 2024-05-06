package io.github.amayaframework.core.util;

import java.util.Map;
import java.util.function.BiConsumer;

public interface Mappable<K, V> extends Streamable<Map.Entry<K, V>> {

    Map<K, V> map();

    void forEach(BiConsumer<? super K, ? super V> consumer);
}
