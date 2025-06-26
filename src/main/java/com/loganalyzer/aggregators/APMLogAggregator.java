package com.loganalyzer.aggregators;

import java.util.*;

public class APMLogAggregator implements LogAggregator {
    private final Map<String, List<Double>> metrics = new HashMap<>();

    public void add(String metric, double value) {
        metrics.computeIfAbsent(metric, k -> new ArrayList<>()).add(value);
    }

    @Override
    public Map<String, Object> getAggregations() {
        Map<String, Object> result = new HashMap<>();

        for (Map.Entry<String, List<Double>> entry : metrics.entrySet()) {
            String metric = entry.getKey();
            List<Double> values = entry.getValue();
            Collections.sort(values);

            Map<String, Object> stats = new HashMap<>();
            stats.put("minimum", formatNumber(values.get(0)));
            stats.put("max", formatNumber(values.get(values.size() - 1)));
            stats.put("average", formatNumber(values.stream().mapToDouble(d -> d).average().orElse(0)));
            stats.put("median", formatNumber(calculateMedian(values)));

            result.put(metric, stats);
        }

        return result;
    }

    private double calculateMedian(List<Double> sorted) {
        int n = sorted.size();
        if (n % 2 == 0) {
            return (sorted.get(n / 2 - 1) + sorted.get(n / 2)) / 2.0;
        } else {
            return sorted.get(n / 2);
        }
    }
    private Object formatNumber(double value) {
        if (value == (int) value) {
            return (int) value; // returns Integer
        } else {
            return value; // stays as Double
        }
    }
}
