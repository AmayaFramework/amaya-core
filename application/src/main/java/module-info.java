module io.github.amayaframework.application {
    // Imports
    // Base dependencies
    requires com.github.romanqed.jfunc;
    requires com.github.romanqed.jct;
    requires com.github.romanqed.jconv;
    // Amaya modules
    requires io.github.amayaframework.options;
    requires io.github.amayaframework.environment;
    requires io.github.amayaframework.service;
    // Optional modules
    requires static io.github.amayaframework.di;
    // Exports
    exports io.github.amayaframework.application;
}
