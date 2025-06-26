package com.loganalyzer;

import com.loganalyzer.handlers.*;
import com.loganalyzer.utils.OutputWriter;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        String inputFile = "src/input/logs.txt";  // Default input path

        if (args.length == 2 && args[0].equals("--file")) {
            inputFile = "src/input/" + args[1];  // Prepend folder
        } else {
            System.out.println("⚠️ No input file passed. Using default: " + inputFile);
        }

        File file = new File(inputFile);
        if (!file.exists()) {
            System.out.println("❌ File not found: " + inputFile);
            return;
        }

        LogHandler chain = buildHandlerChain();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                chain.handle(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Output to /output/ folder
        OutputWriter.write("src/output/apm.json", APMLogHandler.getAggregator().getAggregations());
        OutputWriter.write("src/output/application.json", ApplicationLogHandler.getAggregator().getAggregations());
        OutputWriter.write("src/output/request.json", RequestLogHandler.getAggregator().getAggregations());
        System.out.println("✅ Done.");
    }

    private static LogHandler buildHandlerChain() {
        LogHandler apm = new APMLogHandler();
        LogHandler app = new ApplicationLogHandler();
        LogHandler req = new RequestLogHandler();
        LogHandler nullHandler = new NullLogHandler();

        apm.setNext(app);
        app.setNext(req);
        req.setNext(nullHandler);
        return apm;
    }
}
