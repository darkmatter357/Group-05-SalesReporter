package com.kelaniya.sales.exception;

/**
 * Thrown when the product data file specified by the user cannot be located or opened
 * (e.g. wrong path, file deleted, or insufficient permissions).
 *
 * Owned by: Member 3 (Exception Handling)
 */
public class ProductFileNotFoundException extends Exception {

    public ProductFileNotFoundException(String message) {
        super(message);
    }

    public ProductFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
