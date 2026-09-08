package com.kelaniya.sales.exception;

/**
 * Thrown when the requested output method is missing, unrecognized (not "console" or "file"),
 * or is "file" but no output file path was supplied.
 *
 * Owned by: Member 3 (Exception Handling)
 */
public class InvalidOutputMethodException extends Exception {

    public InvalidOutputMethodException(String message) {
        super(message);
    }

    public InvalidOutputMethodException(String message, Throwable cause) {
        super(message, cause);
    }
}
