package com.kelaniya.sales.output;

import java.io.IOException;

public class ConsoleOutputStrategy implements OutputStrategy {

    @Override
    public void output(String reportContent) throws IOException {
        if (reportContent == null) {
            throw new IOException("Report content cannot be null.");
        }

        System.out.println(reportContent);
    }
}