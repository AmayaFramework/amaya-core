package io.github.amayaframework.core.contexts;

import com.github.romanqed.jutils.util.Checks;

import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Base class for users stream handlers
 */
public abstract class StreamHandler implements Consumer<OutputStream> {
    private Exception exception = null;
    private boolean successful = true;
    private int contentLength = 0;
    private Flushable flushable;

    /**
     * Override this method to interact with the stream
     *
     * @param stream {@link OutputStream} body
     * @throws IOException by stream
     */
    public abstract void handle(OutputStream stream) throws IOException;

    @Override
    public void accept(OutputStream outputStream) {
        try {
            handle(outputStream);
        } catch (Exception e) {
            successful = false;
            exception = e;
        }
    }

    /**
     * <p>Flushes your stream/writer if it necessary</p>
     * <p>Note: DO NOT flush stream/writer manually</p>
     *
     * @param flushable {@link Flushable} to flush
     */
    protected void flush(Flushable flushable) {
        this.flushable = Objects.requireNonNull(flushable);
    }

    public Exception getException() {
        return exception;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public int getContentLength() {
        return contentLength;
    }

    /**
     * Sets content length if it is necessary
     *
     * @param contentLength to be set. Must be more or equal 0
     */
    protected void setContentLength(int contentLength) {
        this.contentLength = Checks.requireCorrectValue(contentLength, e -> e >= 0);
    }

    public void flush() throws IOException {
        if (flushable != null) {
            flushable.flush();
        }
    }
}
