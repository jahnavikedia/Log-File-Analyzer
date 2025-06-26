package com.loganalyzer.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RequestLogEntryTest {
    @Test
    public void testValidParse() {
        String log = "timestamp=.. request_url=\"/api/test\" response_status=200 response_time_ms=120";
        RequestLogEntry entry = RequestLogEntry.parse(log);
        assertNotNull(entry);
        assertEquals("/api/test", entry.getUrl());
        assertEquals(200, entry.getStatus());
        assertEquals(120, entry.getResponseTime());
    }

    @Test
    public void testInvalidLogReturnsNull() {
        String log = "timestamp=... response_time_ms=120";
        RequestLogEntry entry = RequestLogEntry.parse(log);
        assertNull(entry);
    }
}
