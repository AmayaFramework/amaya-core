package io.github.amayaframework.core.contexts;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public abstract class FixedOutputStream extends OutputStream {
    private final OutputStream stream;

    public FixedOutputStream(OutputStream stream) {
        this.stream = Objects.requireNonNull(stream);
    }

    public abstract void sendLength(long length);

    @Override
    public void write(byte[] b) throws IOException {
        stream.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        stream.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        stream.flush();
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }

    @Override
    public void write(int b) throws IOException {
        stream.write(b);
    }
}
