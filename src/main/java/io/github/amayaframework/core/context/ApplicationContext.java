package io.github.amayaframework.core.context;

import io.github.amayaframework.core.Environment;
import io.github.amayaframework.di.ServiceProvider;

public interface ApplicationContext extends Context<Request, Response> {

    Environment getEnvironment();

    ServiceProvider getServiceProvider();
}
