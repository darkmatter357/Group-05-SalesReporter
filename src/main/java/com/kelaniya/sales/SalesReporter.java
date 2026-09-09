package com.kelaniya.sales;

import com.kelaniya.sales.exception.CsvFormatException;
import com.kelaniya.sales.exception.InvalidOutputMethodException;
import com.kelaniya.sales.exception.ProductFileNotFoundException;
import com.kelaniya.sales.io.CsvProductReader;
import com.kelaniya.sales.io.ProductReader;
import com.kelaniya.sales.model.Product;
import com.kelaniya.sales.output.OutputStrategy;
import com.kelaniya.sales.output.OutputStrategyFactory;
import com.kelaniya.sales.report.ReportFormatter;
import com.kelaniya.sales.report.SalesSummary;
import com.kelaniya.sales.report.SalesSummaryCalculator;

import java.io.IOException;
import java.util.List;

/**
 * Main application orchestrator for the Command-Line Product Sales Report Generator.
 * Coordinates input parsing, file reading, business logic, formatting, and output dispatching,
 * and provides the console (CLI) entry point with user-friendly error handling.
 *
 * Owned by: Member 3 (Console Interface, Exception Handling)
 */
public class SalesReporter {

    private final ProductReader productReader;
    private final SalesSummaryCalculator salesSummaryCalculator;
    private final ReportFormatter reportFormatter;

    public SalesReporter(ProductReader productReader,
                          SalesSummaryCalculator salesSummaryCalculator,
                          ReportFormatter reportFormatter) {
        this.productReader = productReader;
        this.salesSummaryCalculator = salesSummaryCalculator;
        this.reportFormatter = reportFormatter;
    }

    /**
     * Executes the sales reporting workflow.
     *
     * @param csvFilePath    path to the CSV data file
     * @param outputMethod   "console" or "file"
     * @param outputFilePath output path (required if outputMethod is "file")
     * @throws ProductFileNotFoundException if the CSV file cannot be found or read
     * @throws CsvFormatException           if the CSV content is malformed
     * @throws InvalidOutputMethodException if the output method/arguments are invalid
     * @throws IOException                  if writing the report to its destination fails
     */
    public void run(String csvFilePath, String outputMethod, String outputFilePath)
            throws ProductFileNotFoundException, CsvFormatException, InvalidOutputMethodException, IOException {
        // 1. Resolve output strategy
        OutputStrategy outputStrategy = OutputStrategyFactory.createStrategy(outputMethod, outputFilePath);

        // 2. Read products from CSV
        List<Product> products = productReader.readProducts(csvFilePath);

        // 3. Compute summary statistics
        SalesSummary summary = salesSummaryCalculator.calculateSummary(products);

        // 4. Format summary report
        String formattedReport = reportFormatter.format(summary);

        // 5. Output report
        outputStrategy.output(formattedReport);
    }

    /**
     * Main entry point for command-line execution.
     *
     * @param args Command line arguments: <csv-file-path> <output-method> [output-file-path]
     */
    public static void main(String[] args) {
        if (args == null || args.length < 2) {
            printUsage();
            System.exit(1);
            return;
        }

        String csvFilePath = args[0];
        String outputMethod = args[1];
        String outputFilePath = args.length >= 3 ? args[2] : null;

        try {
            SalesReporter app = new SalesReporter(
                    new CsvProductReader(),
                    new SalesSummaryCalculator(),
                    new ReportFormatter()
            );

            app.run(csvFilePath, outputMethod, outputFilePath);

        } catch (ProductFileNotFoundException | CsvFormatException | InvalidOutputMethodException e) {
            System.err.println("\n[ERROR] " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("\n[ERROR] Failed to write the report: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("\n[UNEXPECTED ERROR] " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    private static void printUsage() {
        System.err.println("===============================================================================");
        System.err.println("Product Sales Report Generator - SENG 21222");
        System.err.println("===============================================================================");
        System.err.println("Usage:");
        System.err.println("  java SalesReporter <csv-file-path> <output-method> [output-file-path]");
        System.err.println();
        System.err.println("Arguments:");
        System.err.println("  <csv-file-path>     : Path to the input CSV file");
        System.err.println("  <output-method>     : 'console' or 'file'");
        System.err.println("  [output-file-path]  : Required only when output-method is 'file'");
        System.err.println();
        System.err.println("Examples:");
        System.err.println("  java SalesReporter data/sample_sales.csv console");
        System.err.println("  java SalesReporter data/sample_sales.csv file output/report.txt");
        System.err.println("===============================================================================");
    }
}
