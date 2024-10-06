/**
 * Amaya Application Module
 *
 * @author Roman Bakaldin
 */
module io.github.amayaframework.application {
    // Imports
    requires com.github.romanqed.jfunc;
    requires com.github.romanqed.jconv;
    requires io.github.amayaframework.options;
    requires io.github.amayaframework.environment;
    requires io.github.amayaframework.service;
    // Optional imports
    requires static io.github.amayaframework.di;
    // Exports
    exports io.github.amayaframework.application;
}
