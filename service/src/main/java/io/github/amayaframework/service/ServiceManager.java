package io.github.amayaframework.service;

import com.github.romanqed.jfunc.Runnable1;

import java.util.Collection;

public interface ServiceManager extends Service {

    void add(Service service);

    void remove(Service service);

    Collection<Service> services();

    Runnable1<Throwable> onFailure();

    void onFailure(Runnable1<Throwable> action);

    Runnable1<Throwable> onHalt();

    void onHalt(Runnable1<Throwable> action);
}
