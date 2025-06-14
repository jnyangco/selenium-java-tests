package com.automation.utils;

import com.automation.config.ConfigReader;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Screenshot utility class
 */
public class ScreenshotUtils {
    private static final Logger logger = LogManager.getLogger(ScreenshotUtils.class);

    /**
     * Take screenshot and save to file
     * @param driver WebDriver instance
     * @param testName Test name for screenshot filename
     * @return Screenshot file path
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            // Create screenshot directory if it doesn't exist
            String screenshotDir = ConfigReader.getScreenshotPath();
            File directory = new File(screenshotDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate screenshot filename with timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = testName + "_" + timestamp + ".png";
            String filePath = screenshotDir + fileName;

            // Take screenshot
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(filePath);

            // Copy screenshot to destination
            FileUtils.copyFile(sourceFile, destinationFile);

            logger.info("Screenshot saved: " + filePath);
            return filePath;

        } catch (IOException e) {
            logger.error("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }

    /**
     * Take screenshot and return as base64 string
     * @param driver WebDriver instance
     * @return Base64 encoded screenshot string
     */
    public static String takeScreenshotAsBase64(WebDriver driver) {
        try {
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            return takesScreenshot.getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            logger.error("Failed to take screenshot as base64: " + e.getMessage());
            return null;
        }
    }

    /**
     * Take screenshot on test failure
     * @param driver WebDriver instance
     * @param testName Test name
     * @return Screenshot file path
     */
    public static String captureFailureScreenshot(WebDriver driver, String testName) {
        if (ConfigReader.takeScreenshotOnFailure()) {
            return takeScreenshot(driver, testName + "_FAILED");
        }
        return null;
    }
}