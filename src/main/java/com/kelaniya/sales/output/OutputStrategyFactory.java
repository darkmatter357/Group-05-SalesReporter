package com.kelaniya.sales.output;

import com.kelaniya.sales.exception.InvalidOutputMethodException;

import java.util.Locale;

public class OutputStrategyFactory {

    public static OutputStrategy createStrategy(
            String method,
            String outputPath
    ) throws InvalidOutputMethodException {

        if (method == null || method.trim().isEmpty()) {
            throw new InvalidOutputMethodException(
                    "Output method must be specified ('console' or 'file')."
            );
        }

        String normalizedMethod = method.trim().toLowerCase(Locale.ROOT);

        switch (normalizedMethod) {
            case "console":
                return new ConsoleOutputStrategy();

            case "file":
                if (outputPath == null || outputPath.trim().isEmpty()) {
                    throw new InvalidOutputMethodException(
                            "Output file path is required when "
                                    + "output-method is 'file'."
                    );
                }

                return new FileOutputStrategy(outputPath);

            default:
                throw new InvalidOutputMethodException(
                        "Invalid output method '" + method
                                + "'. Supported methods are 'console' or 'file'."
                );
        }
    }
}