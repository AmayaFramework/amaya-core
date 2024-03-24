package io.github.amayaframework.core;

import com.github.romanqed.jconv.Task;
import io.github.amayaframework.core.configuration.Configuration;
import io.github.amayaframework.core.context.ApplicationContext;
import io.github.amayaframework.core.service.ServiceManager;
import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.events.EventManager;
import org.slf4j.Logger;

public interface Application extends ServiceManager, AutoCloseable {

    Logger getLogger();

    Configuration getConfiguration();

    EventManager getEventManager();

    Environment getEnvironment();

    ServiceProvider getServiceProvider();

    Task<ApplicationContext> getHandler();

    void setHandler(Task<ApplicationContext> handler);

    void shutdown() throws InterruptedException;
}
