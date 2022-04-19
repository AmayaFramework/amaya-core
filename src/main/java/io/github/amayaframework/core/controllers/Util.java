package io.github.amayaframework.core.controllers;

import io.github.amayaframework.core.wrapping.Packer;

import java.lang.reflect.AnnotatedElement;

public final class Util {
    public static Packer extractPacker(AnnotatedElement annotated) {
        Pack pack = annotated.getAnnotation(Pack.class);
        if (pack == null) {
            return null;
        }
        Class<? extends Packer> type = pack.value();
        try {
            return type.newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
