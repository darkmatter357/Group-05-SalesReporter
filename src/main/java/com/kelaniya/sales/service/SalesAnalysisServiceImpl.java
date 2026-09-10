package com.kelaniya.sales.service;

import com.kelaniya.sales.model.Product;
import com.kelaniya.sales.model.SalesSummary;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of SalesAnalysisService.
 * Follows Single Responsibility Principle (SRP) by focusing purely on computing statistical metrics.
 */
public class SalesAnalysisServiceImpl implements SalesAnalysisService {

    @Override
    public SalesSummary generateSummary(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return new SalesSummary(products, new LinkedHashMap<>(), null, null, 0.0);
        }

        Map<String, Double> categoryRevenue = new LinkedHashMap<>();
        Product bestSellingProduct = null;
        Product highestRevenueProduct = null;
        double grandTotalRevenue = 0.0;

        for (Product product : products) {
            double productRevenue = product.getTotalRevenue();
            grandTotalRevenue += productRevenue;

            // Accumulate revenue per category
            categoryRevenue.put(
                    product.getCategory(),
                    categoryRevenue.getOrDefault(product.getCategory(), 0.0) + productRevenue
            );

            // Determine best-selling product (highest quantity sold)
            if (bestSellingProduct == null || product.getQuantitySold() > bestSellingProduct.getQuantitySold()) {
                bestSellingProduct = product;
            }

            // Determine highest revenue product
            if (highestRevenueProduct == null || productRevenue > highestRevenueProduct.getTotalRevenue()) {
                highestRevenueProduct = product;
            }
        }

        return new SalesSummary(
                products,
                categoryRevenue,
                bestSellingProduct,
                highestRevenueProduct,
                grandTotalRevenue
        );
    }
}
