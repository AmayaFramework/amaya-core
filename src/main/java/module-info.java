open module io.github.amayaframework.core {
    // Imports
    requires com.github.romanqed.jfunc;
    requires com.github.romanqed.jconv;
    requires io.github.amayaframework.options;
    requires io.github.amayaframework.environment;
    requires io.github.amayaframework.service;
    requires io.github.amayaframework.context;
    requires io.github.amayaframework.server;
    // Optional imports
    requires static io.github.amayaframework.di;
    // Exports
    exports io.github.amayaframework.core;
}
