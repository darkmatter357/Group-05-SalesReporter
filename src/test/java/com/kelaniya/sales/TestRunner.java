package com.kelaniya.sales;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestRunner {

    public static void main(String[] args) {
        SalesReporterTests tests = new SalesReporterTests();
        int passed = 0;
        int failed = 0;

        for (Method method : SalesReporterTests.class.getDeclaredMethods()) {
            if (!method.getName().startsWith("test")
                    || method.getParameterCount() != 0) {
                continue;
            }

            try {
                method.invoke(tests);
                System.out.println("[PASS] " + method.getName());
                passed++;
            } catch (InvocationTargetException e) {
                System.err.println(
                        "[FAIL] " + method.getName()
                                + ": " + e.getCause()
                );
                failed++;
            } catch (ReflectiveOperationException e) {
                System.err.println(
                        "[ERROR] " + method.getName() + ": " + e
                );
                failed++;
            }
        }

        System.out.printf(
                "Analytics tests: Passed: %d | Failed: %d%n",
                passed, failed
        );

        if (failed > 0 || passed == 0) {
            System.exit(1);
        }
    }
}