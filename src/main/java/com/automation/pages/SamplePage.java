package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Sample Page Object class demonstrating page object pattern
 */
public class SamplePage extends BasePage {

    // Page Elements using @FindBy annotation
    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    @FindBy(css = ".error-message")
    private WebElement errorMessage;

    @FindBy(linkText = "Forgot Password?")
    private WebElement forgotPasswordLink;

    // Page Elements using By locators
    private final By headerTitle = By.cssSelector("h1.page-title");
    private final By loadingSpinner = By.className("loading-spinner");
    private final By successMessage = By.xpath("//div[contains(@class, 'success')]");

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public SamplePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enter username
     * @param username Username to enter
     * @return SamplePage instance for method chaining
     */
    public SamplePage enterUsername(String username) {
        typeText(usernameField, username);
        logInfo("Entered username: " + username);
        return this;
    }

    /**
     * Enter password
     * @param password Password to enter
     * @return SamplePage instance for method chaining
     */
    public SamplePage enterPassword(String password) {
        typeText(passwordField, password);
        logInfo("Entered password");
        return this;
    }

    /**
     * Click login button
     * @return SamplePage instance
     */
    public SamplePage clickLoginButton() {
        clickElement(loginButton);
        logInfo("Clicked login button");
        return this;
    }

    /**
     * Perform login with credentials
     * @param username Username
     * @param password Password
     * @return SamplePage instance
     */
    public SamplePage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        logInfo("Performed login with username: " + username);
        return this;
    }

    /**
     * Get error message text
     * @return Error message text
     */
    public String getErrorMessage() {
        String error = getElementText(errorMessage);
        logInfo("Error message: " + error);
        return error;
    }

    /**
     * Check if error message is displayed
     * @return true if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        boolean isDisplayed = isElementDisplayed(errorMessage);
        logInfo("Error message displayed: " + isDisplayed);
        return isDisplayed;
    }

    /**
     * Click forgot password link
     * @return SamplePage instance
     */
    public SamplePage clickForgotPasswordLink() {
        clickElement(forgotPasswordLink);
        logInfo("Clicked forgot password link");
        return this;
    }

    /**
     * Get page header title
     * @return Header title text
     */
    public String getHeaderTitle() {
        String title = getElementText(headerTitle);
        logInfo("Page header title: " + title);
        return title;
    }

    /**
     * Wait for page to load (wait for loading spinner to disappear)
     * @return SamplePage instance
     */
    public SamplePage waitForPageLoad() {
        // Wait for loading spinner to disappear if present
        if (isElementDisplayed(loadingSpinner)) {
            // Wait for spinner to disappear with custom wait utility
            com.automation.utils.WaitUtils.waitForElementToDisappear(driver, loadingSpinner);
            logInfo("Page loaded - loading spinner disappeared");
        }
        waitForPageToLoad();
        return this;
    }

    /**
     * Check if success message is displayed
     * @return true if success message is displayed
     */
    public boolean isSuccessMessageDisplayed() {
        boolean isDisplayed = isElementDisplayed(successMessage);
        logInfo("Success message displayed: " + isDisplayed);
        return isDisplayed;
    }

    /**
     * Get success message text
     * @return Success message text
     */
    public String getSuccessMessage() {
        String message = getElementText(successMessage);
        logInfo("Success message: " + message);
        return message;
    }

    /**
     * Check if login button is enabled
     * @return true if login button is enabled
     */
    public boolean isLoginButtonEnabled() {
        boolean isEnabled = loginButton.isEnabled();
        logInfo("Login button enabled: " + isEnabled);
        return isEnabled;
    }

    /**
     * Clear username field
     * @return SamplePage instance
     */
    public SamplePage clearUsername() {
        usernameField.clear();
        logInfo("Cleared username field");
        return this;
    }

    /**
     * Clear password field
     * @return SamplePage instance
     */
    public SamplePage clearPassword() {
        passwordField.clear();
        logInfo("Cleared password field");
        return this;
    }

    /**
     * Get username field placeholder text
     * @return Placeholder text
     */
    public String getUsernamePlaceholder() {
        String placeholder = getElementAttribute(By.id("username"), "placeholder");
        logInfo("Username placeholder: " + placeholder);
        return placeholder;
    }

    /**
     * Check if username field is displayed
     * @return true if username field is displayed
     */
    public boolean isUsernameFieldDisplayed() {
        boolean isDisplayed = isElementDisplayed(usernameField);
        logInfo("Username field displayed: " + isDisplayed);
        return isDisplayed;
    }

    /**
     * Check if password field is displayed
     * @return true if password field is displayed
     */
    public boolean isPasswordFieldDisplayed() {
        boolean isDisplayed = isElementDisplayed(passwordField);
        logInfo("Password field displayed: " + isDisplayed);
        return isDisplayed;
    }

    /**
     * Verify page title contains expected text
     * @param expectedTitle Expected title text
     * @return true if title contains expected text
     */
    public boolean verifyPageTitle(String expectedTitle) {
        String actualTitle = getPageTitle();
        boolean contains = actualTitle.contains(expectedTitle);
        logInfo("Page title verification - Expected: " + expectedTitle + ", Actual: " + actualTitle + ", Result: " + contains);
        return contains;
    }

    /**
     * Hover over login button
     * @return SamplePage instance
     */
    public SamplePage hoverOverLoginButton() {
        hoverOverElement(By.xpath("//button[@type='submit']"));
        logInfo("Hovered over login button");
        return this;
    }

    /**
     * Double click on username field
     * @return SamplePage instance
     */
    public SamplePage doubleClickUsernameField() {
        doubleClickElement(By.id("username"));
        logInfo("Double clicked username field");
        return this;
    }

    /**
     * Scroll to login button
     * @return SamplePage instance
     */
    public SamplePage scrollToLoginButton() {
        scrollToElement(By.xpath("//button[@type='submit']"));
        logInfo("Scrolled to login button");
        return this;
    }

    /**
     * Helper method for logging info messages
     * @param message Message to log
     */
    private void logInfo(String message) {
        logger.info("SamplePage: " + message);
    }
}