package io.github.amayaframework.server;

import com.github.romanqed.jconv.Task;
import io.github.amayaframework.context.Context;
import io.github.amayaframework.service.Service;

public interface Server<T extends Context> extends Service {

    Task<T> getHandler();

    void setHandler(Task<T> handler);

    @Override
    void start() throws Throwable;

    @Override
    void stop() throws Throwable;
}
