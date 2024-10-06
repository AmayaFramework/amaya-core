package io.github.amayaframework.core;

import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.web.WebApplicationBuilder;
import io.github.amayaframework.web.WebBuilderFactory;

/**
 * A factory implementation that creates standalone web applications (without integration with amaya di module).
 */
public final class StandaloneBuilderFactory implements WebBuilderFactory {

    @Override
    public WebApplicationBuilder create(GroupOptionSet options) {
        var ret = create();
        ret.setOptions(options);
        return ret;
    }

    @Override
    public WebApplicationBuilder create() {
        var managerBuilder = new StandaloneManagerBuilder(Defaults.DEFAULT_MANAGER_FACTORY, Defaults.DEFAULT_HANDLER);
        return new StandaloneApplicationBuilder(managerBuilder, Defaults.DEFAULT_ENVIRONMENT_FACTORY);
    }
}
