package com.automation.pages;

import com.automation.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

// SOLUTION 1: Static imports for simplified logging (RECOMMENDED)
import static com.automation.utils.ExtentManager.logPass;
import static com.automation.utils.ExtentManager.logFail;
import static com.automation.utils.ExtentManager.logInfo;
import static com.automation.utils.ExtentManager.logWarning;
import static com.automation.utils.ExtentManager.logSkip;

public class GooglePage extends BasePage {
    
    // Page Locators - Search
    private final By searchBox = By.name("q");
    private final By searchButton = By.name("btnK");
    private final By feelingLuckyButton = By.name("btnI");
    private final By googleSearchButton = By.cssSelector("input[value='Google Search']");
    
    // Page Locators - Search Results
    private final By searchResultTitles = By.cssSelector("h3");
    private final By resultStats = By.cssSelector("#result-stats");
    private final By nextPageLink = By.cssSelector("a[href*='next']");
    private final By previousPageLink = By.cssSelector("a[href*='prev']");
    private final By searchResults = By.cssSelector("#search .g");
    
    // Page Locators - Login/Signup (if needed)
    private final By signInButton = By.linkText("Sign in");
    private final By emailInput = By.id("identifierId");
    private final By passwordInput = By.name("password");
    private final By nextButton = By.id("identifierNext");
    private final By passwordNextButton = By.id("passwordNext");
    private final By createAccountButton = By.linkText("Create account");
    
    public GooglePage(WebDriver driver) {
        super(driver);
    }
    
    // Navigation Methods - NOW WITH SIMPLIFIED LOGGING!
    public GooglePage navigateToGoogle() {
        logInfo("Navigating to Google homepage");             // ✅ Simplified!
        driver.get("https://www.google.com");
        waitForPageTitle("Google");
        logPass("Successfully navigated to Google homepage"); // ✅ Simplified!
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return this;
    }
    
    // Search Methods - NOW WITH SIMPLIFIED LOGGING!
    public GooglePage enterSearchTerm(String searchTerm) {
        logInfo("Entering search term: " + searchTerm);       // ✅ Simplified!
        sendKeys(searchBox, searchTerm);
        logPass("Successfully entered search term: " + searchTerm); // ✅ Simplified!
        return this;
    }
    
    public GooglePage clickSearchButton() {
        logInfo("Clicking search button");                    // ✅ Simplified!
        click(searchButton);
        logPass("Successfully clicked search button");        // ✅ Simplified!
        return this;
    }
    
    public GooglePage clickFeelingLuckyButton() {
        logInfo("Clicking I'm Feeling Lucky button");         // ✅ Simplified!
        click(feelingLuckyButton);
        logPass("Successfully clicked I'm Feeling Lucky button"); // ✅ Simplified!
        return this;
    }
    
    public GooglePage search(String searchTerm) {
        logInfo("Performing search for: " + searchTerm);      // ✅ Simplified!
        enterSearchTerm(searchTerm);
        clickSearchButton();
        logPass("Successfully performed search for: " + searchTerm); // ✅ Simplified!
        return this;
    }
    
    // Search Results Methods - NOW WITH SIMPLIFIED LOGGING!
    public int getSearchResultsCount() {
        logInfo("Getting search results count");              // ✅ Simplified!
        waitForElementsToBeVisible(searchResultTitles);
        int count = findElements(searchResultTitles).size();
        logInfo("Found " + count + " search results");        // ✅ Simplified!
        return count;
    }
    
    public String getFirstResultTitle() {
        logInfo("Getting first search result title");         // ✅ Simplified!
        List<WebElement> titles = findElements(searchResultTitles);
        if (!titles.isEmpty()) {
            String title = titles.get(0).getText();
            logInfo("First result title: " + title);          // ✅ Simplified!
            return title;
        }
        logWarning("No search results found");                // ✅ Simplified!
        return "";
    }
    
    public void clickSearchResult(int index) {
        logInfo("Clicking search result at index: " + index); // ✅ Simplified!
        List<WebElement> titles = findElements(searchResultTitles);
        if (index < titles.size()) {
            titles.get(index).click();
            logPass("Successfully clicked search result at index: " + index); // ✅ Simplified!
        } else {
            logFail("Search result index out of bounds: " + index); // ✅ Simplified!
            throw new IndexOutOfBoundsException("Search result index out of bounds");
        }
    }
    
    public List<String> getAllResultTitles() {
        logInfo("Getting all search result titles");          // ✅ Simplified!
        waitForElementsToBeVisible(searchResultTitles);
        List<String> titles = findElements(searchResultTitles).stream()
                .map(WebElement::getText)
                .toList();
        logInfo("Retrieved " + titles.size() + " result titles"); // ✅ Simplified!
        return titles;
    }
    
