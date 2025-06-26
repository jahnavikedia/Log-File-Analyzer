package com.loganalyzer.aggregators;

import java.util.Map;

public interface LogAggregator {
    Map<String, Object> getAggregations();
}
