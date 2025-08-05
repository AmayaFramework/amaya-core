package io.github.amayaframework.core;

import com.github.romanqed.jconv.TaskBuilder;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.di.core.ServiceProvider;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.service.ServiceManager;
import io.github.amayaframework.web.AbstractWebApplication;

final class PlainApplication extends AbstractWebApplication {
    private final ServiceProvider provider;

    PlainApplication(GroupOptionSet options,
                            Environment environment,
                            ServiceManager manager,
                            TaskBuilder<HttpContext> builder,
                            HttpServer server,
                            ServiceProvider provider) {
        super(options, environment, manager, builder, server);
        this.provider = provider;
    }

    @Override
    public ServiceProvider provider() {
        return provider;
    }
}
