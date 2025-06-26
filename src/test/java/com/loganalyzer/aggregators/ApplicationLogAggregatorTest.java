package com.loganalyzer.aggregators;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationLogAggregatorTest {
    @Test
    public void testAddAndAggregate() {
        ApplicationLogAggregator agg = new ApplicationLogAggregator();
        agg.reset();
        agg.add("ERROR");
        agg.add("INFO");
        agg.add("ERROR");

        Map<String, Object> counts = agg.getAggregations();
        assertEquals(2, counts.get("ERROR"));
        assertEquals(1, counts.get("INFO"));
    }
}
