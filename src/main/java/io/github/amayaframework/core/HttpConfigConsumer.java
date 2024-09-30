package io.github.amayaframework.core;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.server.HttpServerConfig;

public interface HttpConfigConsumer extends Runnable1<HttpServerConfig> {

    @Override
    void run(HttpServerConfig config) throws Throwable;
}
