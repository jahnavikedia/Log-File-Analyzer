package com.loganalyzer.handlers;

import com.loganalyzer.models.APMLogEntry;
import com.loganalyzer.aggregators.APMLogAggregator;

public class APMLogHandler implements LogHandler {
    private LogHandler next;
    private static final APMLogAggregator aggregator = new APMLogAggregator();

    @Override
    public void setNext(LogHandler next) {
        this.next = next;
    }

    @Override
    public void handle(String line) {
        if (line.contains("metric=") && line.contains("value=")) {
            APMLogEntry entry = APMLogEntry.parse(line);
            if (entry != null) {
                aggregator.add(entry.getMetric(), entry.getValue());
                return;
            }
        }

        if (next != null) {
            next.handle(line);
        }
    }

    public static APMLogAggregator getAggregator() {
        return aggregator;
    }
}