    public String getResultStatistics() {
        logInfo("Getting result statistics");                 // ✅ Simplified!
        String stats = getText(resultStats);
        logInfo("Result statistics: " + stats);               // ✅ Simplified!
        return stats;
    }
    
    public boolean areSearchResultsDisplayed() {
        logInfo("Checking if search results are displayed");  // ✅ Simplified!
        boolean resultsDisplayed = !findElements(searchResultTitles).isEmpty() && isDisplayed(resultStats);
        logInfo("Search results displayed: " + resultsDisplayed); // ✅ Simplified!
        return resultsDisplayed;
    }
    
    public GooglePage goToNextPage() {
        logInfo("Going to next page");                         // ✅ Simplified!
        if (isDisplayed(nextPageLink)) {
            click(nextPageLink);
            logPass("Successfully navigated to next page");   // ✅ Simplified!
        } else {
            logWarning("Next page link not available");       // ✅ Simplified!
        }
        return this;
    }
    
    public GooglePage goToPreviousPage() {
        logInfo("Going to previous page");                     // ✅ Simplified!
        if (isDisplayed(previousPageLink)) {
            click(previousPageLink);
            logPass("Successfully navigated to previous page"); // ✅ Simplified!
        } else {
            logWarning("Previous page link not available");   // ✅ Simplified!
        }
        return this;
    }
    
    public GooglePage searchAgain(String searchTerm) {
        logInfo("Performing new search: " + searchTerm);      // ✅ Simplified!
        sendKeys(searchBox, searchTerm);
        findElement(searchBox).submit();
        logPass("Successfully performed new search: " + searchTerm); // ✅ Simplified!
        return this;
    }
    
    public boolean doesResultContainText(String text) {
        logInfo("Checking if results contain text: " + text); // ✅ Simplified!
        boolean contains = getAllResultTitles().stream()
                .anyMatch(title -> title.toLowerCase().contains(text.toLowerCase()));
        logInfo("Results contain text '" + text + "': " + contains); // ✅ Simplified!
        return contains;
    }
    
    // Login Methods - NOW WITH SIMPLIFIED LOGGING!
    public GooglePage clickSignIn() {
        logInfo("Clicking Sign In button");                   // ✅ Simplified!
        click(signInButton);
        logPass("Successfully clicked Sign In button");       // ✅ Simplified!
        return this;
    }
    
    public GooglePage enterEmail(String email) {
        logInfo("Entering email: " + email);                  // ✅ Simplified!
        sendKeys(emailInput, email);
        logPass("Successfully entered email");                // ✅ Simplified!
        return this;
    }
    
    public GooglePage clickNext() {
        logInfo("Clicking Next button");                      // ✅ Simplified!
        click(nextButton);
        logPass("Successfully clicked Next button");          // ✅ Simplified!
        return this;
    }
    
    public GooglePage enterPassword(String password) {
        logInfo("Entering password");                         // ✅ Simplified!
        sendKeys(passwordInput, password);
        logPass("Successfully entered password");             // ✅ Simplified!
        return this;
    }
    
    public GooglePage clickPasswordNext() {
        logInfo("Clicking password Next button");             // ✅ Simplified!
        click(passwordNextButton);
        logPass("Successfully clicked password Next button"); // ✅ Simplified!
        return this;
    }
    
    public GooglePage login(String email, String password) {
        logInfo("Performing login with email: " + email);     // ✅ Simplified!
        clickSignIn();
        enterEmail(email);
        clickNext();
        enterPassword(password);
        clickPasswordNext();
        logPass("Successfully completed login process");      // ✅ Simplified!
        return this;
    }
    
    public GooglePage clickCreateAccount() {
        logInfo("Clicking Create Account button");            // ✅ Simplified!
        click(createAccountButton);
        logPass("Successfully clicked Create Account button"); // ✅ Simplified!
        return this;
    }
    
    // Validation Methods - NOW WITH SIMPLIFIED LOGGING!
    public boolean isLoaded() {
        logInfo("Checking if Google homepage is loaded");     // ✅ Simplified!
        boolean loaded = isDisplayed(searchBox) && 
                        isDisplayed(searchButton) && 
                        getPageTitle().contains("Google");
        logInfo("Google homepage loaded: " + loaded);         // ✅ Simplified!
        return loaded;
    }
    
    public String getSearchBoxPlaceholder() {
        logInfo("Getting search box placeholder text");       // ✅ Simplified!
        String placeholder = getAttribute(searchBox, "placeholder");
        logInfo("Search box placeholder: " + placeholder);    // ✅ Simplified!
        return placeholder;
    }
    
    // Public methods for test access
    public String getCurrentUrl() {
        return super.getCurrentUrl();
    }
    
    public String getPageTitle() {
        return super.getPageTitle();
    }
}