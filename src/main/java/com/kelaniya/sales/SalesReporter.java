package com.kelaniya.sales;

import com.kelaniya.sales.exception.CsvFormatException;
import com.kelaniya.sales.exception.InvalidOutputMethodException;
import com.kelaniya.sales.exception.ProductFileNotFoundException;
import com.kelaniya.sales.formatter.PlainTextReportFormatter;
import com.kelaniya.sales.formatter.ReportFormatter;
import com.kelaniya.sales.io.CsvProductReader;
import com.kelaniya.sales.io.ProductReader;
import com.kelaniya.sales.model.Product;
import com.kelaniya.sales.model.SalesSummary;
import com.kelaniya.sales.output.OutputStrategy;
import com.kelaniya.sales.output.OutputStrategyFactory;
import com.kelaniya.sales.service.SalesAnalysisService;
import com.kelaniya.sales.service.SalesAnalysisServiceImpl;

import java.io.IOException;
import java.util.List;

public class SalesReporter {

    private final ProductReader productReader;
    private final SalesAnalysisService analysisService;
    private final ReportFormatter reportFormatter;

    public SalesReporter(
            ProductReader productReader,
            SalesAnalysisService analysisService,
            ReportFormatter reportFormatter
    ) {
        this.productReader = productReader;
        this.analysisService = analysisService;
        this.reportFormatter = reportFormatter;
    }

    public void run(
            String csvFilePath,
            String outputMethod,
            String outputFilePath
    ) throws ProductFileNotFoundException,
            CsvFormatException,
            InvalidOutputMethodException,
            IOException {

        OutputStrategy outputStrategy =
                OutputStrategyFactory.createStrategy(
                        outputMethod, outputFilePath
                );

        List<Product> products =
                productReader.readProducts(csvFilePath);

        SalesSummary summary =
                analysisService.generateSummary(products);

        String report = reportFormatter.format(summary);

        outputStrategy.output(report);
    }

    public static void main(String[] args) {
        if (args == null || args.length < 2 || args.length > 3) {
            printUsage();
            System.exit(1);
            return;
        }

        String csvFilePath = args[0];
        String outputMethod = args[1];
        String outputFilePath = args.length == 3 ? args[2] : null;

        try {
            SalesReporter application = new SalesReporter(
                    new CsvProductReader(),
                    new SalesAnalysisServiceImpl(),
                    new PlainTextReportFormatter()
            );

            application.run(
                    csvFilePath, outputMethod, outputFilePath
            );

        } catch (ProductFileNotFoundException
                 | CsvFormatException
                 | InvalidOutputMethodException e) {

            System.err.println("[ERROR] " + e.getMessage());
            System.exit(1);

        } catch (IOException e) {
            System.err.println(
                    "[ERROR] Failed to write the report: "
                            + e.getMessage()
            );
            System.exit(1);

        } catch (Exception e) {
            System.err.println(
                    "[UNEXPECTED ERROR] " + e.getMessage()
            );
            System.exit(1);
        }
    }

    private static void printUsage() {
        System.err.println("Product Sales Report Generator");
        System.err.println(
                "Usage: java -cp bin SalesReporter "
                        + "<csv-file-path> <output-method> "
                        + "[output-file-path]"
        );
        System.err.println("Output method: console or file");
        System.err.println(
                "An output file path is required for file mode."
        );
        System.err.println();
        System.err.println("Examples:");
        System.err.println(
                "java -cp bin SalesReporter "
                        + "data/sample_sales.csv console"
        );
        System.err.println(
                "java -cp bin SalesReporter "
                        + "data/sample_sales.csv file output/report.txt"
        );
    }
}