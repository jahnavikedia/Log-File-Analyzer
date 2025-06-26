package com.loganalyzer.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputWriter {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void write(String filename, Object data) {
        try {
            File file = new File(filename);
            file.getParentFile().mkdirs(); // ✅ Ensure output/ folder exists

            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(data, writer);
                System.out.println("✅ Wrote to " + filename);
            }
        } catch (IOException e) {
            System.err.println("❌ Failed to write: " + e.getMessage());
        }
    }
}


