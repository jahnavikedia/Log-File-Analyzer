package com.loganalyzer.aggregators;

import java.util.*;

public class RequestLogAggregator implements LogAggregator {
    private final Map<String, List<Integer>> responseTimes = new HashMap<>();
    private final Map<String, Map<String, Integer>> statusBuckets = new HashMap<>();

    public void add(String url, int responseTime, int status) {
        // Response Times
        responseTimes.computeIfAbsent(url, k -> new ArrayList<>()).add(responseTime);

        // Status Code Buckets
        String codeGroup = (status / 100) + "XX";
        statusBuckets.computeIfAbsent(url, k -> new HashMap<>());
        Map<String, Integer> bucket = statusBuckets.get(url);
        bucket.put(codeGroup, bucket.getOrDefault(codeGroup, 0) + 1);
    }

    private Map<String, Object> computePercentiles(List<Integer> list) {
        Collections.sort(list);
        Map<String, Object> stats = new HashMap<>();
        stats.put("min", list.get(0));
        stats.put("max", list.get(list.size() - 1));
        stats.put("50_percentile", getPercentile(list, 50));
        stats.put("90_percentile", getPercentile(list, 90));
        stats.put("95_percentile", getPercentile(list, 95));
        stats.put("99_percentile", getPercentile(list, 99));
        return stats;
    }

    private int getPercentile(List<Integer> sorted, int percentile) {
        int index = (int) Math.ceil(percentile / 100.0 * sorted.size()) - 1;
        return sorted.get(Math.min(Math.max(index, 0), sorted.size() - 1));
    }

    @Override
    public Map<String, Object> getAggregations() {
        Map<String, Object> output = new HashMap<>();

        for (String url : responseTimes.keySet()) {
            Map<String, Object> routeData = new HashMap<>();
            routeData.put("response_times", computePercentiles(responseTimes.get(url)));
            routeData.put("status_codes", statusBuckets.getOrDefault(url, new HashMap<>()));
            output.put(url, routeData);
        }

        return output;
    }

}
