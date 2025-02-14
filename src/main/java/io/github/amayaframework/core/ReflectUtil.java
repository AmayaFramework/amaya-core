package io.github.amayaframework.core;

final class ReflectUtil {
    private static final String AMAYA_DI_MODULE = "io.github.amayaframework.di";
    private static final String AMAYA_SERVICE_PROVIDER = "io.github.amayaframework.di.ServiceProvider";
    private static final String DI_ASM_MODULE = "io.github.amayaframework.di.asm";
    private static final String DI_ASM_FACTORY = "io.github.amayaframework.di.asm.BytecodeStubFactory";
    private static final String DI_REFLECT_MODULE = "io.github.amayaframework.di.reflect";
    private static final String DI_REFLECT_FACTORY = "io.github.amayaframework.di.reflect.ReflectStubFactory";

    private ReflectUtil() {
    }

    static boolean isModuleLoaded(String name) {
        var layer = ModuleLayer.boot();
        return layer.findModule(name).isPresent();
    }

    static boolean isClassExists(String name) {
        var loader = Thread.currentThread().getContextClassLoader();
        try {
            var loaded = loader.loadClass(name);
            return loaded.getName().equals(name);
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

    static boolean isDILoaded() {
        return isLibraryLoaded(AMAYA_DI_MODULE, AMAYA_SERVICE_PROVIDER);
    }

    static Class<?> lookupStubFactoryType() {
        var clazz = tryLoadLibrary(DI_ASM_MODULE, DI_ASM_FACTORY);
        if (clazz != null) {
            return clazz;
        }
        return tryLoadLibrary(DI_REFLECT_MODULE, DI_REFLECT_FACTORY);
    }

    static Object lookupStubFactory() {
        var clazz = lookupStubFactoryType();
        if (clazz == null) {
            throw new UnsupportedOperationException("No amaya di stub factory modules found");
        }
        try {
            return clazz.getConstructor().newInstance((Object[]) null);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
