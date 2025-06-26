package com.loganalyzer.aggregators;

import java.util.HashMap;
import java.util.Map;

public class ApplicationLogAggregator implements LogAggregator {
    private final Map<String, Integer> levelCounts = new HashMap<>();

    public void add(String level) {
        levelCounts.put(level, levelCounts.getOrDefault(level, 0) + 1);
    }

    @Override
    public Map<String, Object> getAggregations() {
        return new HashMap<>(levelCounts);
    }

    public void reset() {
        levelCounts.clear();  // ✅ This is the fix
    }
}

