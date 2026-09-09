# Product Sales Report Generator

**Course:** SENG 21222 – Software Construction (Assignment 1 – 2026)  
**Institution:** University of Kelaniya – Sri Lanka (Faculty of Science)  
**Instructor:** Eng. Sudam Kalpage

\---

#### Project Overview



A modular, high-performance command-line application built in **Java** that reads daily product sales data from a CSV file, computes summary analytics (individual product revenues, category revenues, best-seller detection, highest revenue earner, and grand total revenue), and dispatches the formatted report either to the **console** or directly to a **file**.

\---

#### Architecture \& SOLID Principles



The system is designed in accordance with clean code practices and SOLID design principles:

```
src/
├── main/java/com/kelaniya/sales/
│   ├── model/
│   │   ├── Product.java                  # Product entity \\\& line revenue calculation
│   │   └── SalesSummary.java             # Immutable DTO holding computed summary statistics
│   ├── reader/
│   │   ├── ProductReader.java            # Product reading abstraction
│   │   └── CsvProductReader.java         # CSV parsing, header skipping \\\& validation
│   ├── service/
│   │   ├── SalesAnalysisService.java     # Analytics computation interface
│   │   └── SalesAnalysisServiceImpl.java # Analytics engine (totals, categories, highlights)
│   ├── formatter/
│   │   ├── ReportFormatter.java          # Presentation layer interface
│   │   └── PlainTextReportFormatter.java # ASCII table layout \\\& currency formatting
│   ├── output/
│   │   ├── OutputStrategy.java           # Strategy interface for report export
│   │   ├── ConsoleOutputStrategy.java    # Terminal output implementation
│   │   ├── FileOutputStrategy.java       # File output implementation
│   │   └── OutputStrategyFactory.java    # Factory for resolving output destination
│   ├── exception/
│   │   ├── SalesReportException.java     # Base checked domain exception
│   │   ├── DataFormatException.java      # CSV structural/type validation errors
│   │   ├── FileProcessingException.java  # File not found / I/O errors
│   │   └── InvalidInputException.java    # CLI arguments validation errors
│   └── SalesReporter.java                # Orchestrator \\\& CLI main entry point
└── test/java/com/kelaniya/sales/
    ├── SalesReporterTests.java           # Comprehensive unit \\\& integration tests
    └── TestRunner.java                   # Zero-dependency test execution engine
```

#### SOLID Principles Breakdown



|Principle|Implementation in Codebase|
|-|-|
|**S - Single Responsibility Principle (SRP)**|Each class has one distinct responsibility. `Product` models item data; `CsvProductReader` strictly parses CSV; `SalesAnalysisServiceImpl` strictly performs mathematical aggregations; `PlainTextReportFormatter` strictly handles text formatting; `ConsoleOutputStrategy`/`FileOutputStrategy` handle dispatching.|
|**O - Open/Closed Principle (OCP)**|The output system uses the **Strategy Pattern** (`OutputStrategy`). New output channels (e.g., `EmailOutputStrategy`, `JsonOutputStrategy`, `DatabaseOutputStrategy`) can be added without modifying existing report logic or core classes.|
|**L - Liskov Substitution Principle (LSP)**|All implementations of `OutputStrategy` (`ConsoleOutputStrategy`, `FileOutputStrategy`) can be substituted interchangeably by `SalesReporter` without altering the correctness of the program.|
|**I - Interface Segregation Principle (ISP)**|Interfaces are lean and specialized (`ProductReader`, `SalesAnalysisService`, `ReportFormatter`, `OutputStrategy`) instead of one monolithic interface.|
|**D - Dependency Inversion Principle (DIP)**|High-level orchestrator (`SalesReporter`) depends on abstractions (`ProductReader`, `SalesAnalysisService`, `ReportFormatter`, `OutputStrategy`) rather than hardcoded concrete implementations.|

\---

#### Group Member Task Division



|Member|Focus Area|Implemented Components|
|-|-|-|
|**Member 1**|**Core Logic \& Business Analytics**|`Product.java`, `SalesSummary.java`, `SalesAnalysisService.java`, `SalesAnalysisServiceImpl.java` (Revenue per product, category sums, best seller, highest revenue, grand total).|
|**Member 2**|**File I/O, Strategy Pattern \& Unit Testing**|`ProductReader.java`, `CsvProductReader.java`, `OutputStrategy.java`, `ConsoleOutputStrategy.java`, `FileOutputStrategy.java`, `OutputStrategyFactory.java`, `SalesReporterTests.java`.|
|**Member 3**|**Console Interface, Error Handling \& Formatting**|`SalesReporter.java`, `PlainTextReportFormatter.java`, Exception Hierarchy (`SalesReportException`, `DataFormatException`, `FileProcessingException`, `InvalidInputException`), `TestRunner.java`, `README.md`.|

\---

#### How to Build \& Run



##### 1\. Compile the Project

```bash
# Compile all source and test files to bin/ directory
javac -d bin src/main/java/com/kelaniya/sales/\\\*\\\*/\\\*.java src/test/java/com/kelaniya/sales/\\\*.java SalesReporter.java
```

##### 2\. Run the Unit Test Suite

```bash
java -cp bin com.kelaniya.sales.TestRunner
```

*Outputs green `\\\[PASS]` status for all 20 test cases covering revenue math, best seller detection, category grouping, CSV parsing, header skipping, error handling, and file writing.*

##### 3\. Generate Sales Report to Console

```bash
java -cp bin SalesReporter data/sample\\\_sales.csv console
```

**Expected Output:**

```
============================================
        PRODUCT SALES SUMMARY REPORT
============================================
--- Revenue Per Product ---
P001  Wireless Mouse      Electronics   $306.00
P002  Notebook            Stationery    $131.25
P003  USB Hub             Electronics   $144.00
P004  Ballpoint Pen       Stationery    $ 50.00
P005  HDMI Cable          Electronics   $240.00

--- Revenue Per Category ---
Electronics   : $690.00
Stationery    : $181.25

--- Highlights ---
Best-Selling Product : Ballpoint Pen  (100 units)
Highest Revenue      : Wireless Mouse ($306.00)
Grand Total Revenue  : $871.25
============================================
```

##### 4\. Generate Sales Report to a File

```bash
java -cp bin SalesReporter data/sample\\\_sales.csv file output/report.txt
```

\---

#### Error Handling Scenarios



The tool handles errors gracefully and exits with code 1 and user-friendly error messages:

1. **Non-existent CSV File:**

```bash
   java -cp bin SalesReporter data/missing.csv console
   # Output: \\\[ERROR] CSV file not found at path: data/missing.csv
   ```

2. **Missing Output File Path for File Mode:**

```bash
   java -cp bin SalesReporter data/sample\\\_sales.csv file
   # Output: \\\[ERROR] Output file path is required when output-method is 'file'.
   ```

3. **Invalid Output Method:**

```bash
   java -cp bin SalesReporter data/sample\\\_sales.csv cloud
   # Output: \\\[ERROR] Invalid output method 'cloud'. Supported methods are 'console' or 'file'.
   ```

4. **Malformed CSV Row:**

```bash
   java -cp bin SalesReporter data/invalid\\\_row.csv console
   # Output: \\\[ERROR] Invalid CSV format at line 3: Expected 5 columns...
   ```

\---


