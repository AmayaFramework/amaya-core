package io.github.amayaframework.core;

final class LookupUtil {
    private LookupUtil() {
    }

    // Amaya DI
    private static final String AMAYA_DI_MODULE = "amayaframework.di";
    private static final String AMAYA_SERVICE_PROVIDER = "io.github.amayaframework.di.core.ServiceProvider";
    // Amaya DI ASM
    private static final String DI_ASM_FACTORY = "io.github.amayaframework.di.asm.AsmStubFactory";
    // Amaya DI Reflect
    private static final String DI_REFLECT_FACTORY = "io.github.amayaframework.di.reflect.ReflectStubFactory";
    // SLF4J
    private static final String SLF4J_MODULE = "org.slf4j";
    private static final String SLF4J_LOGGER = "org.slf4j.Logger";

    static boolean isModuleLoaded(String name) {
        var layer = ModuleLayer.boot();
        return layer.findModule(name).isPresent();
    }

    static boolean isClassExists(String name) {
        var loader = Thread.currentThread().getContextClassLoader();
        try {
            loader.loadClass(name);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    static boolean isLibraryLoaded(String module, String type) {
        return isModuleLoaded(module) || isClassExists(type);
    }

    static Class<?> loadClass(String name) {
        var loader = Thread.currentThread().getContextClassLoader();
        try {
            return loader.loadClass(name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    static boolean isDiLoaded() {
        return isLibraryLoaded(AMAYA_DI_MODULE, AMAYA_SERVICE_PROVIDER);
    }

    static boolean isSlf4jLoaded() {
        return isLibraryLoaded(SLF4J_MODULE, SLF4J_LOGGER);
    }

    static Class<?> lookupStubFactory() {
        var clazz = loadClass(DI_ASM_FACTORY);
        if (clazz != null) {
            return clazz;
        }
        return loadClass(DI_REFLECT_FACTORY);
    }
}
