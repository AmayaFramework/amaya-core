package io.github.amayaframework.core.pipeline;

import com.github.romanqed.jutils.pipeline.ArrayPipeline;
import com.github.romanqed.jutils.pipeline.Pipeline;
import com.github.romanqed.jutils.util.Action;
import com.github.romanqed.jutils.util.Node;

import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class NamedPipeline implements Pipeline<String> {
    private final Pipeline<String> body;

    public NamedPipeline(Pipeline<String> body) {
        this.body = Objects.requireNonNull(body);
    }

    public NamedPipeline() {
        this.body = new ArrayPipeline<>();
    }

    @Override
    public Action<?, ?> get(String key) {
        return body.get(key);
    }

    @Override
    public Action<?, ?> put(String key, Action<?, ?> value) {
        return body.put(key, value);
    }

    public Action<?, ?> put(PipelineAction<?, ?> value) {
        return body.put(value.getName(), value);
    }

    @Override
    public Action<?, ?> remove(String key) {
        return body.remove(key);
    }

    @Override
    public boolean contains(String key) {
        return body.contains(key);
    }

    @Override
    public void insertAfter(String key, String insertKey, Action<?, ?> value) {
        body.insertAfter(key, insertKey, value);
    }

    public void insertAfter(String key, PipelineAction<?, ?> value) {
        body.insertAfter(key, value.getName(), value);
    }

    @Override
    public void insertBefore(String key, String insertKey, Action<?, ?> value) {
        body.insertBefore(key, insertKey, value);
    }

    public void insertBefore(String key, PipelineAction<?, ?> value) {
        body.insertBefore(key, value.getName(), value);
    }

    @Override
    public void clear() {
        body.clear();
    }

    @Override
    public int size() {
        return body.size();
    }

    @Override
    public boolean isEmpty() {
        return body.isEmpty();
    }

    @Override
    public Object execute(Object o) throws Exception {
        return body.execute(o);
    }

    @Override
    public CompletableFuture<Object> async(Object o) {
        return body.async(o);
    }

    @Override
    public Iterator<Node<String, Action<Object, Object>>> iterator() {
        return body.iterator();
    }

    @Override
    public String toString() {
        return body.toString();
    }
}
