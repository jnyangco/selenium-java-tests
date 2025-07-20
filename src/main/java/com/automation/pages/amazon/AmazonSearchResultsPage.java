package com.automation.pages.amazon;

import com.automation.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AmazonSearchResultsPage extends BasePage {
    
    // Page Locators
    private final By productTitles = By.cssSelector("[data-component-type='s-search-result'] h2 a span");
    private final By productPrices = By.cssSelector("[data-component-type='s-search-result'] .a-price-whole");
    private final By productRatings = By.cssSelector("[data-component-type='s-search-result'] .a-icon-alt");
    private final By resultsInfo = By.cssSelector("span[data-component-type='s-result-info-bar']");
    private final By nextPageButton = By.cssSelector("a[aria-label='Next page']");
    private final By previousPageButton = By.cssSelector("a[aria-label='Previous page']");
    private final By searchResults = By.cssSelector("[data-component-type='s-search-result']");
    private final By filterSection = By.cssSelector("#s-refinements");
    private final By sortDropdown = By.cssSelector("#s-result-sort-select");
    private final By searchBox = By.id("twotabsearchtextbox");
    
    public AmazonSearchResultsPage(WebDriver driver) {
        super(driver);
    }
    
    @Step("Get product count")
    public int getProductCount() {
        waitForElementsToBeVisible(productTitles);
        return findElements(productTitles).size();
    }
    
    @Step("Get first product title")
    public String getFirstProductTitle() {
        List<WebElement> titles = findElements(productTitles);
        if (!titles.isEmpty()) {
            return titles.get(0).getText();
        }
        return "";
    }
    
    @Step("Click on product at index: {index}")
    public void clickProduct(int index) {
        List<WebElement> titles = findElements(productTitles);
        if (index < titles.size()) {
            titles.get(index).click();
        } else {
            throw new IndexOutOfBoundsException("Product index out of bounds");
        }
    }
    
    @Step("Get all product titles")
    public List<String> getAllProductTitles() {
        waitForElementsToBeVisible(productTitles);
        return findElements(productTitles).stream()
                .map(WebElement::getText)
                .toList();
    }
    
    @Step("Get all product prices")
    public List<String> getAllProductPrices() {
        return findElements(productPrices).stream()
                .map(WebElement::getText)
                .toList();
    }
    
    @Step("Get results information")
    public String getResultsInfo() {
        return getText(resultsInfo);
    }
    
    @Step("Verify search results are displayed")
    public boolean areSearchResultsDisplayed() {
        return !findElements(productTitles).isEmpty() && isDisplayed(resultsInfo);
    }
    
    @Step("Go to next page")
    public AmazonSearchResultsPage goToNextPage() {
        if (isDisplayed(nextPageButton)) {
            click(nextPageButton);
        }
        return this;
    }
    
    @Step("Go to previous page")
    public AmazonSearchResultsPage goToPreviousPage() {
        if (isDisplayed(previousPageButton)) {
            click(previousPageButton);
        }
        return this;
    }
    
    @Step("Sort results by: {sortOption}")
    public AmazonSearchResultsPage sortBy(String sortOption) {
        selectByVisibleText(sortDropdown, sortOption);
        return this;
    }
    
    @Step("Filter by price range")
    public AmazonSearchResultsPage filterByPriceRange(String minPrice, String maxPrice) {
        // Implementation for price filtering
        By minPriceInput = By.id("low-price");
        By maxPriceInput = By.id("high-price");
        By goButton = By.cssSelector("input[aria-labelledby='a-autoid-1-announce']");
        
        if (isDisplayed(minPriceInput)) {
            sendKeys(minPriceInput, minPrice);
        }
        if (isDisplayed(maxPriceInput)) {
            sendKeys(maxPriceInput, maxPrice);
        }
        if (isDisplayed(goButton)) {
            click(goButton);
        }
        return this;
    }
    
    @Step("Perform new search: {searchTerm}")
    public AmazonSearchResultsPage searchAgain(String searchTerm) {
        sendKeys(searchBox, searchTerm);
        findElement(searchBox).submit();
        return this;
    }
    
    @Step("Check if product contains text: {text}")
    public boolean doesProductContainText(String text) {
        return getAllProductTitles().stream()
                .anyMatch(title -> title.toLowerCase().contains(text.toLowerCase()));
    }
    
    @Step("Get product rating at index: {index}")
    public String getProductRating(int index) {
        List<WebElement> ratings = findElements(productRatings);
        if (index < ratings.size()) {
            return getAttribute(By.cssSelector("[data-component-type='s-search-result']:nth-child(" + (index + 1) + ") .a-icon-alt"), "alt");
        }
        return "";
    }
}