package io.github.amayaframework.core.service;

import com.github.romanqed.jfunc.Exceptions;

public final class ParallelServiceManager extends AbstractServiceManager {

    public ParallelServiceManager(ServiceCollection collection, ServiceHandler handler) {
        super(collection, handler);
    }

    @Override
    protected void checkedStart() throws Throwable {
        collection.parallelStream().forEach(service -> Exceptions.suppress(() -> handler.start(service)));
    }

    @Override
    protected void checkedStop() throws Throwable {
        collection.parallelStream().forEach(service -> Exceptions.suppress(() -> handler.stop(service)));
    }
}
