package io.github.amayaframework.service;

import com.github.romanqed.jct.CancelToken;
import com.github.romanqed.jct.Cancellation;
import com.github.romanqed.jfunc.Exceptions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public final class AbstractServiceManagerTest {

    @Test
    public void testServiceLifecycle() throws Throwable {
        var manager = (ServiceManager) new TestManager();
        manager.start();
        var service = new TestService();
        manager.add(service);
        assertEquals(ServiceState.STARTED, service.state());
        manager.stop();
        assertEquals(ServiceState.STOPPED, service.state());
        manager.dispose();
        assertEquals(ServiceState.DISPOSED, service.state());
    }

    @Test
    public void testStartAll() throws Throwable {
        var manager = (ServiceManager) new TestManager();
        var a = new TestService();
        var b = new TestService();
        manager.add(a);
        manager.add(b);
        manager.start(null, null);
        assertEquals(ServiceState.STARTED, manager.state());
        assertEquals(ServiceState.STARTED, a.state());
        assertEquals(ServiceState.STARTED, b.state());
        assertEquals("start", a.events.get(0));
        assertEquals("start", b.events.get(0));
    }

    @Test
    public void testStartAllWithCancellation() throws Throwable {
        var manager = (ServiceManager) new TestManager();
        var a = new TestService();
        var b = new TestService();
        manager.add(a);
        manager.add(b);
        manager.start(Cancellation.canceledToken(), null);
        assertEquals(ServiceState.NEW, manager.state());
        assertEquals(ServiceState.NEW, a.state());
        assertEquals(ServiceState.NEW, b.state());
        assertTrue(a.events.isEmpty());
        assertTrue(b.events.isEmpty());
    }

    @Test
    public void testStopAll() throws Throwable {
        var manager = (ServiceManager) new TestManager();
        var a = new TestService();
        var b = new TestService();
        manager.add(a);
        manager.add(b);
        manager.start(null, null);
        manager.stop(null);
        assertEquals(ServiceState.STOPPED, manager.state());
        assertEquals(ServiceState.STOPPED, a.state());
        assertEquals(ServiceState.STOPPED, b.state());
        assertEquals(List.of("start", "stop"), a.events);
        assertEquals(List.of("start", "stop"), b.events);
    }

    @Test
    public void testStopWithCancellation() throws Throwable {
        var manager = (ServiceManager) new TestManager();
        var a = new TestService();
        manager.add(a);
        manager.start(null, null);
        manager.stop(Cancellation.canceledToken());
        assertEquals(ServiceState.STARTED, manager.state());
        assertEquals(ServiceState.STARTED, a.state());
        assertEquals(List.of("start"), a.events);
    }

    @Test
    public void testDisposeAll() {
        var manager = (ServiceManager) new TestManager();
        var a = new TestService();
        var b = new TestService();
        manager.add(a);
        manager.add(b);
        manager.dispose();
        assertEquals(ServiceState.DISPOSED, manager.state());
        assertEquals(ServiceState.DISPOSED, a.state());
        assertEquals(ServiceState.DISPOSED, b.state());
        assertEquals("dispose", a.events.get(0));
        assertEquals("dispose", b.events.get(0));
    }

    @Test
    public void testAddIsIdempotent() throws Throwable {
        var manager = (ServiceManager) new TestManager();
        manager.start();
        var a = new TestService();
        manager.add(a);
        manager.add(a);
        assertEquals(ServiceState.STARTED, a.state());
        assertEquals(List.of("start"), a.events);
    }

    @Test
    public void testAddNullIgnores() {
        var manager = (ServiceManager) new TestManager();
        manager.add((Service) null);
        manager.add((Iterable<Service>) null);
        assertEquals(List.of(), manager.services());
    }

    @Test
    public void testRemoveNullIgnores() {
        var manager = (ServiceManager) new TestManager();
        var a = new TestService();
        manager.add(a);
        manager.remove((Service) null);
        manager.remove((Iterable<Service>) null);
        assertIterableEquals(List.of(a), manager.services());
    }

    @Test
    public void testStateBeforeStartIsNew() {
        assertEquals(ServiceState.NEW, new TestManager().state());
    }

    @Test
    public void testStartPartialFailureMarksFailed() {
        var manager = new TestManager();
        var good = new TestService();
        var bad = new AbstractService() {
            @Override
            protected void doStart(CancelToken token, ServiceCallback callback) {
                throw new RuntimeException("fail");
            }

            @Override
            protected void doStop(CancelToken token) {
            }

            @Override
            protected void doDispose() {
            }
        };
        manager.add(good);
        manager.add(bad);
        assertThrows(RuntimeException.class, () -> manager.start(null, null));
        assertEquals(ServiceState.FAILED, manager.state());
        assertEquals(ServiceState.FAILED, bad.state());
        assertTrue(good.state() == ServiceState.NEW || good.state() == ServiceState.STOPPED);
    }

    @Test
    public void testCallbackThrowsInStartOrStop() throws Throwable {
        var manager = new TestManager();
        var throwsInStart = new Service() {
            @Override
            public void start(CancelToken token, ServiceCallback callback) {
                callback.fail(new IOException());
            }

            @Override
            public void stop(CancelToken token) {
            }

            @Override
            public void dispose() {
            }
        };
        manager.add(throwsInStart);
        assertThrows(IllegalStateException.class, manager::start);
        manager.remove(throwsInStart);
        var throwsInStop = new Service() {
            ServiceCallback cb;

            @Override
            public void start(CancelToken token, ServiceCallback callback) {
                cb = callback;
            }

            @Override
            public void stop(CancelToken token) {
                cb.fail(new IOException());
            }

            @Override
            public void dispose() {
            }
        };
        manager.add(throwsInStop);
        manager.start();
        assertThrows(IllegalStateException.class, manager::stop);
    }

    @Test
    public void testCallbackCallInWatchdog() throws Throwable {
        var manager = new TestManager();
        manager.add(new Service() {
            @Override
            public void start(CancelToken token, ServiceCallback callback) {
                new Thread(() -> {
                    Exceptions.silent(() -> Thread.sleep(500));
                    callback.fail(new IOException());
                }).start();
            }

            @Override
            public void stop(CancelToken token) {
            }

            @Override
            public void dispose() {
            }
        });
        manager.start();
        Thread.sleep(1000);
        assertEquals(ServiceState.FAILED, manager.state());
    }

    private static final class TestManager extends AbstractServiceManager {

        public TestManager() {
            super(new Object(), Cancellation.source(), HashMap::new, e -> {});
        }

        @Override
        protected void doStart(Service service, CancelToken token, ServiceCallback callback) throws Throwable {
            service.start(token, callback);
        }

        @Override
        protected void doStop(Service service, CancelToken token) throws Throwable {
            service.stop(token);
        }

        @Override
        protected void doDispose(Service service) {
            service.dispose();
        }
    }

    private static final class TestService extends AbstractService {
        final List<String> events;

        public TestService() {
            this.events = new LinkedList<>();
        }

        @Override
        protected void doStart(CancelToken token, ServiceCallback callback) {
            if (!token.canceled()) {
                events.add("start");
            }
        }

        @Override
        protected void doStop(CancelToken token) {
            if (!token.canceled()) {
                events.add("stop");
            }
        }

        @Override
        protected void doDispose() {
            events.add("dispose");
        }
    }
}
