package com.loganalyzer.handlers;

import com.loganalyzer.models.ApplicationLogEntry;
import com.loganalyzer.aggregators.ApplicationLogAggregator;

public class ApplicationLogHandler implements LogHandler {
    private LogHandler next;
    private static final ApplicationLogAggregator aggregator = new ApplicationLogAggregator();

    @Override
    public void setNext(LogHandler next) {
        this.next = next;
    }

    @Override
    public void handle(String line) {
        if (line.contains("level=")) {
            ApplicationLogEntry entry = ApplicationLogEntry.parse(line);
            if (entry != null) {
                aggregator.add(entry.getLevel());
                return;
            }
        }

        if (next != null) next.handle(line);
    }

    public static ApplicationLogAggregator getAggregator() {
        return aggregator;
    }
}
