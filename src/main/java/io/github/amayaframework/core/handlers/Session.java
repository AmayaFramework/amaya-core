package io.github.amayaframework.core.handlers;

import com.github.romanqed.jutils.http.HttpCode;
import com.github.romanqed.jutils.lambdas.Action;
import io.github.amayaframework.core.contexts.HttpResponse;

import java.io.IOException;

/**
 * An interface representing session, which must be implemented for a specific server.
 */
public interface Session {
    HttpResponse handleInput(Action<Object, Object> handler) throws Throwable;

    void handleOutput(Action<Object, Object> handler, HttpResponse response) throws Throwable;

    void reject(Throwable e) throws IOException;

    void reject(HttpCode code, String message) throws IOException;
}
