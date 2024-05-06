open module io.github.amayaframework.core {
    // Imports
    requires org.slf4j;
    requires io.github.amayaframework.di;
    requires io.github.amayaframework.events;
    requires com.google.gson;
    requires com.github.romanqed.jconv;
    requires com.github.romanqed.jfunc;
    requires com.github.romanqed.jtype;
    // Exports
    exports io.github.amayaframework.core;
    exports io.github.amayaframework.core.builder;
    exports io.github.amayaframework.core.configuration;
    exports io.github.amayaframework.core.context;
    exports io.github.amayaframework.core.service;
    exports io.github.amayaframework.core.util;
}
