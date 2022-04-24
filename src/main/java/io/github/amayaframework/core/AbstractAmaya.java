package io.github.amayaframework.core;

import io.github.amayaframework.core.handlers.Event;
import io.github.amayaframework.core.handlers.EventManager;

import java.util.Objects;

public abstract class AbstractAmaya<T> implements Amaya<T> {
    private final EventManager manager;

    protected AbstractAmaya(EventManager manager) {
        this.manager = Objects.requireNonNull(manager);
    }

    @Override
    public EventManager getEventManager() {
        return manager;
    }

    @Override
    public void start() throws Throwable {
        manager.callEvent(Event.SERVER_STARTING, getServer());
    }

    @Override
    public void close() throws Exception {
        manager.callEvent(Event.SERVER_CLOSING, getServer());
        manager.close();
    }
}
