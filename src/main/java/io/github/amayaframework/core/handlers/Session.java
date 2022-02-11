package io.github.amayaframework.core.handlers;

import com.github.romanqed.jutils.http.HttpCode;
import com.github.romanqed.jutils.util.Action;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.pipelines.RequestData;

import java.io.IOException;

public interface Session {
    RequestData handleInput(Action<Object, Object> handler) throws Exception;

    void handleOutput(Action<Object, Object> handler, HttpResponse response) throws Exception;

    void reject(Exception e) throws IOException;

    void reject(HttpCode code, String message) throws IOException;
}
