package io.github.amayaframework.core.handlers;

import com.github.romanqed.util.Action;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.util.Completable;
import io.github.amayaframework.http.HttpCode;

import java.io.IOException;

/**
 * An interface representing session, which must be implemented for a specific server.
 */
public interface Session extends Completable {
    HttpResponse handleInput(Action<Object, Object> handler) throws Throwable;

    void handleOutput(Action<Object, Object> handler, HttpResponse response) throws Throwable;

    void reject(Throwable e) throws IOException;

    void reject(HttpCode code, String message) throws IOException;
}
