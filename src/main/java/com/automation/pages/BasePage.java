package com.automation.pages;

import com.automation.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Base Page class containing common page operations
 */
public abstract class BasePage {
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    protected WebDriver driver;
    protected Actions actions;
    protected JavascriptExecutor jsExecutor;

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        this.jsExecutor = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Navigate to URL
     * @param url URL to navigate to
     */
    protected void navigateTo(String url) {
        logger.info("Navigating to: " + url);
        driver.get(url);
    }

    /**
     * Get current page title
     * @return Page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Get current page URL
     * @return Current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Check if element is displayed
     * @param locator Element locator
     * @return true if element is displayed
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if element is displayed
     * @param element WebElement
     * @return true if element is displayed
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if element is enabled
     * @param locator Element locator
     * @return true if element is enabled
     */
    protected boolean isElementEnabled(By locator) {
        try {
            return driver.findElement(locator).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if element is selected
     * @param locator Element locator
     * @return true if element is selected
     */
    protected boolean isElementSelected(By locator) {
        try {
            return driver.findElement(locator).isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click element with wait
     * @param locator Element locator
     */
    protected void clickElement(By locator) {
        try {
            WebElement element = WaitUtils.waitForElementToBeClickable(driver, locator);
            element.click();
            logger.info("Clicked element: " + locator.toString());
        } catch (Exception e) {
            logger.error("Failed to click element: " + locator.toString());
            throw e;
        }
    }

    /**
     * Click element using WebElement
     * @param element WebElement
     */
    protected void clickElement(WebElement element) {
        try {
            WaitUtils.waitForElementToBeClickable(driver, element);
            element.click();
            logger.info("Clicked element: " + element.toString());
        } catch (Exception e) {
            logger.error("Failed to click element: " + element.toString());
            throw e;
        }
    }

    /**
     * Click element using JavaScript
     * @param locator Element locator
     */
    protected void clickElementWithJS(By locator) {
        try {
            WebElement element = WaitUtils.waitForElementToBeVisible(driver, locator);
            jsExecutor.executeScript("arguments[0].click();", element);
            logger.info("Clicked element using JS: " + locator.toString());
        } catch (Exception e) {
            logger.error("Failed to click element using JS: " + locator.toString());
            throw e;
        }
    }

    /**
     * Type text in element
     * @param locator Element locator
     * @param text Text to type
     */
    protected void typeText(By locator, String text) {
        try {
            WebElement element = WaitUtils.waitForElementToBeVisible(driver, locator);
            element.clear();
            element.sendKeys(text);
            logger.info("Typed text '" + text + "' in element: " + locator.toString());
        } catch (Exception e) {
            logger.error("Failed to type text in element: " + locator.toString());
            throw e;
        }
    }

    /**
     * Type text in WebElement
     * @param element WebElement
     * @param text Text to type
     */
    protected void typeText(WebElement element, String text) {
        try {
            WaitUtils.waitForElementToBeVisible(driver, (By) element);
            element.clear();
            element.sendKeys(text);
            logger.info("Typed text '" + text + "' in element: " + element.toString());
        } catch (Exception e) {
            logger.error("Failed to type text in element: " + element.toString());
            throw e;
        }
    }

    /**
     * Get text from element
     * @param locator Element locator
     * @return Element text
     */
    protected String getElementText(By locator) {
        try {
            WebElement element = WaitUtils.waitForElementToBeVisible(driver, locator);
            String text = element.getText();
            logger.info("Got text '" + text + "' from element: " + locator.toString());
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: " + locator.toString());
            throw e;
        }
    }

    /**
     * Get text from WebElement
     * @param element WebElement
     * @return Element text
     */
    protected String getElementText(WebElement element) {
        try {
            WaitUtils.waitForElementToBeVisible(driver, (By) element);
            String text = element.getText();
            logger.info("Got text '" + text + "' from element: " + element.toString());
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: " + element.toString());
            throw e;
        }
    }

    /**
     * Get attribute value from element
     * @param locator Element locator
     * @param attributeName Attribute name
     * @return Attribute value
     */
    protected String getElementAttribute(By locator, String attributeName) {
        try {
            WebElement element = WaitUtils.waitForElementToBeVisible(driver, locator);
            return element.getAttribute(attributeName);
        } catch (Exception e) {
            logger.error("Failed to get attribute '" + attributeName + "' from element: " + locator.toString());
            throw e;
        }
    }

    /**
     * Select dropdown option by visible text
     * @param locator Dropdown locator
     * @param optionText Option text to select
     */
    protected void selectDropdownByText(By locator, String optionText) {
        try {
            WebElement element = WaitUtils.waitForElementToBeVisible(driver, locator);
            Select select = new Select(element);
            select.selectByVisibleText(optionText);
            logger.info("Selected dropdown option '" + optionText + "' from: " + locator.toString());
        } catch (Exception e) {
            logger.error("Failed to select dropdown option: " + locator.toString());
            throw e;
        }
    }

    /**
     * Select dropdown option by value
     * @param locator Dropdown locator
     * @param value Option value to select
     */
    protected void selectDropdownByValue(By locator, String value) {
        try {
            WebElement element = WaitUtils.waitForElementToBeVisible(driver, locator);
            Select select = new Select(element);
            select.selectByValue(value);
            logger.info("Selected dropdown option by value '" + value + "' from: " + locator.toString());
        } catch (Exception e) {
            logger.error("Failed to select dropdown option by value: " + locator.toString());
            throw e;
        }
    }

    /**
     * Hover over element
     * @param locator Element locator
     */
    protected void hoverOverElement(By locator) {
        try {
            WebElement element = WaitUtils.waitForElementToBeVisible(driver, locator);
            actions.moveToElement(element).perform();
            logger.info("Hovered over element: " + locator.toString());
        } catch (Exception e) {
            logger.error("Failed to hover over element: " + locator.toString());
            throw e;
        }
    }

    /**
     * Double click element
     * @param locator Element locator
     */
    protected void doubleClickElement(By locator) {
        try {
            WebElement element = WaitUtils.waitForElementToBeClickable(driver, locator);
            actions.doubleClick(element).perform();
            logger.info("Double clicked element: " + locator.toString());
        } catch (Exception e) {
            logger.error("Failed to double click element: " + locator.toString());
            throw e;
        }
    }

    /**
     * Right click element
     * @param locator Element locator
     */
    protected void rightClickElement(By locator) {
        try {
            WebElement element = WaitUtils.waitForElementToBeClickable(driver, locator);
            actions.contextClick(element).perform();
            logger.info("Right clicked element: " + locator.toString());
        } catch (Exception e) {
            logger.error("Failed to right click element: " + locator.toString());
            throw e;
        }
    }

    /**
     * Scroll to element
     * @param locator Element locator
     */
    protected void scrollToElement(By locator) {
        try {
            WebElement element = WaitUtils.waitForElementToBeVisible(driver, locator);
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
            logger.info("Scrolled to element: " + locator.toString());
        } catch (Exception e) {
            logger.error("Failed to scroll to element: " + locator.toString());
            throw e;
        }
    }

    /**
     * Scroll page by pixels
     * @param x Horizontal pixels
     * @param y Vertical pixels
     */
    protected void scrollBy(int x, int y) {
        jsExecutor.executeScript("window.scrollBy(" + x + "," + y + ")");
        logger.info("Scrolled page by (" + x + ", " + y + ") pixels");
    }

    /**
     * Get all elements matching locator
     * @param locator Element locator
     * @return List of WebElements
     */
    protected List<WebElement> getElements(By locator) {
        return WaitUtils.waitForAllElementsToBeVisible(driver, locator);
    }

    /**
     * Wait for page to load completely
     */
    protected void waitForPageToLoad() {
        WaitUtils.getWait(driver).until(
                webDriver -> jsExecutor.executeScript("return document.readyState").equals("complete")
        );
        logger.info("Page loaded completely");
    }

    /**
     * Refresh current page
     */
    protected void refreshPage() {
        driver.navigate().refresh();
        logger.info("Page refreshed");
    }

    /**
     * Navigate back
     */
    protected void navigateBack() {
        driver.navigate().back();
        logger.info("Navigated back");
    }

    /**
     * Navigate forward
     */
    protected void navigateForward() {
        driver.navigate().forward();
        logger.info("Navigated forward");
    }
}