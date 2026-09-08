package com.kelaniya.sales.exception;

/**
 * Thrown when a row (or the overall structure) of the input CSV file is malformed:
 * wrong number of columns, non-numeric quantity/price, or invalid product field values.
 *
 * Owned by: Member 3 (Exception Handling)
 */
public class CsvFormatException extends Exception {

    public CsvFormatException(String message) {
        super(message);
    }

    public CsvFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
