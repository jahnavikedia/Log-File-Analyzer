# Log File Analyzer

A Java command-line application that analyzes mixed log files, classifies log entries (APM logs, Request logs, and Application logs), and outputs structured JSON summaries. The project is built using the **Chain of Responsibility** design pattern for scalable and modular log processing.

---
## Project Report

📄 **Answers to Part 1** and the **class diagram** are available in:  
`202 answers individual project.pdf`

---

## How to Run the Application

### Build the JAR file

```bash
mvn clean package
mv target/logfileanalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar log-analyzer.jar
```

### Run with a sample input file

```bash
java -jar log-analyzer.jar --file sample_input_logs-1.TXT
```

> The output files will be created inside the `src/output/` directory.

### Run Tests

```bash
mvn clean test
```

---

## Output Files

After execution, the following JSON files are generated:

- `apm.json` – Aggregates for metrics like CPU, memory, disk, etc.
- `application.json` – Counts of log levels (e.g., ERROR, INFO, DEBUG)
- `request.json` – API response time stats and status code summaries

---

## Design Pattern Used

The application uses the **Chain of Responsibility** pattern. Each handler (`APMLogHandler`, `ApplicationLogHandler`, `RequestLogHandler`, `NullLogHandler`) checks if it can process a log line and, if not, passes it to the next handler.

Each handler uses a corresponding aggregator:
- `APMLogAggregator`
- `ApplicationLogAggregator`
- `RequestLogAggregator`

These aggregators implement a shared interface: `LogAggregator`.

---

## Sample Input Log Lines

```
timestamp=2024-04-12T22:00:00Z metric=cpu_usage_percent value=72
timestamp=2024-04-12T22:00:01Z level=INFO message="Starting application"
timestamp=2024-04-12T22:00:02Z request_method=POST request_url="/api/update" response_status=200 response_time_ms=120
```

---

## 👩‍💻 Developed by

**Jahnavi Kedia**  
CMPE 202 – Individual Project  
San Jose State University
