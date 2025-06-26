package com.loganalyzer.handlers;

public interface LogHandler {
    void setNext(LogHandler next);
    void handle(String line);
}
