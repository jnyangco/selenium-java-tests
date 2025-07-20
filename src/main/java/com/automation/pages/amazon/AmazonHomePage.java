package com.automation.pages.amazon;

import com.automation.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AmazonHomePage extends BasePage {
    
    // Page Locators
    private final By searchBox = By.id("twotabsearchtextbox");
    private final By searchButton = By.id("nav-search-submit-button");
    private final By amazonLogo = By.id("nav-logo-sprites");
    private final By signInButton = By.cssSelector("#nav-signin-tooltip .nav-action-inner");
    private final By cartButton = By.id("nav-cart");
    private final By allDepartmentsMenu = By.cssSelector("#nav-main [data-menu-id='16']");
    private final By categoryDropdown = By.cssSelector("#searchDropdownBox");
    private final By cartCount = By.id("nav-cart-count");
    private final By accountName = By.cssSelector("#nav-link-accountList .nav-line-1");
    
    public AmazonHomePage(WebDriver driver) {
        super(driver);
    }
    
    @Step("Navigate to Amazon homepage")
    public AmazonHomePage navigateToAmazon() {
        driver.get("https://www.amazon.com");
        waitForElementToBeVisible(amazonLogo);
        return this;
    }
    
    @Step("Enter search term: {searchTerm}")
    public AmazonHomePage enterSearchTerm(String searchTerm) {
        sendKeys(searchBox, searchTerm);
        return this;
    }
    
    @Step("Click search button")
    public AmazonSearchResultsPage clickSearchButton() {
        click(searchButton);
        return new AmazonSearchResultsPage(driver);
    }
    
    @Step("Perform search for: {searchTerm}")
    public AmazonSearchResultsPage search(String searchTerm) {
        enterSearchTerm(searchTerm);
        return clickSearchButton();
    }
    
    @Step("Select category: {category}")
    public AmazonHomePage selectCategory(String category) {
        selectByVisibleText(categoryDropdown, category);
        return this;
    }
    
    @Step("Click sign in button")
    public void clickSignIn() {
        click(signInButton);
    }
    
    @Step("Click cart button")
    public void clickCart() {
        click(cartButton);
    }
    
    @Step("Verify Amazon homepage is loaded")
    public boolean isLoaded() {
        return isDisplayed(searchBox) && 
               isDisplayed(amazonLogo) && 
               driver.getTitle().contains("Amazon");
    }
    
    @Step("Get cart count")
    public String getCartCount() {
        return getText(cartCount);
    }
    
    @Step("Check if user is signed in")
    public boolean isUserSignedIn() {
        try {
            String text = getText(accountName);
            return !text.contains("Hello, sign in");
        } catch (Exception e) {
            return false;
        }
    }
}