package io.github.amayaframework.core.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * A class describing a standard event manager.
 */
public class EventManager implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventManager.class);
    private final Map<Event, Consumer<Object>> events;
    private final ExecutorService executor;
    private final boolean isDebug;

    public EventManager(ExecutorService executor, boolean isDebug) {
        this.executor = Objects.requireNonNull(executor);
        this.events = new ConcurrentHashMap<>();
        this.isDebug = isDebug;
    }

    public EventManager() {
        this(Executors.newWorkStealingPool(), false);
    }

    public void callEvent(Event event, Object body) {
        final Consumer<Object> found = events.get(event);
        if (found == null) {
            return;
        }
        executor.submit(() -> {
            if (isDebug) {
                LOGGER.debug("Event " + event + "is called");
            }
            found.accept(body);
        });
    }

    @SuppressWarnings("unchecked")
    public <T> void addEvent(Event event, Consumer<?> body) {
        Consumer<Object> found = events.get(event);
        Consumer<Object> toPut;
        if (found != null) {
            toPut = found.andThen((Consumer<Object>) body);
        } else {
            toPut = (Consumer<Object>) body;
        }
        events.put(event, toPut);
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    @Override
    public void close() {
        executor.shutdown();
    }
}
