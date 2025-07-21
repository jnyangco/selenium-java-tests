package com.automation.base;

import com.automation.utils.ExtentManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    private static final int DEFAULT_TIMEOUT = 10;
    
    public BasePage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        logger.info("Initialized page object: {} on thread: {}", this.getClass().getSimpleName(), Thread.currentThread().getName());
        logInfo("Initialized page object: " + this.getClass().getSimpleName());
    }
    
    // =======================================================
    // SOLUTION 2: Add protected logging methods to BasePage
    // These can be used directly in all page classes
    
    protected void logPass(String message) {
        ExtentManager.logPass(message);
    }
    
    protected void logFail(String message) {
        ExtentManager.logFail(message);
    }
    
    protected void logInfo(String message) {
        ExtentManager.logInfo(message);
    }
    
    protected void logWarning(String message) {
        ExtentManager.logWarning(message);
    }
    
    protected void logSkip(String message) {
        ExtentManager.logSkip(message);
    }
    
    // =======================================================
    // Helper methods to find elements
    protected WebElement findElement(By locator) {
        try {
            return driver.findElement(locator);
        } catch (Exception e) {
            logger.error("Failed to find element {}: {}", locator, e.getMessage());
            logFail("Failed to find element " + locator + ": " + e.getMessage());
            throw e;
        }
    }
    
    protected List<WebElement> findElements(By locator) {
        try {
            return driver.findElements(locator);
        } catch (Exception e) {
            logger.error("Failed to find elements {}: {}", locator, e.getMessage());
            logFail("Failed to find elements " + locator + ": " + e.getMessage());
            throw e;
        }
    }
    
    protected void click(By locator) {
        try {
            WebElement element = waitForElementToBeClickable(locator);
            element.click();
            logger.info("Successfully clicked element: {}", locator);
            logInfo("Successfully clicked element: " + locator);
        } catch (Exception e) {
            logger.error("Failed to click element {}: {}", locator, e.getMessage());
            logFail("Failed to click element " + locator + ": " + e.getMessage());
            throw new RuntimeException("Failed to click element", e);
        }
    }
    
    protected void sendKeys(By locator, String text) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            element.clear();
            element.sendKeys(text);
            logger.info("Successfully entered text '{}' in element: {}", text, locator);
            logInfo("Successfully entered text '" + text + "' in element: " + locator);
        } catch (Exception e) {
            logger.error("Failed to enter text '{}' in element {}: {}", text, locator, e.getMessage());
            logFail("Failed to enter text '" + text + "' in element " + locator + ": " + e.getMessage());
            throw new RuntimeException("Failed to enter text", e);
        }
    }
    
    protected String getText(By locator) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            String text = element.getText();
            logger.info("Retrieved text '{}' from element: {}", text, locator);
            logInfo("Retrieved text '" + text + "' from element: " + locator);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element {}: {}", locator, e.getMessage());
            logFail("Failed to get text from element " + locator + ": " + e.getMessage());
            throw new RuntimeException("Failed to get text", e);
        }
    }
    
    protected String getAttribute(By locator, String attributeName) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            String attributeValue = element.getAttribute(attributeName);
            logger.info("Retrieved attribute '{}' value '{}' from element: {}", attributeName, attributeValue, locator);
            logInfo("Retrieved attribute '" + attributeName + "' value '" + attributeValue + "' from element: " + locator);
            return attributeValue;
        } catch (Exception e) {
            logger.error("Failed to get attribute '{}' from element {}: {}", attributeName, locator, e.getMessage());
            logFail("Failed to get attribute '" + attributeName + "' from element " + locator + ": " + e.getMessage());
            throw new RuntimeException("Failed to get attribute", e);
        }
    }
    
    protected boolean isDisplayed(By locator) {
        try {
            boolean displayed = findElement(locator).isDisplayed();
            logInfo("Element " + locator + " displayed: " + displayed);
            return displayed;
        } catch (Exception e) {
            logger.warn("Element {} is not displayed: {}", locator, e.getMessage());
            logWarning("Element " + locator + " is not displayed: " + e.getMessage());
            return false;
        }
    }
    
    protected boolean isEnabled(By locator) {
        try {
            boolean enabled = findElement(locator).isEnabled();
            logInfo("Element " + locator + " enabled: " + enabled);
            return enabled;
        } catch (Exception e) {
            logger.warn("Element {} is not enabled: {}", locator, e.getMessage());
            logWarning("Element " + locator + " is not enabled: " + e.getMessage());
            return false;
        }
    }
    
    protected void selectByVisibleText(By locator, String text) {
        try {
            WebElement dropdown = findElement(locator);
            Select select = new Select(dropdown);
            select.selectByVisibleText(text);
            logger.info("Successfully selected option '{}' from dropdown: {}", text, locator);
            logInfo("Successfully selected option '" + text + "' from dropdown: " + locator);
        } catch (Exception e) {
            logger.error("Failed to select option '{}' from dropdown {}: {}", text, locator, e.getMessage());
            logFail("Failed to select option '" + text + "' from dropdown " + locator + ": " + e.getMessage());
            throw new RuntimeException("Failed to select dropdown option", e);
        }
    }
    
    protected void selectByValue(By locator, String value) {
        try {
            WebElement dropdown = findElement(locator);
            Select select = new Select(dropdown);
            select.selectByValue(value);
            logger.info("Successfully selected value '{}' from dropdown: {}", value, locator);
            logInfo("Successfully selected value '" + value + "' from dropdown: " + locator);
        } catch (Exception e) {
            logger.error("Failed to select value '{}' from dropdown {}: {}", value, locator, e.getMessage());
            logFail("Failed to select value '" + value + "' from dropdown " + locator + ": " + e.getMessage());
            throw new RuntimeException("Failed to select dropdown value", e);
        }
    }
    
    protected WebElement waitForElementToBeVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element {} not visible within timeout", locator);
            logFail("Element " + locator + " not visible within timeout");
            throw new RuntimeException("Element not visible within timeout", e);
        }
    }
    
    protected WebElement waitForElementToBeClickable(By locator) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            logger.error("Element {} not clickable within timeout", locator);
            logFail("Element " + locator + " not clickable within timeout");
            throw new RuntimeException("Element not clickable within timeout", e);
        }
    }
    
    protected List<WebElement> waitForElementsToBeVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        } catch (TimeoutException e) {
            logger.error("Elements {} not visible within timeout", locator);
            logFail("Elements " + locator + " not visible within timeout");
            throw new RuntimeException("Elements not visible within timeout", e);
        }
    }
    
    protected void waitForPageTitle(String title) {
        try {
            wait.until(ExpectedConditions.titleContains(title));
            logger.info("Page title contains: {}", title);
            logInfo("Page title contains: " + title);
        } catch (TimeoutException e) {
            logger.error("Page title does not contain '{}' within timeout", title);
            logFail("Page title does not contain '" + title + "' within timeout");
            throw new RuntimeException("Page title timeout", e);
        }
    }
    
    protected void scrollToElement(By locator) {
        try {
            WebElement element = findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            logger.info("Scrolled to element: {}", locator);
            logInfo("Scrolled to element: " + locator);
        } catch (Exception e) {
            logger.error("Failed to scroll to element {}: {}", locator, e.getMessage());
            logFail("Failed to scroll to element " + locator + ": " + e.getMessage());
            throw new RuntimeException("Failed to scroll to element", e);
        }
    }
    
    protected Object executeJavaScript(String script, Object... args) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Object result = js.executeScript(script, args);
            logger.info("Executed JavaScript successfully");
            logInfo("Executed JavaScript successfully");
            return result;
        } catch (Exception e) {
            logger.error("Failed to execute JavaScript: {}", e.getMessage());
            logFail("Failed to execute JavaScript: " + e.getMessage());
            throw new RuntimeException("Failed to execute JavaScript", e);
        }
    }
    
    protected void navigateTo(String url) {
        logger.info("Navigating to URL: {}", url);
        logInfo("Navigating to URL: " + url);
        try {
            driver.get(url);
            logger.info("Successfully navigated to: {}", url);
            logPass("Successfully navigated to: " + url);
        } catch (Exception e) {
            logger.error("Failed to navigate to URL: {}", url, e);
            logFail("Failed to navigate to URL: " + url + " - " + e.getMessage());
            throw new RuntimeException("Failed to navigate to URL", e);
        }
    }
    
    // Make these methods public so they can be accessed from test classes
    public String getCurrentUrl() {
        try {
            return driver.getCurrentUrl();
        } catch (Exception e) {
            logger.error("Failed to get current URL: {}", e.getMessage());
            logFail("Failed to get current URL: " + e.getMessage());
            return "";
        }
    }
    
    public String getPageTitle() {
        try {
            return driver.getTitle();
        } catch (Exception e) {
            logger.error("Failed to get page title: {}", e.getMessage());
            logFail("Failed to get page title: " + e.getMessage());
            return "";
        }
    }
    
    // Additional utility methods for better error handling
    protected boolean isElementPresent(By locator) {
        try {
            findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    protected void waitForPageToLoad() {
        try {
            wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
            logInfo("Page loaded completely");
        } catch (Exception e) {
            logger.warn("Page load wait timeout: {}", e.getMessage());
            logWarning("Page load wait timeout: " + e.getMessage());
        }
    }
    
    // Additional wait methods for better stability
    protected void waitForElementToDisappear(By locator) {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            logInfo("Element disappeared: " + locator);
        } catch (TimeoutException e) {
            logger.warn("Element did not disappear within timeout: {}", locator);
            logWarning("Element did not disappear within timeout: " + locator);
        }
    }
    
    protected void waitForTextToBePresentInElement(By locator, String text) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
            logInfo("Text '" + text + "' found in element: " + locator);
        } catch (TimeoutException e) {
            logger.error("Text '{}' not found in element {} within timeout", text, locator);
            logFail("Text '" + text + "' not found in element " + locator + " within timeout");
            throw new RuntimeException("Text not found within timeout", e);
        }
    }
    
    // Refresh and navigation utilities
    protected void refreshPage() {
        try {
            driver.navigate().refresh();
            logInfo("Page refreshed successfully");
        } catch (Exception e) {
            logger.error("Failed to refresh page: {}", e.getMessage());
            logFail("Failed to refresh page: " + e.getMessage());
            throw new RuntimeException("Failed to refresh page", e);
        }
    }
    
    protected void navigateBack() {
        try {
            driver.navigate().back();
            logInfo("Navigated back successfully");
        } catch (Exception e) {
            logger.error("Failed to navigate back: {}", e.getMessage());
            logFail("Failed to navigate back: " + e.getMessage());
            throw new RuntimeException("Failed to navigate back", e);
        }
    }
    
    protected void navigateForward() {
        try {
            driver.navigate().forward();
            logInfo("Navigated forward successfully");
        } catch (Exception e) {
            logger.error("Failed to navigate forward: {}", e.getMessage());
            logFail("Failed to navigate forward: " + e.getMessage());
            throw new RuntimeException("Failed to navigate forward", e);
        }
    }

    public void hardWait(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}