package io.github.amayaframework.service;

import java.util.Collection;
import java.util.function.Consumer;

public interface ServiceManager extends Service {

    void add(Service service);

    void remove(Service service);

    Collection<Service> services();

    Consumer<Throwable> onFailure();

    void onFailure(Consumer<Throwable> action);

    Consumer<Throwable> onHalt();

    void onHalt(Consumer<Throwable> action);
}
