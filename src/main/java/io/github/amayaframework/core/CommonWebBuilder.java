package io.github.amayaframework.core;

import com.github.romanqed.jconv.TaskBuilders;
import io.github.amayaframework.application.ServicesConfigurer;
import io.github.amayaframework.di.core.ServiceProvider;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.environment.EnvironmentFactory;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.options.OpenOptionSet;
import io.github.amayaframework.options.ProvidedGroupSet;
import io.github.amayaframework.server.HttpServer;
import io.github.amayaframework.service.ServiceManager;
import io.github.amayaframework.web.AbstractWebBuilder;
import io.github.amayaframework.web.WebApplication;
import org.slf4j.ILoggerFactory;

public abstract class CommonWebBuilder extends AbstractWebBuilder {
    protected final ILoggerFactory loggerFactory;

    protected CommonWebBuilder(ServicesConfigurer configurer,
                     EnvironmentFactory defaultEnvironmentFactory,
                     ILoggerFactory loggerFactory) {
        super(configurer, defaultEnvironmentFactory);
        this.loggerFactory = loggerFactory;
    }

    @Override
    protected GroupOptionSet createDefaultOptions() {
        return new ProvidedGroupSet(OpenOptionSet::new);
    }

    protected WebApplication createApplication(GroupOptionSet options,
                                               Environment env,
                                               ServiceManager manager,
                                               ServiceProvider provider,
                                               HttpServer server) {
        if (loggerFactory != null) {
            var logger = loggerFactory.getLogger("WebApplication");
            return new LoggingApplication(options, env, manager, TaskBuilders.linked(), server, provider, logger);
        }
        return new PlainApplication(options, env, manager, TaskBuilders.linked(), server, provider);
    }
}
