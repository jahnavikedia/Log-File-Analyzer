package com.loganalyzer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MainIntegrationTest {

    @Test
    public void testEndToEndFlow() throws Exception {
        String testLog = "timestamp=2024-04-12T22:00:00Z metric=cpu_usage_percent value=78\n" +
                         "timestamp=... level=ERROR message=Failed\n" +
                         "timestamp=... request_url=\"/api/test\" response_status=200 response_time_ms=150";

        File tempInput = new File("src/input/test_logs.txt");
        tempInput.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(tempInput)) {
            writer.write(testLog);
        }

        String[] args = {"--file", "test_logs.txt"};
        Main.main(args);

        Gson gson = new Gson();
        try (Reader reader = new FileReader("src/output/apm.json")) {
            Map<String, Object> json = gson.fromJson(reader, new TypeToken<Map<String, Object>>() {}.getType());
            assertTrue(json.containsKey("cpu_usage_percent"));
        }

        try (Reader reader = new FileReader("src/output/application.json")) {
            Map<String, Object> json = gson.fromJson(reader, new TypeToken<Map<String, Object>>() {}.getType());
            assertTrue(json.containsKey("ERROR"));
        }

        try (Reader reader = new FileReader("src/output/request.json")) {
            Map<String, Object> json = gson.fromJson(reader, new TypeToken<Map<String, Object>>() {}.getType());
            assertTrue(json.containsKey("/api/test"));
        }
    }
}
