module io.github.amayaframework.server {
    // Imports
    // Base dependencies
    requires com.github.romanqed.jfunc;
    requires com.github.romanqed.juni;
    requires com.github.romanqed.jct;
    // Servlets
    requires jakarta.servlet;
    // Amaya modules
    requires io.github.amayaframework.http;
    requires io.github.amayaframework.options;
    requires io.github.amayaframework.service;
    requires io.github.amayaframework.context;
    requires static io.github.amayaframework.environment;
    // Exports
    exports io.github.amayaframework.server;
}
