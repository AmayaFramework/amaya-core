package io.github.amayaframework.core.handlers;

import com.github.romanqed.jutils.http.HttpCode;
import com.github.romanqed.jutils.pipeline.PipelineResult;
import io.github.amayaframework.core.contexts.HttpResponse;

import java.io.IOException;

public interface SourceWrapper<T> {
    void sendStringResponse(T source, HttpResponse response) throws IOException;

    void sendStreamResponse(T source, HttpResponse response) throws IOException;

    void reject(T source, Exception e) throws IOException;

    void reject(T source, HttpCode code, String message) throws IOException;

    void reject(T source, HttpResponse response) throws IOException;

    void process(T source, PipelineResult processResult) throws IOException;
}
