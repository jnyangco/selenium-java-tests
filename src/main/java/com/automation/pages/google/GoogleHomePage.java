package com.automation.pages.google;

import com.automation.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GoogleHomePage extends BasePage {
    
    // Page Locators
    private final By searchBox = By.name("q");
    private final By searchButton = By.name("btnK");
    private final By feelingLuckyButton = By.name("btnI");
    private final By googleSearchButton = By.cssSelector("input[value='Google Search']");
    
    public GoogleHomePage(WebDriver driver) {
        super(driver);
    }
    
    @Step("Navigate to Google homepage")
    public GoogleHomePage navigateToGoogle() {
        driver.get("https://www.google.com");
        waitForPageTitle("Google");
        return this;
    }
    
    @Step("Enter search term: {searchTerm}")
    public GoogleHomePage enterSearchTerm(String searchTerm) {
        sendKeys(searchBox, searchTerm);
        return this;
    }
    
    @Step("Click search button")
    public GoogleSearchResultsPage clickSearchButton() {
        click(searchButton);
        return new GoogleSearchResultsPage(driver);
    }
    
    @Step("Click I'm Feeling Lucky button")
    public void clickFeelingLuckyButton() {
        click(feelingLuckyButton);
    }
    
    @Step("Perform search for: {searchTerm}")
    public GoogleSearchResultsPage search(String searchTerm) {
        enterSearchTerm(searchTerm);
        return clickSearchButton();
    }
    
    @Step("Verify Google homepage is loaded")
    public boolean isLoaded() {
        return isDisplayed(searchBox) && 
               isDisplayed(searchButton) && 
               driver.getTitle().contains("Google");
    }
    
    @Step("Get search box placeholder text")
    public String getSearchBoxPlaceholder() {
        return getAttribute(searchBox, "placeholder");
    }
}