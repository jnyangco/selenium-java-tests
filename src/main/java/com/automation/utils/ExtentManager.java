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
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final String REPORT_PATH = "reports/extent-reports/";
    
    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String reportFileName = "Test-Report-" + timestamp + ".html";
            String reportPath = REPORT_PATH + reportFileName;
            
            // Create reports directory if it doesn't exist
            File reportDir = new File(REPORT_PATH);
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }
            
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            configureSparkReporter(sparkReporter);
            
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Selenium Version", "4.26.0");
            extent.setSystemInfo("TestNG Version", "7.10.2");
            
            logger.info("ExtentReports initialized. Report will be saved at: {}", reportPath);
        }
        return extent;
    }
    
    private static void configureSparkReporter(ExtentSparkReporter sparkReporter) {
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("Selenium Test Automation Report");
        sparkReporter.config().setReportName("Test Execution Report");
        sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        sparkReporter.config().setEncoding("utf-8");
    }
    
    public static synchronized ExtentTest createTest(String testName, String description) {
        ExtentTest extentTest = getInstance().createTest(testName, description);
        test.set(extentTest);
        logger.info("Created test: {} - {}", testName, description);
        return extentTest;
    }
    
    public static synchronized ExtentTest createTest(String testName) {
        return createTest(testName, "");
    }
    
    public static synchronized ExtentTest getTest() {
        return test.get();
    }
    
    public static synchronized void removeTest() {
        test.remove();
    }
    
    public static synchronized void flush() {
        if (extent != null) {
            extent.flush();
            logger.info("ExtentReports flushed successfully");
        }
    }
    
    
    
    // ===========================================================
    // Utility methods for logging
    public static void logPass(String message) {
        if (getTest() != null) {
            getTest().log(Status.PASS, message);
            logger.info("✅ PASS: {}", message);
        }
    }
    
    public static void logFail(String message) {
        if (getTest() != null) {
            getTest().log(Status.FAIL, message);
            logger.error("❌ FAIL: {}", message);
        }
    }
    
    public static void logInfo(String message) {
        if (getTest() != null) {
            getTest().log(Status.INFO, message);
            logger.info("ℹ️ INFO: {}", message);
        }
    }
    
    public static void logWarning(String message) {
        if (getTest() != null) {
            getTest().log(Status.WARNING, message);
            logger.warn("⚠️ WARNING: {}", message);
        }
    }
    
    public static void logSkip(String message) {
        if (getTest() != null) {
            getTest().log(Status.SKIP, message);
            logger.warn("⏭️ SKIP: {}", message);
            
        }
    }
    // ===========================================================
    
    
    public static void addScreenshot(String base64Screenshot, String description) {
        if (getTest() != null) {
            getTest().addScreenCaptureFromBase64String(base64Screenshot, description);
        }
    }
}