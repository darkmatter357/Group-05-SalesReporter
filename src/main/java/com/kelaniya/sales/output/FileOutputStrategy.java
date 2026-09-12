package com.kelaniya.sales.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileOutputStrategy implements OutputStrategy {

    private final String targetFilePath;

    public FileOutputStrategy(String targetFilePath) {
        if (targetFilePath == null || targetFilePath.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Target file path cannot be null or empty."
            );
        }

        this.targetFilePath = targetFilePath.trim();
    }

    public String getTargetFilePath() {
        return targetFilePath;
    }

    @Override
    public void output(String reportContent) throws IOException {
        if (reportContent == null) {
            throw new IOException("Report content cannot be null.");
        }

        File targetFile = new File(targetFilePath);
        File parentDirectory = targetFile.getParentFile();

        if (parentDirectory != null && !parentDirectory.exists()) {
            boolean created = parentDirectory.mkdirs();

            if (!created && !parentDirectory.exists()) {
                throw new IOException(
                        "Failed to create parent directories for: "
                                + targetFilePath
                );
            }
        }

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(targetFile))) {
            writer.write(reportContent);
        } catch (IOException e) {
            throw new IOException(
                    "Failed to write sales report to file: "
                            + targetFilePath,
                    e
            );
        }

        System.out.println(
                "Sales report successfully written to file: "
                        + targetFilePath
        );
    }
}