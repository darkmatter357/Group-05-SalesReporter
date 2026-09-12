# UNIVERSITY OF KELANIYA – SRI LANKA
## Faculty of Science — Department of Statistics & Computer Science / Software Engineering
### SENG 21222 – Software Construction
### Assignment 1 (2026) — Command-Line Product Sales Report Generator

---

**Repository URL:** [Insert your GitHub/Bitbucket repository URL here]  
**Submission Date:** [Insert Date e.g., September 10, 2026]  
**Lecturer / Course Instructor:** Eng. Sudam Kalpage  

---

## 📋 Group Member Details & Individual Contributions

### Member 1: [Full Name & Student Number]
* **Git Username:** `[Member 1 Git Username]`
* **Contribution Breakdown:**
  1. **Core Data Models:**
     - Designed and implemented `Product.java` (encapsulating `productId`, `productName`, `category`, `quantitySold`, `unitPrice` and `getTotalRevenue()`).
     - Designed and implemented `SalesSummary.java` (immutable Data Transfer Object holding all summary analytics).
  2. **Analytics & Computation Service:**
     - Designed `SalesAnalysisService.java` interface and implemented `SalesAnalysisServiceImpl.java`.
     - Implemented single-pass $O(N)$ calculation of:
       - Individual product line revenue (`quantity_sold × unit_price`).
       - Revenue per product category using `LinkedHashMap` to preserve category ordering.
       - Best-selling product detection (highest `quantity_sold`).
       - Highest revenue product detection (highest `total_revenue`).
       - Grand total sales revenue summation.
  3. **Object-Oriented & SOLID Design:**
     - Applied the **Single Responsibility Principle (SRP)** by keeping domain entities decoupled from file I/O and display formatting.

---

### Member 2: [Full Name & Student Number]
* **Git Username:** `[Member 2 Git Username]`
* **Contribution Breakdown:**
  1. **File I/O & CSV Parsing:**
     - Designed `ProductReader.java` interface and implemented `CsvProductReader.java`.
     - Handled CSV header detection and skipping, token trimming, and row validation.
  2. **Strategy Pattern for Report Output:**
     - Designed `OutputStrategy.java` interface.
     - Implemented `ConsoleOutputStrategy.java` for terminal rendering.
     - Implemented `FileOutputStrategy.java` for writing summary reports directly to disk.
     - Built `OutputStrategyFactory.java` to instantiate strategies based on CLI parameters.
  3. **Unit Testing & Test Suite:**
     - Implemented `SalesReporterTests.java` containing 20 unit and integration tests covering revenue calculations, best-seller detection, category groupings, CSV reader validation, and file writing.
  4. **SOLID Principles:**
     - Enforced **Open/Closed Principle (OCP)** (extensible output channels without modifying core logic) and **Liskov Substitution Principle (LSP)**.

---

### Member 3: [Full Name & Student Number]
* **Git Username:** `[Member 3 Git Username]`
* **Contribution Breakdown:**
  1. **CLI Orchestration & Entry Point:**
     - Implemented `SalesReporter.java` command-line orchestrator matching the exact assignment syntax: `java SalesReporter <csv-file-path> <output-method> [output-file-path]`.
  2. **Report Formatting:**
     - Designed `ReportFormatter.java` interface and `PlainTextReportFormatter.java`.
     - Formatted ASCII tables with column alignment, section headers, category breakdowns, highlights, and currency notation (`$%.2f`).
  3. **Exception Handling & Robustness:**
     - Built domain exception hierarchy (`SalesReportException`, `DataFormatException`, `FileProcessingException`, `InvalidInputException`).
     - Handled error states (file not found, bad CSV rows, missing parameters) with user-friendly messages and graceful exit status codes.
  4. **Testing Harness & Documentation:**
     - Built `TestRunner.java` for standalone zero-dependency test execution.
     - Authored `README.md` and submission documentation.

---

## 🏛️ System Architecture & Design Patterns

### 1. SOLID Design Principles Applied
* **Single Responsibility Principle (SRP):** Each class has a single, well-defined reason to change.
* **Open/Closed Principle (OCP):** New output formats (e.g., Email, JSON) can be plugged in via `OutputStrategy` without editing existing code.
* **Liskov Substitution Principle (LSP):** Any implementation of `OutputStrategy` can be used interchangeably by `SalesReporter`.
* **Interface Segregation Principle (ISP):** Clean, minimal interfaces (`ProductReader`, `SalesAnalysisService`, `ReportFormatter`, `OutputStrategy`).
* **Dependency Inversion Principle (DIP):** Orchestrator depends on interfaces rather than concrete implementations.

### 2. Output Verification

#### Command:
```bash
java -cp bin SalesReporter data/sample_sales.csv console
```

#### Output:
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

#### Test Suite Execution:
```
===============================================================
  RUNNING SENG 21222 SALES REPORTER UNIT TEST SUITE
===============================================================
  [PASS] testOutputStrategyFactoryInvalidMethodThrowsException
  [PASS] testOutputStrategyFactoryFileMissingPathThrowsException
  [PASS] testProductBlankFieldsThrowException
  [PASS] testOutputStrategyFactoryConsole
  [PASS] testRevenuePerCategoryCalculation
  [PASS] testProductNegativeQuantityThrowsException
  [PASS] testOutputStrategyFactoryFile
  [PASS] testBestSellingProductDetection
  [PASS] testGrandTotalRevenueCalculation
  [PASS] testProductRevenueZeroQuantity
  [PASS] testHighestRevenueProductDetection
  [PASS] testCsvReaderFileNotFoundThrowsException
  [PASS] testCsvReaderInvalidColumnsThrowsException
  [PASS] testProductRevenueCalculation
  [PASS] testProductNegativePriceThrowsException
  [PASS] testFileOutputStrategyWritesFile
  [PASS] testCsvReaderValidFile
  [PASS] testReportFormatterOutput
  [PASS] testEmptyProductsSummary
  [PASS] testCsvReaderEmptyFile
===============================================================
  TEST RESULTS: Total: 20 | Passed: 20 | Failed: 0
===============================================================
  ALL TESTS PASSED SUCCESSFULLY!
```
