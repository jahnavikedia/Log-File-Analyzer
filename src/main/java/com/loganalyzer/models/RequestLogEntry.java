package com.loganalyzer.models;

public class RequestLogEntry extends LogEntry {
    private final String url;
    private final int status;
    private final int responseTime;

    public RequestLogEntry(String url, int status, int responseTime) {
        this.url = url;
        this.status = status;
        this.responseTime = responseTime;
    }

    public String getUrl() {
        return url;
    }

    public int getStatus() {
        return status;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public static RequestLogEntry parse(String line) {
        try {
            String url = null;
            int status = -1;
            int responseTime = -1;

            String[] parts = line.split("\\s+");
            for (String part : parts) {
                if (part.startsWith("request_url=")) {
                    url = part.split("=", 2)[1].replaceAll("\"", "");
                } else if (part.startsWith("response_status=")) {
                    status = Integer.parseInt(part.split("=", 2)[1]);
                } else if (part.startsWith("response_time_ms=")) {
                    responseTime = Integer.parseInt(part.split("=", 2)[1]);
                }
            }

            if (url != null && status != -1 && responseTime != -1) {
                return new RequestLogEntry(url, status, responseTime);
            }
        } catch (Exception ignored) {}
        return null;
    }
}
