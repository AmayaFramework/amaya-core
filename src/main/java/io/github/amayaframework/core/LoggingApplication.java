package io.github.amayaframework.core;

import com.github.romanqed.jconv.TaskBuilder;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.di.core.ServiceProvider;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.service.ServiceManager;
import org.slf4j.Logger;

final class LoggingApplication extends AbstractLoggingApplication {
    private final ServiceProvider provider;

    LoggingApplication(GroupOptionSet options,
                       Environment environment,
                       ServiceManager manager,
                       TaskBuilder<HttpContext> builder,
                       HttpServer server,
                       ServiceProvider provider,
                       Logger logger) {
        super(options, environment, manager, builder, server, logger);
        this.provider = provider;
    }

    @Override
    protected void doAppDispose() {
        super.doAppDispose();
        provider.close();
    }

    @Override
    protected void onHalt(Throwable throwable) {
        super.onHalt(throwable);
        provider.close();
    }

    @Override
    public ServiceProvider provider() {
        return provider;
    }
}
