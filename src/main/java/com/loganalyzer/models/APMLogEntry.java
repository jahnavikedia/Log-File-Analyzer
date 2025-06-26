package com.loganalyzer.models;

public class APMLogEntry extends LogEntry {
    private String metric;
    private double value;

    public APMLogEntry(String metric, double value) {
        this.metric = metric;
        this.value = value;
    }

    public String getMetric() {
        return metric;
    }

    public double getValue() {
        return value;
    }

    public static APMLogEntry parse(String line) {
        try {
            String[] parts = line.split("\\s+");
            String metric = null;
            double value = -1;

            for (String part : parts) {
                if (part.startsWith("metric=")) {
                    metric = part.split("=", 2)[1];
                } else if (part.startsWith("value=")) {
                    value = Double.parseDouble(part.split("=", 2)[1]);
                }
            }

            if (metric != null && value >= 0) {
                return new APMLogEntry(metric, value);
            }
        } catch (Exception e) {
            // ignore parse errors
        }

        return null;
    }
}

