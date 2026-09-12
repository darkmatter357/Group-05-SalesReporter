package com.kelaniya.sales.io;

import com.kelaniya.sales.exception.CsvFormatException;
import com.kelaniya.sales.exception.ProductFileNotFoundException;
import com.kelaniya.sales.model.Product;

import java.util.List;

/**
 * Defines how product sales records are read.
 */
public interface ProductReader {

    List<Product> readProducts(String filePath)
            throws ProductFileNotFoundException, CsvFormatException;
}