package com.kelaniya.sales.output;

import java.io.IOException;

public interface OutputStrategy {
    void output(String reportContent) throws IOException;
}