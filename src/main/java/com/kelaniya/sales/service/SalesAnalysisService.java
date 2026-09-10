package com.kelaniya.sales.service;

import com.kelaniya.sales.model.Product;
import com.kelaniya.sales.model.SalesSummary;

import java.util.List;

/**
 * Service interface for analyzing product sales records.
 * Follows Interface Segregation Principle (ISP) and Dependency Inversion Principle (DIP).
 */
public interface SalesAnalysisService {

    /**
     * Analyzes a list of products and computes summary metrics.
     *
     * @param products list of products to analyze
     * @return populated SalesSummary instance
     */
    SalesSummary generateSummary(List<Product> products);
}
