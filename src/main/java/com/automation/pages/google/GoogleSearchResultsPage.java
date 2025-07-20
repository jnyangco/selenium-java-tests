package com.automation.pages.google;

import com.automation.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GoogleSearchResultsPage extends BasePage {
    
    // Page Locators
    private final By searchResultTitles = By.cssSelector("h3");
    private final By resultStats = By.cssSelector("#result-stats");
    private final By searchBox = By.name("q");
    private final By nextPageLink = By.cssSelector("a[href*='next']");
    private final By previousPageLink = By.cssSelector("a[href*='prev']");
    private final By searchResults = By.cssSelector("#search .g");
    
    public GoogleSearchResultsPage(WebDriver driver) {
        super(driver);
    }
    
    @Step("Get search results count")
    public int getSearchResultsCount() {
        waitForElementsToBeVisible(searchResultTitles);
        return findElements(searchResultTitles).size();
    }
    
    @Step("Get first search result title")
    public String getFirstResultTitle() {
        List<WebElement> titles = findElements(searchResultTitles);
        if (!titles.isEmpty()) {
            return titles.get(0).getText();
        }
        return "";
    }
    
    @Step("Click on search result at index: {index}")
    public void clickSearchResult(int index) {
        List<WebElement> titles = findElements(searchResultTitles);
        if (index < titles.size()) {
            titles.get(index).click();
        } else {
            throw new IndexOutOfBoundsException("Search result index out of bounds");
        }
    }
    
    @Step("Get all search result titles")
    public List<String> getAllResultTitles() {
        waitForElementsToBeVisible(searchResultTitles);
        return findElements(searchResultTitles).stream()
                .map(WebElement::getText)
                .toList();
    }
    
    @Step("Get result statistics")
    public String getResultStatistics() {
        return getText(resultStats);
    }
    
    @Step("Verify search results are displayed")
    public boolean areSearchResultsDisplayed() {
        return !findElements(searchResultTitles).isEmpty() && isDisplayed(resultStats);
    }
    
    @Step("Go to next page")
    public GoogleSearchResultsPage goToNextPage() {
        if (isDisplayed(nextPageLink)) {
            click(nextPageLink);
        }
        return this;
    }
    
    @Step("Go to previous page")
    public GoogleSearchResultsPage goToPreviousPage() {
        if (isDisplayed(previousPageLink)) {
            click(previousPageLink);
        }
        return this;
    }
    
    @Step("Perform new search: {searchTerm}")
    public GoogleSearchResultsPage searchAgain(String searchTerm) {
        sendKeys(searchBox, searchTerm);
        findElement(searchBox).submit();
        return this;
    }
    
    @Step("Check if result contains text: {text}")
    public boolean doesResultContainText(String text) {
        return getAllResultTitles().stream()
                .anyMatch(title -> title.toLowerCase().contains(text.toLowerCase()));
    }
}