/**
 * Root entrypoint alias allowing direct execution via:
 * java SalesReporter <csv-file-path> <output-method> [output-file-path]
 */
public class SalesReporter {
    public static void main(String[] args) {
        com.kelaniya.sales.SalesReporter.main(args);
    }
}
