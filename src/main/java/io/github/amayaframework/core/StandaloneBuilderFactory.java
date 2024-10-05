package io.github.amayaframework.core;

import io.github.amayaframework.options.GroupOptionSet;

public final class StandaloneBuilderFactory implements ApplicationBuilderFactory {

    @Override
    public ApplicationBuilder create(GroupOptionSet options) {
        var ret = create();
        ret.setOptions(options);
        return ret;
    }

    @Override
    public ApplicationBuilder create() {
        var managerBuilder = new StandaloneManagerBuilder(Defaults.DEFAULT_MANAGER_FACTORY, Defaults.DEFAULT_HANDLER);
        return new StandaloneApplicationBuilder(managerBuilder, Defaults.DEFAULT_ENVIRONMENT_FACTORY);
    }
}
