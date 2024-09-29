package io.github.amayaframework.server;

import io.github.amayaframework.context.Context;
import io.github.amayaframework.options.OptionSet;

public interface ServerFactory<T extends Context> {

    Server<T> create(OptionSet set);

    Server<T> create();
}
