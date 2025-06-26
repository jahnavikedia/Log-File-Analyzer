package com.loganalyzer.handlers;

import com.loganalyzer.models.RequestLogEntry;
import com.loganalyzer.aggregators.RequestLogAggregator;

public class RequestLogHandler implements LogHandler {
    private LogHandler next;
    private static final RequestLogAggregator aggregator = new RequestLogAggregator();

    @Override
    public void setNext(LogHandler next) {
        this.next = next;
    }

    @Override
    public void handle(String line) {
        if (line.contains("request_url=") && line.contains("response_status=")) {
            RequestLogEntry entry = RequestLogEntry.parse(line);
            if (entry != null) {
                aggregator.add(entry.getUrl(), entry.getResponseTime(), entry.getStatus());
                return;
            }
        }
        if (next != null) next.handle(line);
    }

    public static RequestLogAggregator getAggregator() {
        return aggregator;
    }
}
