package com.automation.tests;

import com.automation.config.ConfigReader;
import com.automation.utils.DriverManager;
import com.automation.utils.ExtentManager;
import com.automation.utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

/**
 * Base Test class containing common test setup and teardown methods
 */
public abstract class BaseTest {
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected WebDriver driver;
    protected ExtentTest extentTest;

    /**
     * Suite level setup - Initialize ExtentReports
     */
    @BeforeSuite
    public void suiteSetup() {
        logger.info("==== Test Suite Started ====");
        ExtentManager.initializeExtentReports();
    }

    /**
     * Test level setup - Initialize WebDriver and create ExtentTest
     * @param method Test method
     */
    @BeforeMethod
    public void setUp(Method method) {
        logger.info("Setting up test: " + method.getName());

        // Initialize WebDriver
        String browser = ConfigReader.getBrowser();
        driver = DriverManager.initializeDriver(browser);

        // Navigate to base URL
        String baseUrl = ConfigReader.getBaseUrl();
        if (baseUrl != null && !baseUrl.isEmpty()) {
            driver.get(baseUrl);
            logger.info("Navigated to base URL: " + baseUrl);
        }

        // Create ExtentTest
        String testName = method.getName();
        String testDescription = getTestDescription(method);
        String testCategory = getTestCategory(method);

        if (testCategory != null && !testCategory.isEmpty()) {
            extentTest = ExtentManager.createTest(testName, testDescription, testCategory);
        } else {
            extentTest = ExtentManager.createTest(testName, testDescription);
        }

        extentTest.info("Test started with browser: " + browser);
        logger.info("Test setup completed for: " + testName);
    }

    /**
     * Test level teardown - Handle test results and cleanup
     * @param result Test result
     */
    @AfterMethod
    public void tearDown(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        try {
            // Handle test result
            if (result.getStatus() == ITestResult.SUCCESS) {
                extentTest.log(Status.PASS, "Test passed successfully");
                logger.info("Test PASSED: " + testName);

            } else if (result.getStatus() == ITestResult.FAILURE) {
                extentTest.log(Status.FAIL, "Test failed: " + result.getThrowable().getMessage());
                logger.error("Test FAILED: " + testName + " - " + result.getThrowable().getMessage());

                // Take screenshot on failure
                if (driver != null) {
                    String screenshotPath = ScreenshotUtils.captureFailureScreenshot(driver, testName);
                    if (screenshotPath != null) {
                        extentTest.addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
                    }
                }

            } else if (result.getStatus() == ITestResult.SKIP) {
                extentTest.log(Status.SKIP, "Test skipped: " + result.getThrowable().getMessage());
                logger.warn("Test SKIPPED: " + testName + " - " + result.getThrowable().getMessage());
            }

        } catch (Exception e) {
            logger.error("Error in tearDown method: " + e.getMessage());
        } finally {
            // Clean up WebDriver
            if (driver != null) {
                DriverManager.quitDriver();
                logger.info("WebDriver quit successfully");
            }

            // Remove ExtentTest from ThreadLocal
            ExtentManager.removeTest();
        }

        logger.info("Test teardown completed for: " + testName);
    }

    /**
     * Suite level teardown - Flush ExtentReports
     */
    @AfterSuite
    public void suiteTearDown() {
        ExtentManager.flushReports();
        logger.info("==== Test Suite Completed ====");
        logger.info("Test report generated at: " + ExtentManager.getReportPath());
    }

    /**
     * Get test description from method annotation or return default
     * @param method Test method
     * @return Test description
     */
    private String getTestDescription(Method method) {
        Test testAnnotation = method.getAnnotation(Test.class);
        if (testAnnotation != null && !testAnnotation.description().isEmpty()) {
            return testAnnotation.description();
        }
        return "Test case: " + method.getName();
    }

    /**
     * Get test category from custom annotation or return null
     * @param method Test method
     * @return Test category
     */
    private String getTestCategory(Method method) {
        // You can implement custom annotation for test categories
        // For now, returning null
        return null;
    }

    /**
     * Log info message to both logger and ExtentTest
     * @param message Message to log
     */
    protected void logInfo(String message) {
        logger.info(message);
        if (extentTest != null) {
            extentTest.info(message);
        }
    }

    /**
     * Log pass message to both logger and ExtentTest
     * @param message Message to log
     */
    protected void logPass(String message) {
        logger.info("PASS: " + message);
        if (extentTest != null) {
            extentTest.pass(message);
        }
    }

    /**
     * Log fail message to both logger and ExtentTest
     * @param message Message to log
     */
    protected void logFail(String message) {
        logger.error("FAIL: " + message);
        if (extentTest != null) {
            extentTest.fail(message);
        }
    }

    /**
     * Log warning message to both logger and ExtentTest
     * @param message Message to log
     */
    protected void logWarning(String message) {
        logger.warn("WARNING: " + message);
        if (extentTest != null) {
            extentTest.warning(message);
        }
    }

    /**
     * Take screenshot and attach to ExtentTest
     * @param stepName Step name for screenshot
     */
    protected void takeScreenshot(String stepName) {
        if (driver != null) {
            String screenshotPath = ScreenshotUtils.takeScreenshot(driver, stepName);
            if (screenshotPath != null && extentTest != null) {
                extentTest.addScreenCaptureFromPath(screenshotPath, stepName);
                logInfo("Screenshot captured: " + stepName);
            }
        }
    }

    /**
     * Get current WebDriver instance
     * @return WebDriver instance
     */
    protected WebDriver getDriver() {
        return driver;
    }

    /**
     * Get current ExtentTest instance
     * @return ExtentTest instance
     */
    protected ExtentTest getExtentTest() {
        return extentTest;
    }
}