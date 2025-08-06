package io.github.amayaframework.core;

import io.github.amayaframework.application.ServicesConfigurer;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.OptionSet;
import io.github.amayaframework.service.ServiceManager;

interface ServicesBuilder extends ServicesConfigurer {

    ServiceManager build(OptionSet options, Environment env);
}
