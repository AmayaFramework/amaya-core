package io.github.amayaframework.core.contexts;

import java.io.IOException;
import java.io.OutputStream;

public abstract class FixedOutputStream extends OutputStream {
    private final OutputStream stream;
    protected long length = -1;

    public FixedOutputStream(OutputStream stream) {
        this.stream = stream;
    }

    public long getLength() {
        return length;
    }

    private void checkLength(long checked) {
        if (length <= 0) {
            throw new IllegalStateException("Unable to write to stream, available length <= 0");
        }
        if (checked > length) {
            throw new IllegalStateException("Unable to write to stream, data too long");
        }
    }

    public abstract void specifyLength(long length) throws IOException;

    @Override
    public void write(byte[] b) throws IOException {
        checkLength(b.length);
        stream.write(b);
        length -= b.length;
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        checkLength(len);
        stream.write(b, off, len);
        length -= len;
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
        checkLength(1);
        stream.write(b);
        --length;
    }
}
