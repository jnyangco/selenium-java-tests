package com.automation.utils;

import com.automation.config.ConfigReader;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ExtentReports Manager class
 */
public class ExtentManager {
    private static final Logger logger = LogManager.getLogger(ExtentManager.class);
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static String reportPath;

    /**
     * Initialize ExtentReports
     */
    public static void initializeExtentReports() {
        if (extent == null) {
            // Create report directory if it doesn't exist
            String reportDir = ConfigReader.getExtentReportPath();
            File directory = new File(reportDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate report file name with timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String reportName = "AutomationReport_" + timestamp + ".html";
            reportPath = reportDir + reportName;

            // Initialize ExtentSparkReporter
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            // Configure reporter
            sparkReporter.config().setDocumentTitle("Automation Test Report");
            sparkReporter.config().setReportName("Test Execution Report");
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setEncoding("utf-8");

            // Initialize ExtentReports
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // Set system information
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Browser", ConfigReader.getBrowser());
            extent.setSystemInfo("Base URL", ConfigReader.getBaseUrl());
            extent.setSystemInfo("Environment", "Test");

            logger.info("ExtentReports initialized. Report will be generated at: " + reportPath);
        }
    }

    /**
     * Create a new test in ExtentReports
     * @param testName Test name
     * @param description Test description
     * @return ExtentTest instance
     */
    public static ExtentTest createTest(String testName, String description) {
        ExtentTest test = extent.createTest(testName, description);
        extentTest.set(test);
        return test;
    }

    /**
     * Create a new test with category
     * @param testName Test name
     * @param description Test description
     * @param category Test category
     * @return ExtentTest instance
     */
    public static ExtentTest createTest(String testName, String description, String category) {
        ExtentTest test = extent.createTest(testName, description);
        test.assignCategory(category);
        extentTest.set(test);
        return test;
    }

    /**
     * Get current ExtentTest instance
     * @return ExtentTest instance
     */
    public static ExtentTest getTest() {
        return extentTest.get();
    }

    /**
     * Remove current test from ThreadLocal
     */
    public static void removeTest() {
        extentTest.remove();
    }

    /**
     * Flush the ExtentReports
     */
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
            logger.info("ExtentReports flushed successfully");
        }
    }

    /**
     * Get report file path
     * @return Report file path
     */
    public static String getReportPath() {
        return reportPath;
    }

    /**
     * Get ExtentReports instance
     * @return ExtentReports instance
     */
    public static ExtentReports getExtentReports() {
        return extent;
    }
}