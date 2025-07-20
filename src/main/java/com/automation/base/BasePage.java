package com.automation.base;

import io.qameta.allure.Step;
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
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        logger.info("Initialized page object: {}", this.getClass().getSimpleName());
    }
    
    // Helper methods to find elements
    protected WebElement findElement(By locator) {
        return driver.findElement(locator);
    }
    
    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }
    
    
    @Step("Click element using locator")
    protected void click(By locator) {
        try {
            WebElement element = waitForElementToBeClickable(locator);
            element.click();
            logger.info("Successfully clicked element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to click element {}: {}", locator, e.getMessage());
            throw new RuntimeException("Failed to click element", e);
        }
    }
    
    @Step("Enter text using locator: {text}")
    protected void sendKeys(By locator, String text) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            element.clear();
            element.sendKeys(text);
            logger.info("Successfully entered text '{}' in element: {}", text, locator);
        } catch (Exception e) {
            logger.error("Failed to enter text '{}' in element {}: {}", text, locator, e.getMessage());
            throw new RuntimeException("Failed to enter text", e);
        }
    }
    
    @Step("Get text from element using locator")
    protected String getText(By locator) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            String text = element.getText();
            logger.info("Retrieved text '{}' from element: {}", text, locator);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element {}: {}", locator, e.getMessage());
            throw new RuntimeException("Failed to get text", e);
        }
    }
    
    @Step("Get attribute value using locator: {attributeName}")
    protected String getAttribute(By locator, String attributeName) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            String attributeValue = element.getAttribute(attributeName);
            logger.info("Retrieved attribute '{}' value '{}' from element: {}", attributeName, attributeValue, locator);
            return attributeValue;
        } catch (Exception e) {
            logger.error("Failed to get attribute '{}' from element {}: {}", attributeName, locator, e.getMessage());
            throw new RuntimeException("Failed to get attribute", e);
        }
    }
    
    @Step("Check if element is displayed using locator")
    protected boolean isDisplayed(By locator) {
        try {
            return findElement(locator).isDisplayed();
        } catch (Exception e) {
            logger.warn("Element {} is not displayed: {}", locator, e.getMessage());
            return false;
        }
    }
    
    @Step("Check if element is enabled using locator")
    protected boolean isEnabled(By locator) {
        try {
            return findElement(locator).isEnabled();
        } catch (Exception e) {
            logger.warn("Element {} is not enabled: {}", locator, e.getMessage());
            return false;
        }
    }
    
    @Step("Select dropdown option by visible text: {text}")
    protected void selectByVisibleText(By locator, String text) {
        try {
            WebElement dropdown = findElement(locator);
            Select select = new Select(dropdown);
            select.selectByVisibleText(text);
            logger.info("Successfully selected option '{}' from dropdown: {}", text, locator);
        } catch (Exception e) {
            logger.error("Failed to select option '{}' from dropdown {}: {}", text, locator, e.getMessage());
            throw new RuntimeException("Failed to select dropdown option", e);
        }
    }
    
    @Step("Select dropdown option by value: {value}")
    protected void selectByValue(By locator, String value) {
        try {
            WebElement dropdown = findElement(locator);
            Select select = new Select(dropdown);
            select.selectByValue(value);
            logger.info("Successfully selected value '{}' from dropdown: {}", value, locator);
        } catch (Exception e) {
            logger.error("Failed to select value '{}' from dropdown {}: {}", value, locator, e.getMessage());
            throw new RuntimeException("Failed to select dropdown value", e);
        }
    }
    
    @Step("Wait for element to be visible")
    protected WebElement waitForElementToBeVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element {} not visible within timeout", locator);
            throw new RuntimeException("Element not visible within timeout", e);
        }
    }
    
    @Step("Wait for element to be clickable")
    protected WebElement waitForElementToBeClickable(By locator) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            logger.error("Element {} not clickable within timeout", locator);
            throw new RuntimeException("Element not clickable within timeout", e);
        }
    }
    
    @Step("Wait for elements to be visible")
    protected List<WebElement> waitForElementsToBeVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        } catch (TimeoutException e) {
            logger.error("Elements {} not visible within timeout", locator);
            throw new RuntimeException("Elements not visible within timeout", e);
        }
    }
    
    @Step("Wait for page title to contain: {title}")
    protected void waitForPageTitle(String title) {
        try {
            wait.until(ExpectedConditions.titleContains(title));
            logger.info("Page title contains: {}", title);
        } catch (TimeoutException e) {
            logger.error("Page title does not contain '{}' within timeout", title);
            throw new RuntimeException("Page title timeout", e);
        }
    }
    
    @Step("Scroll to element using locator")
    protected void scrollToElement(By locator) {
        try {
            WebElement element = findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            logger.info("Scrolled to element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to scroll to element {}: {}", locator, e.getMessage());
            throw new RuntimeException("Failed to scroll to element", e);
        }
    }
    
    @Step("Execute JavaScript: {script}")
    protected Object executeJavaScript(String script, Object... args) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Object result = js.executeScript(script, args);
            logger.info("Executed JavaScript successfully");
            return result;
        } catch (Exception e) {
            logger.error("Failed to execute JavaScript: {}", e.getMessage());
            throw new RuntimeException("Failed to execute JavaScript", e);
        }
    }
    
    @Step("Navigate to: {url}")
    protected void navigateTo(String url) {
        logger.info("Navigating to URL: {}", url);
        driver.get(url);
        logger.info("Successfully navigated to: {}", url);
    }
    
    @Step("Get current url")
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    @Step("Get page title")
    protected String getPageTitle() {
        return driver.getTitle();
    }
    
}