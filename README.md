# Product Sales Report Generator

SENG 21222 – Software Construction, Assignment 1 (2026)
University of Kelaniya

## Overview

A Java command-line application that reads product sales from CSV and
generates a report containing:

- Revenue per product
- Revenue per category
- Best-selling product by quantity
- Highest-revenue product
- Grand total revenue

Reports can be printed to the console or saved to a text file.

## Requirements

Install a Java Development Kit (JDK). Ensure both `java` and `javac`
are available in your terminal.

## Compile — Windows PowerShell

Run from the project root containing `src`, `data`, and the root
`SalesReporter.java` launcher:

```powershell
New-Item -ItemType Directory -Force bin | Out-Null
$sources = @(Get-ChildItem .\src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
$sources += (Resolve-Path .\SalesReporter.java).Path
javac -d bin $sources
```

Resolve any compilation errors before running the application.

## Run

Console report:

```powershell
java -cp bin SalesReporter data/sample_sales.csv console
```

Save to a file:

```powershell
java -cp bin SalesReporter data/sample_sales.csv file output/report.txt
```

Expected sample results:

- Grand total: $871.25
- Best seller: Ballpoint Pen, 100 units
- Highest revenue: Wireless Mouse, $306.00

## Tests

```powershell
java -cp bin com.kelaniya.sales.TestRunner
java -cp bin com.kelaniya.sales.io.CsvProductReaderTest
```

The analytics suite contains five tests covering product revenue,
category revenue, grand total, best seller, and highest revenue.

The I/O suite contains ten tests covering CSV reading, invalid input,
output strategy selection, and file writing.

## Structure

Production sources are under `src/main/java/com/kelaniya/sales`:

- `model`: product and summary data
- `service`: sales calculations
- `io`: CSV reader and reader interface
- `formatter`: report formatting interface and implementation
- `output`: console/file strategies and strategy factory
- `exception`: custom exceptions
- `SalesReporter.java`: CLI and application orchestration

Tests are under `src/test/java/com/kelaniya/sales`.

The root `SalesReporter.java` delegates to the packaged application.

## Error Handling

- `ProductFileNotFoundException`: missing or unreadable input file
- `CsvFormatException`: malformed CSV data
- `InvalidOutputMethodException`: invalid output method or missing destination
- `IOException`: output file failures

The CLI prints an error message and exits with status 1 on failure.

## Design

Reading, calculation, formatting, and output have separate responsibilities.

The CLI accepts reader, analytics, and formatter interfaces through its
constructor. Output strategies share a small interface.

New output strategies can be implemented without changing the calculation
logic. The factory must be updated to select a new strategy.

## Team Responsibilities

- Member 1: product model and sales analytics.
- Member 2: file I/O, output strategies, and I/O testing.
- Member 3: CLI, exception handling, formatting, test runner, and documentation.

The integration repair restores missing dependencies and connects the CLI
to the existing analytics classes. Actual contributions should be reported
accurately using the team's Git history.

## Repository

https://github.com/darkmatter357/Group-05-SalesReporter