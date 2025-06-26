package com.loganalyzer.handlers;

import org.junit.jupiter.api.Test;

public class NullLogHandlerTest {
    @Test
    public void testHandlesUnknownLog() {
        LogHandler handler = new NullLogHandler();
        handler.handle("unrecognized=log data");
        // No assertion needed since it just prints
    }
}
