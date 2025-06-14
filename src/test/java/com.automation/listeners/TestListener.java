package com.automation.listeners;

import com.automation.utils.ExtentManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG Listener for handling test events
 */
public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test Started: " + result.getMethod().getMethodName());

        ExtentTest test = ExtentManager.getTest();
        if (test != null) {
            test.info("Test execution started");
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test Passed: " + result.getMethod().getMethodName());

        ExtentTest test = ExtentManager.getTest();
        if (test != null) {
            test.log(Status.PASS, "Test passed successfully");
            test.info("Execution time: " + (result.getEndMillis() - result.getStartMillis()) + " ms");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String errorMessage = result.getThrowable().getMessage();

        logger.error("Test Failed: " + testName + " - " + errorMessage);

        ExtentTest test = ExtentManager.getTest();
        if (test != null) {
            test.log(Status.FAIL, "Test failed: " + errorMessage);
            test.info("Execution time: " + (result.getEndMillis() - result.getStartMillis()) + " ms");

            // Add stack trace to report
            test.fail(result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String skipReason = result.getThrowable() != null ? result.getThrowable().getMessage() : "Test skipped";

        logger.warn("Test Skipped: " + testName + " - " + skipReason);

        ExtentTest test = ExtentManager.getTest();
        if (test != null) {
            test.log(Status.SKIP, "Test skipped: " + skipReason);
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.info("Test failed but within success percentage: " + result.getMethod().getMethodName());
    }
}