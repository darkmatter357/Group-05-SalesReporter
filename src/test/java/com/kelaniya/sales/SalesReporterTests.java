package com.kelaniya.sales;

import com.kelaniya.sales.model.Product;
import com.kelaniya.sales.model.SalesSummary;
import com.kelaniya.sales.service.SalesAnalysisServiceImpl;

import java.util.Arrays;
import java.util.List;

public class SalesReporterTests {

    private SalesSummary sampleSummary() {
        List<Product> products = Arrays.asList(
                new Product("P001", "Wireless Mouse", "Electronics", 12, 25.50),
                new Product("P002", "Notebook", "Stationery", 35, 3.75),
                new Product("P003", "USB Hub", "Electronics", 8, 18.00),
                new Product("P004", "Ballpoint Pen", "Stationery", 100, 0.50),
                new Product("P005", "HDMI Cable", "Electronics", 20, 12.00)
        );

        return new SalesAnalysisServiceImpl().generateSummary(products);
    }

    private void assertAmount(double expected, double actual) {
        if (!Double.isFinite(actual)
                || Math.abs(expected - actual) > 0.001) {
            throw new AssertionError(
                    "Expected " + expected + ", but got " + actual
            );
        }
    }

    public void testProductRevenueCalculation() {
        Product product = new Product(
                "P001", "Wireless Mouse", "Electronics", 12, 25.50
        );

        assertAmount(306.00, product.getTotalRevenue());
    }

    public void testGrandTotalRevenue() {
        assertAmount(871.25, sampleSummary().getGrandTotalRevenue());
    }

    public void testCategoryRevenue() {
        SalesSummary summary = sampleSummary();

        assertAmount(690.00, summary.getCategoryRevenue().get("Electronics"));
        assertAmount(181.25, summary.getCategoryRevenue().get("Stationery"));
    }

    public void testBestSellingProduct() {
        Product bestSeller = sampleSummary().getBestSellingProduct();

        if (bestSeller == null
                || !"P004".equals(bestSeller.getProductId())
                || bestSeller.getQuantitySold() != 100) {
            throw new AssertionError(
                    "Expected Ballpoint Pen (P004), with 100 units."
            );
        }
    }

    public void testHighestRevenueProduct() {
        Product highest = sampleSummary().getHighestRevenueProduct();

        if (highest == null || !"P001".equals(highest.getProductId())) {
            throw new AssertionError(
                    "Expected Wireless Mouse (P001) as highest revenue product."
            );
        }

        assertAmount(306.00, highest.getTotalRevenue());
    }
}