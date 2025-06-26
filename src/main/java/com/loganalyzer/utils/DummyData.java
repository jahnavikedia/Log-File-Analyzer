package com.loganalyzer.utils;

import java.util.HashMap;
import java.util.Map;

public class DummyData {
    public static Map<String, Object> get() {
        Map<String, Object> dummy = new HashMap<>();
        dummy.put("status", "success");
        dummy.put("message", "This is a test output.");
        return dummy;
    }
}
