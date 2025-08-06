module io.github.amayaframework.core {
    // Imports
    // Basic
    requires com.github.romanqed.jfunc;
    requires com.github.romanqed.juni;
    requires com.github.romanqed.jconv;
    requires com.github.romanqed.jct;
    // Logger
    requires static org.slf4j;
    // Amaya modules
    requires io.github.amayaframework.http;
    requires io.github.amayaframework.options;
    requires io.github.amayaframework.environment;
    requires io.github.amayaframework.service;
    requires io.github.amayaframework.context;
    requires io.github.amayaframework.server;
    requires io.github.amayaframework.application;
    requires io.github.amayaframework.web;
    // Optional modules
    requires static io.github.amayaframework.di;
    requires static io.github.amayaframework.di.stub;
    requires com.github.romanqed.jtype;
    // Exports
    exports io.github.amayaframework.core;
}
