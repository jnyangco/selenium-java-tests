package com.automation.utils;

import com.automation.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * WebDriver Wait utility class
 */
public class WaitUtils {
    private static final Logger logger = LogManager.getLogger(WaitUtils.class);

    /**
     * Get WebDriverWait instance with default timeout
     * @param driver WebDriver instance
     * @return WebDriverWait instance
     */
    public static WebDriverWait getWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
    }

    /**
     * Get WebDriverWait instance with custom timeout
     * @param driver WebDriver instance
     * @param timeoutInSeconds Timeout in seconds
     * @return WebDriverWait instance
     */
    public static WebDriverWait getWait(WebDriver driver, int timeoutInSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    /**
     * Wait for element to be visible
     * @param driver WebDriver instance
     * @param locator Element locator
     * @return WebElement
     */
    public static WebElement waitForElementToBeVisible(WebDriver driver, By locator) {
        try {
            WebDriverWait wait = getWait(driver);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element not visible: " + locator.toString());
            throw e;
        }
    }

    /**
     * Wait for element to be visible with custom timeout
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param timeoutInSeconds Timeout in seconds
     * @return WebElement
     */
    public static WebElement waitForElementToBeVisible(WebDriver driver, By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = getWait(driver, timeoutInSeconds);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element not visible within " + timeoutInSeconds + " seconds: " + locator.toString());
            throw e;
        }
    }

    /**
     * Wait for element to be clickable
     * @param driver WebDriver instance
     * @param locator Element locator
     * @return WebElement
     */
    public static WebElement waitForElementToBeClickable(WebDriver driver, By locator) {
        try {
            WebDriverWait wait = getWait(driver);
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            logger.error("Element not clickable: " + locator.toString());
            throw e;
        }
    }

    /**
     * Wait for element to be clickable
     * @param driver WebDriver instance
     * @param element WebElement
     * @return WebElement
     */
    public static WebElement waitForElementToBeClickable(WebDriver driver, WebElement element) {
        try {
            WebDriverWait wait = getWait(driver);
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            logger.error("Element not clickable: " + element.toString());
            throw e;
        }
    }

    /**
     * Wait for element to be present
     * @param driver WebDriver instance
     * @param locator Element locator
     * @return WebElement
     */
    public static WebElement waitForElementToBePresent(WebDriver driver, By locator) {
        try {
            WebDriverWait wait = getWait(driver);
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element not present: " + locator.toString());
            throw e;
        }
    }

    /**
     * Wait for all elements to be visible
     * @param driver WebDriver instance
     * @param locator Element locator
     * @return List of WebElements
     */
    public static List<WebElement> waitForAllElementsToBeVisible(WebDriver driver, By locator) {
        try {
            WebDriverWait wait = getWait(driver);
            return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        } catch (Exception e) {
            logger.error("Elements not visible: " + locator.toString());
            throw e;
        }
    }

    /**
     * Wait for element to disappear
     * @param driver WebDriver instance
     * @param locator Element locator
     * @return boolean
     */
    public static boolean waitForElementToDisappear(WebDriver driver, By locator) {
        try {
            WebDriverWait wait = getWait(driver);
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element did not disappear: " + locator.toString());
            return false;
        }
    }

    /**
     * Wait for text to be present in element
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param text Expected text
     * @return boolean
     */
    public static boolean waitForTextToBePresentInElement(WebDriver driver, By locator, String text) {
        try {
            WebDriverWait wait = getWait(driver);
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
        } catch (Exception e) {
            logger.error("Text '" + text + "' not present in element: " + locator.toString());
            return false;
        }
    }

    /**
     * Wait for page title to contain text
     * @param driver WebDriver instance
     * @param title Expected title text
     * @return boolean
     */
    public static boolean waitForTitleToContain(WebDriver driver, String title) {
        try {
            WebDriverWait wait = getWait(driver);
            return wait.until(ExpectedConditions.titleContains(title));
        } catch (Exception e) {
            logger.error("Title does not contain: " + title);
            return false;
        }
    }

    /**
     * Wait for URL to contain text
     * @param driver WebDriver instance
     * @param url Expected URL text
     * @return boolean
     */
    public static boolean waitForUrlToContain(WebDriver driver, String url) {
        try {
            WebDriverWait wait = getWait(driver);
            return wait.until(ExpectedConditions.urlContains(url));
        } catch (Exception e) {
            logger.error("URL does not contain: " + url);
            return false;
        }
    }

    /**
     * Wait for alert to be present
     * @param driver WebDriver instance
     * @return Alert
     */
    public static org.openqa.selenium.Alert waitForAlertToBePresent(WebDriver driver) {
        try {
            WebDriverWait wait = getWait(driver);
            return wait.until(ExpectedConditions.alertIsPresent());
        } catch (Exception e) {
            logger.error("Alert not present");
            throw e;
        }
    }

    /**
     * Wait for frame to be available and switch to it
     * @param driver WebDriver instance
     * @param locator Frame locator
     * @return WebDriver
     */
    public static WebDriver waitForFrameToBeAvailableAndSwitchToIt(WebDriver driver, By locator) {
        try {
            WebDriverWait wait = getWait(driver);
            return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
        } catch (Exception e) {
            logger.error("Frame not available: " + locator.toString());
            throw e;
        }
    }
}