module amayaframework.application {
    // Imports
    // Base dependencies
    requires com.github.romanqed.jfunc;
    requires com.github.romanqed.jct;
    requires com.github.romanqed.jconv;
    // Amaya modules
    requires amayaframework.options;
    requires amayaframework.environment;
    requires amayaframework.service;
    // Optional modules
    requires static amayaframework.di;
    // Exports
    exports io.github.amayaframework.application;
}
