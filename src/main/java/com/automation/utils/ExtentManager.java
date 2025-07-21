package com.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentManager {
    
    private static final Logger logger = LoggerFactory.getLogger(ExtentManager.class);
    private static volatile ExtentReports extent; // FIXED: Added volatile for thread safety
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final String REPORT_PATH = "reports/extent-reports/";
    private static final Object lock = new Object(); // FIXED: Added synchronization lock
    
    public static ExtentReports getInstance() {
        if (extent == null) {
            synchronized (lock) { // FIXED: Double-checked locking pattern
                if (extent == null) {
                    initializeExtentReports();
                }
            }
        }
        return extent;
    }
    
    private static void initializeExtentReports() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String reportFileName = "Test-Report-" + timestamp + ".html";
        String reportPath = REPORT_PATH + reportFileName;
        
        // Create reports directory if it doesn't exist
        File reportDir = new File(REPORT_PATH);
        if (!reportDir.exists()) {
            boolean created = reportDir.mkdirs();
            if (!created) {
                logger.warn("Failed to create report directory: {}", REPORT_PATH);
            }
        }
        
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        configureSparkReporter(sparkReporter);
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Selenium Version", "4.26.0");
        extent.setSystemInfo("TestNG Version", "7.10.2");
        extent.setSystemInfo("Browser", System.getProperty("browser", "chrome"));
        extent.setSystemInfo("Execution Mode", "Parallel");
        
        logger.info("ExtentReports initialized. Report will be saved at: {}", reportPath);
    }
    
    private static void configureSparkReporter(ExtentSparkReporter sparkReporter) {
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("Selenium Test Automation Report");
        sparkReporter.config().setReportName("Test Execution Report - Parallel Execution");
        sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        sparkReporter.config().setEncoding("utf-8");
    }
    
    public static synchronized ExtentTest createTest(String testName, String description) {
        ExtentTest extentTest = getInstance().createTest(testName, description);
        test.set(extentTest);
        logger.info("Created test: {} - {} on thread: {}", testName, description, Thread.currentThread().getName());
        return extentTest;
    }
    
    public static synchronized ExtentTest createTest(String testName) {
        return createTest(testName, "");
    }
    
    public static ExtentTest getTest() {
        ExtentTest currentTest = test.get();
        if (currentTest == null) {
            logger.warn("No ExtentTest found for current thread: {}", Thread.currentThread().getName());
            // Create a default test if none exists
            return createTest("Default Test - " + Thread.currentThread().getName());
        }
        return currentTest;
    }
    
    public static void removeTest() {
        test.remove();
        logger.debug("Removed test from thread: {}", Thread.currentThread().getName());
    }
    
    public static synchronized void flush() {
        if (extent != null) {
            extent.flush();
            logger.info("ExtentReports flushed successfully");
        }
    }
    
    // ===========================================================
    // Utility methods for logging - FIXED thread safety
    public static void logPass(String message) {
        ExtentTest currentTest = getTest();
        if (currentTest != null) {
            synchronized (currentTest) {
                currentTest.log(Status.PASS, message);
            }
            logger.info("✅ PASS: {}", message);
        } else {
            logger.warn("Cannot log PASS - no test context available: {}", message);
        }
    }
    
    public static void logFail(String message) {
        ExtentTest currentTest = getTest();
        if (currentTest != null) {
            synchronized (currentTest) {
                currentTest.log(Status.FAIL, message);
            }
            logger.error("❌ FAIL: {}", message);
        } else {
            logger.warn("Cannot log FAIL - no test context available: {}", message);
        }
    }
    
    public static void logInfo(String message) {
        ExtentTest currentTest = getTest();
        if (currentTest != null) {
            synchronized (currentTest) {
                currentTest.log(Status.INFO, message);
            }
            logger.info("ℹ️ INFO: {}", message);
        } else {
            logger.warn("Cannot log INFO - no test context available: {}", message);
        }
    }
    
    public static void logWarning(String message) {
        ExtentTest currentTest = getTest();
        if (currentTest != null) {
            synchronized (currentTest) {
                currentTest.log(Status.WARNING, message);
            }
            logger.warn("⚠️ WARNING: {}", message);
        } else {
            logger.warn("Cannot log WARNING - no test context available: {}", message);
        }
    }
    
    public static void logSkip(String message) {
        ExtentTest currentTest = getTest();
        if (currentTest != null) {
            synchronized (currentTest) {
                currentTest.log(Status.SKIP, message);
            }
            logger.warn("⏭️ SKIP: {}", message);
        } else {
            logger.warn("Cannot log SKIP - no test context available: {}", message);
        }
    }
    // ===========================================================
    
    public static void addScreenshot(String base64Screenshot, String description) {
        ExtentTest currentTest = getTest();
        if (currentTest != null) {
            synchronized (currentTest) {
                currentTest.addScreenCaptureFromBase64String(base64Screenshot, description);
            }
        } else {
            logger.warn("Cannot add screenshot - no test context available");
        }
    }
}