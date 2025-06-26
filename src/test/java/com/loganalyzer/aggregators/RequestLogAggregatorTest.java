package com.loganalyzer.aggregators;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RequestLogAggregatorTest {
    @Test
    public void testAddAndAggregate() {
        RequestLogAggregator agg = new RequestLogAggregator();
        agg.add("/api/status", 100, 200);
        agg.add("/api/status", 200, 404);
        agg.add("/api/status", 300, 500);

        Map<String, Object> data = agg.getAggregations();
        assertTrue(data.containsKey("/api/status"));

        Map<String, Object> route = (Map<String, Object>) data.get("/api/status");
        assertTrue(route.containsKey("response_times"));
        assertTrue(route.containsKey("status_codes"));
    }
}
