Product Sales Report Generator
Course: SENG 21222 – Software Construction (Assignment 1 – 2026)
Institution: University of Kelaniya – Sri Lanka (Faculty of Science)
Instructor: Eng. Sudam Kalpage
---
Project Overview
A modular, command-line Java application that reads daily product sales data from a CSV
file, computes summary analytics (per-product and per-category revenue, best-seller
detection, highest-revenue product, and grand total revenue), and dispatches the
formatted report to either the console or a file.
---
Architecture
```
src/
├── main/java/com/kelaniya/sales/
│   ├── SalesReporter.java                        # CLI entry point & orchestrator      [Member 3]
│   ├── model/
│   │   └── Product.java                          # Product entity + line revenue       [Member 1]
│   ├── report/
│   │   ├── SalesSummary.java                     # Immutable summary DTO               [Member 1]
│   │   ├── SalesSummaryCalculator.java            # Analytics engine                    [Member 1]
│   │   └── ReportFormatter.java                  # Plain-text report layout            [Member 1]
│   ├── io/
│   │   ├── ProductReader.java                    # Reading abstraction (DIP)           [Member 2]
│   │   └── CsvProductReader.java                 # CSV parsing & validation            [Member 2]
│   ├── output/
│   │   ├── OutputStrategy.java                   # Strategy interface (OCP)            [Member 2]
│   │   ├── ConsoleOutputStrategy.java             # Console output                      [Member 2]
│   │   ├── FileOutputStrategy.java               # File output                          [Member 2]
│   │   └── OutputStrategyFactory.java             # Resolves strategy from CLI args     [Member 2]
│   └── exception/
│       ├── ProductFileNotFoundException.java     # Input file missing/unreadable       [Member 3]
│       ├── CsvFormatException.java               # Malformed CSV content                [Member 3]
│       └── InvalidOutputMethodException.java     # Bad output method/args               [Member 3]
└── test/java/com/kelaniya/sales/
    ├── report/SalesSummaryCalculatorTest.java     # Core logic tests                     [Member 1]
    └── io/CsvProductReaderTest.java               # File I/O & output tests              [Member 2]
```
---
Group Member Task Division
Member	Focus Area	Files
Member 1	Core logic: reading the product file, computing the summary, writing the report	`model/Product.java`, `report/SalesSummary.java`, `report/SalesSummaryCalculator.java`, `report/ReportFormatter.java`, `test/report/SalesSummaryCalculatorTest.java`
Member 2	File I/O, unit testing, and applying SOLID principles	`io/ProductReader.java`, `io/CsvProductReader.java`, `output/OutputStrategy.java`, `output/ConsoleOutputStrategy.java`, `output/FileOutputStrategy.java`, `output/OutputStrategyFactory.java`, `test/io/CsvProductReaderTest.java`
Member 3	Console interface, exception handling, and documentation	`SalesReporter.java`, `exception/ProductFileNotFoundException.java`, `exception/CsvFormatException.java`, `exception/InvalidOutputMethodException.java`, `README.md`
> All paths above are relative to `src/main/java/com/kelaniya/sales/` (and `src/test/java/com/kelaniya/sales/` for test files).
SOLID Principles Applied
Principle	Where
SRP	Each class has one reason to change — `Product` models data, `CsvProductReader` only parses, `SalesSummaryCalculator` only computes, `ReportFormatter` only formats, each `OutputStrategy` only dispatches.
OCP	New output channels (Email, JSON, DB) can be added by implementing `OutputStrategy` without touching `SalesReporter`.
LSP	Any `OutputStrategy` implementation is interchangeable wherever the interface is used.
ISP	`ProductReader` and `OutputStrategy` are small, focused interfaces.
DIP	`SalesReporter` depends on the `ProductReader` and `OutputStrategy` abstractions, not concrete classes.
---
How to Build & Run
```bash
# Compile
javac -d bin $(find src -name "*.java")

# Run Member 1's tests
java -cp bin com.kelaniya.sales.report.SalesSummaryCalculatorTest

# Run Member 2's tests
java -cp bin com.kelaniya.sales.io.CsvProductReaderTest

# Generate a report to the console
java -cp bin com.kelaniya.sales.SalesReporter data/sample_sales.csv console

# Generate a report to a file
java -cp bin com.kelaniya.sales.SalesReporter data/sample_sales.csv file output/report.txt
```
Sample Output
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
---
Error Handling
Scenario	Exception thrown	Example
CSV file missing/unreadable	`ProductFileNotFoundException`	`[ERROR] CSV file not found at path: data/missing.csv`
Malformed CSV row/data	`CsvFormatException`	`[ERROR] Invalid CSV format at line 3: Expected 5 columns...`
Invalid or missing output method/path	`InvalidOutputMethodException`	`[ERROR] Invalid output method 'cloud'. Supported methods are 'console' or 'file'.`
`SalesReporter.main()` catches each of these along with a general `IOException` (for write
failures) and prints a clean, user-friendly message with exit code `1` — no raw stack
traces reach the user for expected error conditions.
---
Recommended Git Workflow
Since this is a genuine 3-way file split, each member can work in their own branch and open
a pull request into `main`, or push directly to their assigned files if branch protection
isn't set up:
`git checkout -b member1-core-logic` (etc. per member)
Add only your assigned files under the paths listed above
Commit with a clear message, e.g. `feat: implement Product, SalesSummary, and SalesSummaryCalculator`
Open a PR into `main` — the project only fully compiles once all three branches are merged, since `SalesReporter.java` depends on all three members' classes
Shared Resources
`data/*.csv` (sample, invalid-row, and empty test fixtures) and the `output/` directory are
shared resources needed for running the app and both test suites — not owned by a single
member, and should be added to `main` first (or by whoever creates the initial repo scaffold).
