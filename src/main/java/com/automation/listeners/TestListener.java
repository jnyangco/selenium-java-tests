package com.automation.listeners;

import com.automation.base.BaseTest;
import io.qameta.allure.Attachment;
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
        logger.info("Starting test: {}.{}", 
                result.getTestClass().getName(), 
                result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test passed: {}.{}", 
                result.getTestClass().getName(), 
                result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed: {}.{}", 
                result.getTestClass().getName(), 
                result.getMethod().getMethodName());
        
        // Capture screenshot on failure
        WebDriver driver = getDriverFromTest(result);
        if (driver != null) {
            saveScreenshot(driver, result.getMethod().getMethodName());
        }
        
        // Log the exception
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            logger.error("Test failure reason: {}", throwable.getMessage(), throwable);
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test skipped: {}.{}", 
                result.getTestClass().getName(), 
                result.getMethod().getMethodName());
        
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            logger.warn("Test skip reason: {}", throwable.getMessage());
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
        }
        return null;
    }
    
    @Attachment(value = "Screenshot on Failure", type = "image/png")
    private byte[] saveScreenshot(WebDriver driver, String testName) {
        try {
            logger.info("Taking screenshot for failed test: {}", testName);
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            logger.error("Failed to take screenshot: {}", e.getMessage());
            return new byte[0];
        }
    }
}