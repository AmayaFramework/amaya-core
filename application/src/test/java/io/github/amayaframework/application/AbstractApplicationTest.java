package io.github.amayaframework.application;

import com.github.romanqed.jconv.Task;
import com.github.romanqed.jconv.TaskBuilder;
import com.github.romanqed.jct.CancelToken;
import com.github.romanqed.jct.Cancellation;
import io.github.amayaframework.di.core.ServiceProvider;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.GroupOptionSet;
import io.github.amayaframework.service.ServiceCallback;
import io.github.amayaframework.service.ServiceManager;
import io.github.amayaframework.service.ServiceState;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public final class AbstractApplicationTest {
    private final Task<Object> dummyTask = mock(Task.class);
    private final ServiceManager manager = mock(ServiceManager.class);
    private final TaskBuilder<Object> builder = mock(TaskBuilder.class);
    private final Environment environment = mock(Environment.class);
    private final GroupOptionSet options = mock(GroupOptionSet.class);

    private AbstractApplication<Object> createApp(AtomicBoolean doAppStartCalled,
                                                 AtomicBoolean doAppStopCalled,
                                                 AtomicBoolean doAppDisposeCalled) {
        when(builder.build()).thenReturn(dummyTask);
        return new AbstractApplication<>(options, environment, manager, builder) {

            @Override
            public ServiceProvider provider() {
                return null;
            }

            @Override
            protected void onFailure(Throwable throwable) {}

            @Override
            protected void onHalt(Throwable throwable) {}

            @Override
            protected void doAppStart(Task<Object> task, CancelToken token, ServiceCallback callback) {
                doAppStartCalled.set(true);
            }

            @Override
            protected void doAppStop(CancelToken token) {
                doAppStopCalled.set(true);
            }

            @Override
            protected void doAppDispose() {
                doAppDisposeCalled.set(true);
            }
        };
    }

    @Test
    public void testRunTransitionsToStarted() throws Throwable {
        var doAppStartCalled = new AtomicBoolean(false);
        var doAppStopCalled = new AtomicBoolean(false);
        var doAppDisposeCalled = new AtomicBoolean(false);
        var app = createApp(doAppStartCalled, doAppStopCalled, doAppDisposeCalled);

        app.run(dummyTask);

        assertEquals(ServiceState.STARTED, app.state());
        assertTrue(doAppStartCalled.get());
    }

    @Test
    public void testRunWithSupplierBuildsTask() throws Throwable {
        var doAppStartCalled = new AtomicBoolean(false);
        var doAppStopCalled = new AtomicBoolean(false);
        var doAppDisposeCalled = new AtomicBoolean(false);
        var app = createApp(doAppStartCalled, doAppStopCalled, doAppDisposeCalled);

        app.run(() -> dummyTask);

        assertEquals(ServiceState.STARTED, app.state());
        assertTrue(doAppStartCalled.get());
        verify(builder, never()).build(); // task supplied directly, no build
    }

    @Test
    public void testRunDefaultBuildsTask() throws Throwable {
        var doAppStartCalled = new AtomicBoolean(false);
        var doAppStopCalled = new AtomicBoolean(false);
        var doAppDisposeCalled = new AtomicBoolean(false);
        AbstractApplication<Object> app = createApp(doAppStartCalled, doAppStopCalled, doAppDisposeCalled);

        app.run();

        assertEquals(ServiceState.STARTED, app.state());
        assertTrue(doAppStartCalled.get());
        verify(builder, times(1)).build();
    }

    @Test
    public void testShutdownSetsDisposedState() throws IOException {
        var doAppStartCalled = new AtomicBoolean(false);
        var doAppStopCalled = new AtomicBoolean(false);
        var doAppDisposeCalled = new AtomicBoolean(false);
        var app = createApp(doAppStartCalled, doAppStopCalled, doAppDisposeCalled);

        app.shutdown();

        assertEquals(ServiceState.DISPOSED, app.state());
        assertTrue(doAppDisposeCalled.get());
        verify(environment).close();
    }

    @Test
    public void testAddHookReturnsTrueIfNoShutdown() {
        var doAppStartCalled = new AtomicBoolean(false);
        var doAppStopCalled = new AtomicBoolean(false);
        var doAppDisposeCalled = new AtomicBoolean(false);
        var app = createApp(doAppStartCalled, doAppStopCalled, doAppDisposeCalled);

        var added = app.addHook();

        assertTrue(added);
        assertNotNull(app.hook);
    }

    @Test
    public void testAddHookReturnsFalseIfShutdown() {
        var doAppStartCalled = new AtomicBoolean(false);
        var doAppStopCalled = new AtomicBoolean(false);
        var doAppDisposeCalled = new AtomicBoolean(false);
        AbstractApplication<Object> app = createApp(doAppStartCalled, doAppStopCalled, doAppDisposeCalled);

        app.shutdown = true;
        boolean added = app.addHook();

        assertFalse(added);
    }

    @Test
    public void testRemoveHookRemovesIfPresent() {
        var doAppStartCalled = new AtomicBoolean(false);
        var doAppStopCalled = new AtomicBoolean(false);
        var doAppDisposeCalled = new AtomicBoolean(false);
        var app = createApp(doAppStartCalled, doAppStopCalled, doAppDisposeCalled);

        app.addHook();
        app.removeHook();

        assertNull(app.hook);
    }

    @Test
    public void testRunThrowsIfAlreadyStarted() throws Throwable {
        var doAppStartCalled = new AtomicBoolean(false);
        var doAppStopCalled = new AtomicBoolean(false);
        var doAppDisposeCalled = new AtomicBoolean(false);
        var app = createApp(doAppStartCalled, doAppStopCalled, doAppDisposeCalled);

        app.run(dummyTask);

        var ex = assertThrows(IllegalStateException.class, () -> app.run(dummyTask));
        assertTrue(ex.getMessage().contains("Cannot run application from state"));
    }

    @Test
    public void testResetClearsTaskAndBuilder() {
        var doAppStartCalled = new AtomicBoolean(false);
        var doAppStopCalled = new AtomicBoolean(false);
        var doAppDisposeCalled = new AtomicBoolean(false);
        var app = createApp(doAppStartCalled, doAppStopCalled, doAppDisposeCalled);

        app.reset();

        assertNull(app.task);
        verify(builder).clear();
    }

    @Test
    public void testDoStopCallsDoAppStopAndRemovesHook() throws Throwable {
        var doAppStartCalled = new AtomicBoolean(false);
        var doAppStopCalled = new AtomicBoolean(false);
        var doAppDisposeCalled = new AtomicBoolean(false);
        var app = createApp(doAppStartCalled, doAppStopCalled, doAppDisposeCalled);

        app.addHook();

        var token = Cancellation.emptyToken();

        app.doStop(token);

        assertTrue(doAppStopCalled.get());
        assertNull(app.hook);
    }

    @Test
    public void testDoDisposeCallsDoAppDisposeAndRemovesHook() throws IOException {
        var doAppStartCalled = new AtomicBoolean(false);
        var doAppStopCalled = new AtomicBoolean(false);
        var doAppDisposeCalled = new AtomicBoolean(false);
        var app = createApp(doAppStartCalled, doAppStopCalled, doAppDisposeCalled);

        app.addHook();

        app.doDispose();

        assertTrue(doAppDisposeCalled.get());
        assertNull(app.hook);
        verify(environment).close();
    }
}
