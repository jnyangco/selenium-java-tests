package com.automation.listeners;

import com.automation.base.BaseTest;
import com.automation.utils.ExtentManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Field;

public class TestListener implements ITestListener {
    
    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);
    
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getTestClass().getName() + "." + result.getMethod().getMethodName();
        logger.info("Starting test: {}", testName);
        ExtentManager.logInfo("Starting test: " + testName);
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getTestClass().getName() + "." + result.getMethod().getMethodName();
        logger.info("Test passed: {}", testName);
        ExtentManager.logPass("Test passed: " + testName);
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getTestClass().getName() + "." + result.getMethod().getMethodName();
        logger.error("Test failed: {}", testName);
        ExtentManager.logFail("Test failed: " + testName);
        
        // Capture screenshot on failure
        WebDriver driver = getDriverFromTest(result);
        if (driver != null) {
            String screenshot = saveScreenshotAsBase64(driver, result.getMethod().getMethodName());
            if (screenshot != null) {
                ExtentManager.addScreenshot(screenshot, "Screenshot on test failure");
            }
        }
        
        // Log the exception
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            logger.error("Test failure reason: {}", throwable.getMessage(), throwable);
            ExtentManager.logFail("Test failure reason: " + throwable.getMessage());
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getTestClass().getName() + "." + result.getMethod().getMethodName();
        logger.warn("Test skipped: {}", testName);
        ExtentManager.logSkip("Test skipped: " + testName);
        
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            logger.warn("Test skip reason: {}", throwable.getMessage());
            ExtentManager.logSkip("Test skip reason: " + throwable.getMessage());
        }
    }
    
    private WebDriver getDriverFromTest(ITestResult result) {
        try {
            Object testInstance = result.getInstance();
            if (testInstance instanceof BaseTest) {
                Field driverField = BaseTest.class.getDeclaredField("driver");
                driverField.setAccessible(true);
                return (WebDriver) driverField.get(testInstance);
            }
        } catch (Exception e) {
            logger.error("Failed to get WebDriver from test instance: {}", e.getMessage());
            ExtentManager.logWarning("Failed to get WebDriver from test instance: " + e.getMessage());
        }
        return null;
    }
    
    private String saveScreenshotAsBase64(WebDriver driver, String testName) {
        try {
            logger.info("Taking screenshot for failed test: {}", testName);
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            logger.error("Failed to take screenshot: {}", e.getMessage());
            ExtentManager.logFail("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }
}