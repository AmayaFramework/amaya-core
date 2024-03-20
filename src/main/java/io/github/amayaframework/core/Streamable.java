package io.github.amayaframework.core;

import java.util.stream.Stream;

public interface Streamable<T> extends Iterable<T> {

    Stream<T> stream();

    Stream<T> parallelStream();
}
