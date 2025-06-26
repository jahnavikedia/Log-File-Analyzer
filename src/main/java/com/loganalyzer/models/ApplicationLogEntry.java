package com.loganalyzer.models;

public class ApplicationLogEntry extends LogEntry {
    private final String level;

    public ApplicationLogEntry(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public static ApplicationLogEntry parse(String line) {
        try {
            String[] parts = line.split("\\s+");
            for (String part : parts) {
                if (part.startsWith("level=")) {
                    String level = part.split("=")[1];
                    return new ApplicationLogEntry(level.toUpperCase());
                }
            }
        } catch (Exception ignored) {}
        return null;
    }
}
