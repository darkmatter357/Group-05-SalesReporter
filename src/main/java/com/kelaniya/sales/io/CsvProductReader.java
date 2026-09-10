package com.kelaniya.sales.io;

import com.kelaniya.sales.exception.CsvFormatException;
import com.kelaniya.sales.exception.ProductFileNotFoundException;
import com.kelaniya.sales.model.Product;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads and parses product sales records from a CSV file.
 * Handles header detection/skipping, blank-line skipping, and per-row validation.
 * Follows SRP by focusing strictly on parsing and validating CSV data.
 *
 * Owned by: Member 2 (File I/O)
 */
public class CsvProductReader implements ProductReader {

    private static final int EXPECTED_COLUMN_COUNT = 5;

    @Override
    public List<Product> readProducts(String filePath) throws ProductFileNotFoundException, CsvFormatException {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new ProductFileNotFoundException("CSV file path must not be null or empty.");
        }

        File file = new File(filePath.trim());
        if (!file.exists() || !file.isFile()) {
            throw new ProductFileNotFoundException("CSV file not found at path: " + filePath);
        }

        List<Product> products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            boolean isFirstNonEmptyLine = true;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String trimmedLine = line.trim();

                // Skip blank lines
                if (trimmedLine.isEmpty()) {
                    continue;
                }

                String[] tokens = trimmedLine.split(",");

                // Check for header row on the first non-empty line
                if (isFirstNonEmptyLine) {
                    isFirstNonEmptyLine = false;
                    if (isHeaderRow(tokens)) {
                        continue; // Skip header row
                    }
                }

                if (tokens.length < EXPECTED_COLUMN_COUNT) {
                    throw new CsvFormatException(String.format(
                            "Invalid CSV format at line %d: Expected %d columns (product_id, product_name, category, quantity_sold, unit_price), but found %d. Row content: \"%s\"",
                            lineNumber, EXPECTED_COLUMN_COUNT, tokens.length, trimmedLine
                    ));
                }

                String productId = tokens[0].trim();
                String productName = tokens[1].trim();
                String category = tokens[2].trim();
                int quantitySold;
                double unitPrice;

                try {
                    quantitySold = Integer.parseInt(tokens[3].trim());
                } catch (NumberFormatException e) {
                    throw new CsvFormatException(String.format(
                            "Invalid numeric value for quantity_sold at line %d: '%s'",
                            lineNumber, tokens[3].trim()
                    ));
                }

                try {
                    unitPrice = Double.parseDouble(tokens[4].trim());
                } catch (NumberFormatException e) {
                    throw new CsvFormatException(String.format(
                            "Invalid numeric value for unit_price at line %d: '%s'",
                            lineNumber, tokens[4].trim()
                    ));
                }

                try {
                    Product product = new Product(productId, productName, category, quantitySold, unitPrice);
                    products.add(product);
                } catch (IllegalArgumentException e) {
                    throw new CsvFormatException(String.format(
                            "Data validation error at line %d: %s", lineNumber, e.getMessage()
                    ));
                }
            }
        } catch (IOException e) {
            // Covers any I/O failure that occurs after the initial existence check
            // (e.g. permissions changing mid-read, disk errors).
            throw new ProductFileNotFoundException("Error reading CSV file at: " + filePath, e);
        }

        return products;
    }

    /**
     * Determines whether the given row tokens represent a header row.
     */
    private boolean isHeaderRow(String[] tokens) {
        if (tokens == null || tokens.length == 0) {
            return false;
        }
        String firstColumn = tokens[0].trim().toLowerCase();
        return firstColumn.contains("product_id") || firstColumn.contains("id") || firstColumn.contains("product");
    }
}
