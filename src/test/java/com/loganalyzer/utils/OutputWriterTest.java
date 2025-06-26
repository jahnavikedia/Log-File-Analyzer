package com.loganalyzer.utils;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OutputWriterTest {

    @Test
    public void testJsonFileIsCreated() {
        Map<String, Object> data = new HashMap<>();
        data.put("testKey", "testValue");

        String path = "output/test_output.json";
        OutputWriter.write(path, data);

        File file = new File(path);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }
}
