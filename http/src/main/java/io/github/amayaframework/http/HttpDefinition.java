package io.github.amayaframework.http;

public interface HttpDefinition {

    HttpVersion getSince();

    boolean isSupported(HttpVersion version);
}
