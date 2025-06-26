package com.loganalyzer.handlers;

public class NullLogHandler implements LogHandler {
    @Override
    public void setNext(LogHandler next) {
        // no next
    }

    @Override
    public void handle(String line) {
        System.out.println("❌ Unrecognized log: " + line);
    }
}
