package com.kelaniya.sales.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object holding computed sales summary metrics.
 * Follows Single Responsibility Principle (SRP) by encapsulating the computed analytical results.
 */
public class SalesSummary {
    private final List<Product> products;
    private final Map<String, Double> categoryRevenue;
    private final Product bestSellingProduct;
    private final Product highestRevenueProduct;
    private final double grandTotalRevenue;

    public SalesSummary(List<Product> products,
                        Map<String, Double> categoryRevenue,
                        Product bestSellingProduct,
                        Product highestRevenueProduct,
                        double grandTotalRevenue) {
        this.products = products != null ? Collections.unmodifiableList(products) : Collections.emptyList();
        this.categoryRevenue = categoryRevenue != null ? Collections.unmodifiableMap(categoryRevenue) : Collections.emptyMap();
        this.bestSellingProduct = bestSellingProduct;
        this.highestRevenueProduct = highestRevenueProduct;
        this.grandTotalRevenue = grandTotalRevenue;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Map<String, Double> getCategoryRevenue() {
        return categoryRevenue;
    }

    public Product getBestSellingProduct() {
        return bestSellingProduct;
    }

    public Product getHighestRevenueProduct() {
        return highestRevenueProduct;
    }

    public double getGrandTotalRevenue() {
        return grandTotalRevenue;
    }
}
