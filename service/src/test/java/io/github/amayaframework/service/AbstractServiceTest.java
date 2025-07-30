package io.github.amayaframework.service;

import com.github.romanqed.jct.CancelToken;
import com.github.romanqed.jct.Cancellation;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractServiceTest {

    private static final class TestService extends AbstractService {
        private final AtomicBoolean started = new AtomicBoolean(false);
        private final AtomicBoolean stopped = new AtomicBoolean(false);
        private final AtomicBoolean disposed = new AtomicBoolean(false);

        public TestService() {
            super();
        }

        @Override
        protected void doStart(CancelToken token, ServiceCallback callback) {
            if (token.canceled()) {
                return;
            }
            if (started.getAndSet(true)) {
                throw new RuntimeException("Double-started");
            }
        }

        @Override
        protected void doStop(CancelToken token) {
            if (token.canceled()) {
                return;
            }
            if (stopped.getAndSet(true)) {
                throw new RuntimeException("Double-stopped");
            }
        }

        @Override
        protected void doDispose() {
            if (disposed.getAndSet(true)) {
                throw new RuntimeException("Double-disposed");
            }
        }

        public boolean isStarted() {
            return started.get();
        }

        public boolean isStopped() {
            return stopped.get();
        }

        public boolean isDisposed() {
            return disposed.get();
        }
    }

    @Test
    public void testNewState() {
        assertEquals(ServiceState.NEW, new TestService().state());
    }

    @Test
    public void testStartTransitionToStarted() throws Throwable {
        var service = new TestService();
        service.start(null, null);
        assertEquals(ServiceState.STARTED, service.state());
        assertTrue(service.isStarted());
    }

    @Test
    public void testStartTwice() throws Throwable {
        var service = new TestService();
        service.start(null, null);
        service.start(null, null); // Should no-op
        assertEquals(ServiceState.STARTED, service.state());
    }

    @Test
    public void testStopTwice() throws Throwable {
        var service = new TestService();
        service.start(null, null);
        service.stop(null);
        service.stop(null); // Should no-op
        assertEquals(ServiceState.STOPPED, service.state());
    }

    @Test
    public void testStartCanceledToken() throws Throwable {
        var service = new TestService();
        var token = Cancellation.canceledToken();
        service.start(token, null);
        assertEquals(ServiceState.STOPPED, service.state()); // doStart wasn't called
        assertFalse(service.isStarted());
    }

    @Test
    public void testStartException() {
        var throwingService = new AbstractService() {
            @Override
            protected void doStart(CancelToken token, ServiceCallback callback) {
                throw new IllegalStateException("Failure");
            }

            @Override
            protected void doStop(CancelToken token) {
            }

            @Override
            protected void doDispose() {
            }
        };
        assertThrows(IllegalStateException.class, () -> throwingService.start(null, null));
        assertEquals(ServiceState.FAILED, throwingService.state());
    }

    @Test
    public void testStopFromStarted() throws Throwable {
        var service = new TestService();
        service.start(null, null);
        service.stop(null);
        assertEquals(ServiceState.STOPPED, service.state());
        assertTrue(service.isStopped());
    }

    @Test
    public void testStopCanceledToken() throws Throwable {
        var service = new TestService();
        service.start(null, null);
        service.stop(Cancellation.canceledToken());
        assertEquals(ServiceState.STARTED, service.state()); // doStop не вызван
        assertFalse(service.isStopped());
    }

    @Test
    public void testStopException() throws Throwable {
        var throwingService = new AbstractService() {
            @Override
            protected void doStart(CancelToken token, ServiceCallback callback) {
            }

            @Override
            protected void doStop(CancelToken token) {
                throw new RuntimeException("Stop failed");
            }

            @Override
            protected void doDispose() {
            }
        };
        throwingService.start(null, null);
        assertThrows(RuntimeException.class, () -> throwingService.stop(null));
        assertEquals(ServiceState.FAILED, throwingService.state());
    }

    @Test
    public void testDispose() {
        var service = new TestService();
        service.dispose();
        assertEquals(ServiceState.DISPOSED, service.state());
        assertTrue(service.isDisposed());
    }

    @Test
    public void testDisposeIsIdempotent() {
        var service = new TestService();
        service.dispose();
        service.dispose(); // Второй вызов ничего не должен сделать
        assertEquals(ServiceState.DISPOSED, service.state());
        assertTrue(service.isDisposed());
    }

    @Test
    public void testDisposeSwallowsException() {
        var throwingService = new AbstractService() {
            @Override
            protected void doStart(CancelToken token, ServiceCallback callback) {
            }

            @Override
            protected void doStop(CancelToken token) {
            }

            @Override
            protected void doDispose() {
                throw new RuntimeException("Should be swallowed");
            }
        };
        assertDoesNotThrow(throwingService::dispose);
        assertEquals(ServiceState.DISPOSED, throwingService.state());
    }
}
