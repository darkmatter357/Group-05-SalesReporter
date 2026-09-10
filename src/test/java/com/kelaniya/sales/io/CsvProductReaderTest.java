package com.kelaniya.sales.io;

import com.kelaniya.sales.exception.CsvFormatException;
import com.kelaniya.sales.exception.InvalidOutputMethodException;
import com.kelaniya.sales.exception.ProductFileNotFoundException;
import com.kelaniya.sales.model.Product;
import com.kelaniya.sales.output.ConsoleOutputStrategy;
import com.kelaniya.sales.output.FileOutputStrategy;
import com.kelaniya.sales.output.OutputStrategy;
import com.kelaniya.sales.output.OutputStrategyFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Zero-dependency unit test suite for Member 2's file I/O and Strategy-pattern output code:
 * {@link CsvProductReader}, {@link OutputStrategy} implementations, and {@link OutputStrategyFactory}.
 *
 * Assumes tests are run from the project root, so data/*.csv are resolvable as relative paths.
 *
 * Run directly with:
 *   java -cp bin com.kelaniya.sales.io.CsvProductReaderTest
 *
 * Owned by: Member 2 (File I/O, Unit Testing, SOLID)
 */
public class CsvProductReaderTest {

    private final ProductReader csvReader = new CsvProductReader();

    // ==========================================
    // 1. CSV READER & PARSING TESTS
    // ==========================================

    public void testCsvReaderValidFile() throws ProductFileNotFoundException, CsvFormatException {
        List<Product> products = csvReader.readProducts("data/sample_sales.csv");
        assertEquals(5, products.size(), "Sample sales CSV should contain 5 records");

        Product first = products.get(0);
        assertEquals("P001", first.getProductId(), "First product ID should be P001");
        assertEquals("Wireless Mouse", first.getProductName(), "First product name should match");
        assertEquals("Electronics", first.getCategory(), "First product category should match");
        assertEquals(12, first.getQuantitySold(), "First product quantity should be 12");
        assertEquals(25.50, first.getUnitPrice(), 0.001, "First product unit price should be 25.50");
    }

    public void testCsvReaderFileNotFoundThrowsException() {
        try {
            csvReader.readProducts("data/non_existent_file.csv");
            fail("Expected ProductFileNotFoundException for non-existent file");
        } catch (ProductFileNotFoundException expected) {
            // Success
        } catch (CsvFormatException e) {
            fail("Expected ProductFileNotFoundException but got: " + e.getClass().getSimpleName());
        }
    }

    public void testCsvReaderInvalidColumnsThrowsException() throws ProductFileNotFoundException {
        try {
            csvReader.readProducts("data/invalid_row.csv");
            fail("Expected CsvFormatException for row with missing columns");
        } catch (CsvFormatException expected) {
            // Success
        }
    }

    public void testCsvReaderEmptyFile() throws ProductFileNotFoundException, CsvFormatException {
        List<Product> products = csvReader.readProducts("data/empty.csv");
        assertTrue(products.isEmpty(), "Empty CSV (only header) should result in 0 products");
    }

    public void testCsvReaderNullPathThrowsException() {
        try {
            csvReader.readProducts(null);
            fail("Expected ProductFileNotFoundException for null path");
        } catch (ProductFileNotFoundException expected) {
            // Success
        } catch (CsvFormatException e) {
            fail("Expected ProductFileNotFoundException but got: " + e.getClass().getSimpleName());
        }
    }

    // ==========================================
    // 2. OUTPUT STRATEGY & FACTORY TESTS
    // ==========================================

    public void testOutputStrategyFactoryConsole() throws InvalidOutputMethodException {
        OutputStrategy strategy = OutputStrategyFactory.createStrategy("console", null);
        assertTrue(strategy instanceof ConsoleOutputStrategy, "Should create ConsoleOutputStrategy");
    }

    public void testOutputStrategyFactoryFile() throws InvalidOutputMethodException {
        OutputStrategy strategy = OutputStrategyFactory.createStrategy("file", "test_output.txt");
        assertTrue(strategy instanceof FileOutputStrategy, "Should create FileOutputStrategy");
    }

    public void testOutputStrategyFactoryFileMissingPathThrowsException() {
        try {
            OutputStrategyFactory.createStrategy("file", null);
            fail("Expected InvalidOutputMethodException when file output path is null");
        } catch (InvalidOutputMethodException expected) {
            // Success
        }
    }

    public void testOutputStrategyFactoryInvalidMethodThrowsException() {
        try {
            OutputStrategyFactory.createStrategy("database", null);
            fail("Expected InvalidOutputMethodException for invalid output method");
        } catch (InvalidOutputMethodException expected) {
            // Success
        }
    }

    public void testFileOutputStrategyWritesFile() throws IOException {
        String tempFilePath = "data/test_output_generated.txt";
        OutputStrategy fileStrategy = new FileOutputStrategy(tempFilePath);
        String testContent = "Test Sales Report Content";
        fileStrategy.output(testContent);

        File outputFile = new File(tempFilePath);
        assertTrue(outputFile.exists(), "Output file must exist on disk");

        String content = new String(Files.readAllBytes(outputFile.toPath()));
        assertEquals(testContent, content, "Written file content must match report content");

        // Clean up
        outputFile.delete();
    }

    // ==========================================
    // ASSERTION HELPERS
    // ==========================================

    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion Failed: " + message);
        }
    }

    private void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(String.format("Assertion Failed: %s [expected: <%s>, actual: <%s>]", message, expected, actual));
        }
    }

    private void assertEquals(double expected, double actual, double delta, String message) {
        if (Math.abs(expected - actual) > delta) {
            throw new AssertionError(String.format("Assertion Failed: %s [expected: <%.4f>, actual: <%.4f>, delta: <%.4f>]", message, expected, actual, delta));
        }
    }

    private void fail(String message) {
        throw new AssertionError("Assertion Failed: " + message);
    }

    // ==========================================
    // STANDALONE TEST RUNNER (zero dependencies)
    // ==========================================

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("  MEMBER 2 - FILE I/O TEST SUITE (CsvProductReaderTest)");
        System.out.println("===============================================================");

        CsvProductReaderTest testSuite = new CsvProductReaderTest();
        Method[] methods = CsvProductReaderTest.class.getDeclaredMethods();

        int passed = 0;
        int failed = 0;
        List<String> failedDetails = new ArrayList<>();

        for (Method method : methods) {
            if (method.getName().startsWith("test")) {
                try {
                    method.invoke(testSuite);
                    System.out.printf("  [PASS] %-45s%n", method.getName());
                    passed++;
                } catch (InvocationTargetException e) {
                    Throwable cause = e.getCause();
                    System.out.printf("  [FAIL] %-45s%n", method.getName());
                    failedDetails.add(method.getName() + " -> " + (cause != null ? cause.getMessage() : e.getMessage()));
                    failed++;
                } catch (Exception e) {
                    System.out.printf("  [ERROR] %-45s%n", method.getName());
                    failedDetails.add(method.getName() + " -> " + e.getMessage());
                    failed++;
                }
            }
        }

        System.out.println("===============================================================");
        System.out.printf("  TEST RESULTS: Total: %d | Passed: %d | Failed: %d%n", (passed + failed), passed, failed);
        System.out.println("===============================================================");

        if (failed > 0) {
            System.err.println("\nFAILURES:");
            for (String detail : failedDetails) {
                System.err.println("  - " + detail);
            }
            System.exit(1);
        } else {
            System.out.println("  ALL TESTS PASSED SUCCESSFULLY!\n");
        }
    }
}
