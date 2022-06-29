package io.github.amayaframework.core.contexts;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A class representing a thread used by the framework with a pre-known size.
 */
public class FixedOutputStream extends OutputStream {
    private final OutputStream stream;
    private final Object lock = new Object();
    private volatile Long length;

    public FixedOutputStream(OutputStream stream) {
        this.stream = stream;
    }

    /**
     * @return the length of an unwritten data chunk, if the length is not specified yet, -1
     */
    public long getRemainingLength() {
        return length == null ? -1 : length;
    }

    private void checkLength(long checked) {
        if (length == null) {
            throw new IllegalStateException("Unable to write to stream, the length is not specified");
        }
        if (length <= 0) {
            throw new IllegalStateException("Unable to write to stream, available length <= 0");
        }
        if (checked > length) {
            throw new IllegalStateException("Unable to write to stream, the data is too long");
        }
    }

    /**
     * Specifies the length of the data that is supposed to be written to the stream.
     *
     * @param length the set length, must be {@literal >}= 0.
     * @throws IOException if an error occurred when specifying the length
     */
    public void specifyLength(long length) throws IOException {
        if (this.length != null) {
            throw new IllegalStateException("Unable specify the length again");
        }
        if (length < 0) {
            throw new IllegalArgumentException("The length of the stream cannot be negative");
        }
        synchronized (lock) {
            this.length = length;
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        checkLength(b.length);
        stream.write(b);
        synchronized (lock) {
            length -= b.length;
        }
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        checkLength(len);
        stream.write(b, off, len);
        synchronized (lock) {
            length -= len;
        }
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
        synchronized (lock) {
            --length;
        }
    }
}
