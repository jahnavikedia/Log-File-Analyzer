package com.loganalyzer.handlers;

import com.loganalyzer.aggregators.ApplicationLogAggregator;
import com.loganalyzer.models.ApplicationLogEntry;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class HandlerChainTest {

    @Test
    public void testChainPassesToCorrectHandler() {
        LogHandler apm = new APMLogHandler();
        LogHandler app = new ApplicationLogHandler();
        LogHandler req = new RequestLogHandler();
        LogHandler nullHandler = new NullLogHandler();

        apm.setNext(app);
        app.setNext(req);
        req.setNext(nullHandler);

        ApplicationLogHandler.getAggregator().reset();
        app.handle("timestamp=... level=ERROR message=Oops");

        Map<String, Object> appAgg = ApplicationLogHandler.getAggregator().getAggregations();
        assertTrue(appAgg.containsKey("ERROR"));
        assertEquals(1, appAgg.get("ERROR"));
    }
}
