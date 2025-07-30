package io.github.amayaframework.service;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public final class ServiceStateTest {

    @Test
    public void testStateSet() {
        var set = new HashSet<String>();
        for (var state : ServiceState.values()) {
            set.add(state.name());
        }
        assertEquals(
                Set.of("UNMANAGED", "NEW", "DISPOSED", "FAILED", "STARTING", "STARTED", "STOPPING", "STOPPED"),
                set
        );
    }

    @Test
    public void testIsStopped() {
        assertTrue(ServiceState.NEW.isStopped());
        assertTrue(ServiceState.DISPOSED.isStopped());
        assertTrue(ServiceState.FAILED.isStopped());
        assertTrue(ServiceState.STOPPED.isStopped());
        assertFalse(ServiceState.UNMANAGED.isStopped());
        assertFalse(ServiceState.STARTING.isStopped());
        assertFalse(ServiceState.STARTED.isStopped());
        assertFalse(ServiceState.STOPPING.isStopped());
    }
}
