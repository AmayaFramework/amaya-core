/**
 * Amaya Web Application Module
 *
 * @author Roman Bakaldin
 */
module io.github.amayaframework.web {
    // Requires
    // Basic
    requires com.github.romanqed.jfunc;
    requires com.github.romanqed.jconv;
    requires com.github.romanqed.jct;
    // Amaya modules
    requires io.github.amayaframework.http;
    requires io.github.amayaframework.options;
    requires io.github.amayaframework.environment;
    requires io.github.amayaframework.service;
    requires io.github.amayaframework.server;
    requires io.github.amayaframework.application;
    requires io.github.amayaframework.context;
    // Optional modules
    requires static io.github.amayaframework.di;
    // Exports
    exports io.github.amayaframework.web;
}
