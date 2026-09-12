package com.kelaniya.sales.formatter;

import com.kelaniya.sales.model.Product;
import com.kelaniya.sales.model.SalesSummary;

import java.util.Locale;
import java.util.Map;

public class PlainTextReportFormatter implements ReportFormatter {

    private static final String BORDER =
            "============================================";

    @Override
    public String format(SalesSummary summary) {
        StringBuilder report = new StringBuilder();

        report.append(BORDER).append("\n");
        report.append("        PRODUCT SALES SUMMARY REPORT\n");
        report.append(BORDER).append("\n");

        if (summary == null || summary.getProducts().isEmpty()) {
            report.append("\nNo product sales data available to display.\n");
            report.append(BORDER);
            return report.toString();
        }

        report.append("--- Revenue Per Product ---\n");

        for (Product product : summary.getProducts()) {
            report.append(String.format(
                    Locale.US,
                    "%-6s%-20s%-14s$%6.2f\n",
                    product.getProductId(),
                    product.getProductName(),
                    product.getCategory(),
                    product.getTotalRevenue()
            ));
        }

        report.append("\n--- Revenue Per Category ---\n");

        for (Map.Entry<String, Double> entry
                : summary.getCategoryRevenue().entrySet()) {
            report.append(String.format(
                    Locale.US,
                    "%-14s: $%.2f\n",
                    entry.getKey(),
                    entry.getValue()
            ));
        }

        report.append("\n--- Highlights ---\n");

        Product bestSelling = summary.getBestSellingProduct();

        if (bestSelling != null) {
            report.append(String.format(
                    Locale.US,
                    "Best-Selling Product : %-14s (%d units)\n",
                    bestSelling.getProductName(),
                    bestSelling.getQuantitySold()
            ));
        }

        Product highestRevenue = summary.getHighestRevenueProduct();

        if (highestRevenue != null) {
            report.append(String.format(
                    Locale.US,
                    "Highest Revenue      : %-14s ($%.2f)\n",
                    highestRevenue.getProductName(),
                    highestRevenue.getTotalRevenue()
            ));
        }

        report.append(String.format(
                Locale.US,
                "Grand Total Revenue  : $%.2f\n",
                summary.getGrandTotalRevenue()
        ));

        report.append(BORDER);
        return report.toString();
    }
}