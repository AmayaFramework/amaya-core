package io.github.amayaframework.core;

final class LookupUtil {
    // Amaya DI
    private static final String AMAYA_DI_MODULE = "io.github.amayaframework.di";
    private static final String AMAYA_SERVICE_PROVIDER = "io.github.amayaframework.di.core.ServiceProvider";
    // Amaya DI ASM
    private static final String DI_ASM_MODULE = "io.github.amayaframework.di.asm";
    private static final String DI_ASM_FACTORY = "io.github.amayaframework.di.asm.AsmStubFactory";
    // Amaya DI Reflect
    private static final String DI_REFLECT_MODULE = "io.github.amayaframework.di.reflect";
    private static final String DI_REFLECT_FACTORY = "io.github.amayaframework.di.reflect.ReflectStubFactory";
    // SLF4J
    private static final String SLF4J_MODULE = "org.slf4j";
    private static final String SLF4J_LOGGER = "org.slf4j.Logger";

    private LookupUtil() {
    }

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

    static Class<?> tryLoadLibrary(String module, String type) {
        if (!isModuleLoaded(module)) {
            return null;
        }
        return loadClass(type);
    }

    static boolean isDiLoaded() {
        return isLibraryLoaded(AMAYA_DI_MODULE, AMAYA_SERVICE_PROVIDER);
    }

    static boolean isSlf4jLoaded() {
        return isLibraryLoaded(SLF4J_MODULE, SLF4J_LOGGER);
    }

    static Class<?> lookupStubFactory() {
        var clazz = tryLoadLibrary(DI_ASM_MODULE, DI_ASM_FACTORY);
        if (clazz != null) {
            return clazz;
        }
        return tryLoadLibrary(DI_REFLECT_MODULE, DI_REFLECT_FACTORY);
    }
}
