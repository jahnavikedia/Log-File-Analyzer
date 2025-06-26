package com.loganalyzer.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationLogEntryTest {
    @Test
    public void testValidParse() {
        String log = "timestamp=... level=ERROR message=Something broke";
        ApplicationLogEntry entry = ApplicationLogEntry.parse(log);
        assertNotNull(entry);
        assertEquals("ERROR", entry.getLevel());
    }

    @Test
    public void testInvalidLogReturnsNull() {
        String log = "timestamp=... message=Something broke";
        ApplicationLogEntry entry = ApplicationLogEntry.parse(log);
        assertNull(entry);
    }
}
