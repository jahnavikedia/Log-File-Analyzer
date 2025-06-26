package com.loganalyzer.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class APMLogEntryTest {
    @Test
    public void testValidParse() {
        String log = "timestamp=... metric=cpu_usage_percent value=72";
        APMLogEntry entry = APMLogEntry.parse(log);
        assertNotNull(entry);
        assertEquals("cpu_usage_percent", entry.getMetric());
        assertEquals(72.0, entry.getValue(), 0.01);
    }

    @Test
    public void testInvalidParseReturnsNull() {
        String log = "timestamp=... value=72";
        APMLogEntry entry = APMLogEntry.parse(log);
        assertNull(entry);
    }
}
