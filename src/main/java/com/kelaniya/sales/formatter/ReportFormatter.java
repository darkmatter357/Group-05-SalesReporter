package com.kelaniya.sales.formatter;

import com.kelaniya.sales.model.SalesSummary;

public interface ReportFormatter {
    String format(SalesSummary summary);
}