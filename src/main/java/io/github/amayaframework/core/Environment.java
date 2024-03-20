package io.github.amayaframework.core;

import io.github.amayaframework.core.configuration.Configuration;
import io.github.amayaframework.events.EventManager;
import org.slf4j.Logger;

public interface Environment {

    Logger getLogger();

    Configuration getConfiguration();

    EventManager getEventManager();
}
