package io.github.amayaframework.service;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ServiceManagerTest {
    private static final ServiceManagerFactory FACTORY = new HandledManagerFactory();
    private static final ServiceHandler HANDLER = new SimpleHandler();

    private static ServiceManager createManager() {
        return FACTORY.create(HANDLER);
    }

    private static Service createService(Runnable start, Runnable stop) {
        return new Service() {

            @Override
            public void start() {
                start.run();
            }

            @Override
            public void stop() {
                stop.run();
            }
        };
    }

    private static Service createService() {
        return new Service() {

            @Override
            public void start() {
            }

            @Override
            public void stop() {
            }
        };
    }

    @Test
    public void testLifecycle() throws Throwable {
        var manager = createManager();
        var cnt = new int[3];
        var st = (Runnable) () -> cnt[0] += 1;
        var sp = (Runnable) () -> cnt[1] += 1;
        var s1 = manager.add(createService(st, sp));
        var s2 = manager.add(createService(st, sp));
        var s3 = manager.add(createService(st, sp));
        assertEquals(ServiceState.NEW, s1.getState());
        assertEquals(ServiceState.NEW, s2.getState());
        assertEquals(ServiceState.NEW, s3.getState());
        manager.start();
        assertEquals(cnt[0], 3);
        assertEquals(ServiceState.STARTED, s1.getState());
        assertEquals(ServiceState.STARTED, s2.getState());
        assertEquals(ServiceState.STARTED, s3.getState());
        manager.stop();
        assertEquals(cnt[1], 3);
        assertEquals(ServiceState.STOPPED, s1.getState());
        assertEquals(ServiceState.STOPPED, s2.getState());
        assertEquals(ServiceState.STOPPED, s3.getState());
        var set = new HashSet<WatchedService>();
        manager.getServices().forEach(set::add);
        assertEquals(Set.of(s1, s2, s3), set);
        s1.unwatch();
        s2.unwatch();
        s3.unwatch();
        assertEquals(ServiceState.UNWATCHED, s1.getState());
        assertEquals(ServiceState.UNWATCHED, s2.getState());
        assertEquals(ServiceState.UNWATCHED, s3.getState());
    }

    @Test
    public void testUnwatch() {
        var manager = createManager();
        var s = createService();
        var ws = manager.add(s);
        assertEquals(s, ws.unwatch());
    }

    static final class SimpleHandler implements ServiceHandler {

        @Override
        public void start(Iterable<WatchedService> services) throws Throwable {
            for (var service : services) {
                service.start();
            }
        }

        @Override
        public void stop(Iterable<WatchedService> services) throws Throwable {
            for (var service : services) {
                service.stop();
            }
        }
    }
}
