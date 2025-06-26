package com.loganalyzer.aggregators;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class APMLogAggregatorTest {

    @Test
    public void testMetricAggregation() {
        APMLogAggregator aggregator = new APMLogAggregator();
        aggregator.add("cpu_usage_percent", 60.0);
        aggregator.add("cpu_usage_percent", 70.0);
        aggregator.add("cpu_usage_percent", 90.0);

        Map<String, Object> result = aggregator.getAggregations();
        Map<String, Object> cpuStats = (Map<String, Object>) result.get("cpu_usage_percent");

        assertEquals(60, ((Number) cpuStats.get("minimum")).intValue());
        assertEquals(90, ((Number) cpuStats.get("max")).intValue());
        assertEquals(73.33, round(((Number) cpuStats.get("average")).doubleValue(), 2));
        assertEquals(70.0, ((Number) cpuStats.get("median")).doubleValue());
    }

    private double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}
