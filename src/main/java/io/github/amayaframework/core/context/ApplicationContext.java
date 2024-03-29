package io.github.amayaframework.core.context;

import io.github.amayaframework.core.environment.Environment;
import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.events.EventManager;

public interface ApplicationContext extends Context<Request, Response> {

    Environment getEnvironment();

    EventManager getEventManager();

    ServiceProvider getServiceProvider();
}
