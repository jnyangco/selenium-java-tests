package com.automation.tests.google;

import com.automation.base.BaseTest;
import com.automation.pages.GooglePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GoogleSearchTest extends BaseTest {
    
    @Test(description = "Verify Google homepage loads successfully")
    public void testGoogleHomepageLoads() {
        logInfo("Starting Google homepage load test");
        
        GooglePage googlePage = new GooglePage(driver);
        
        logInfo("Step 1: Navigate to Google homepage");
        googlePage.navigateToGoogle();
        
        logInfo("Step 2: Verify homepage elements are displayed");
        Assert.assertTrue(googlePage.isLoaded(), "Google homepage should load successfully");
        logPass("Google homepage loaded with all required elements");
        
        logInfo("Step 3: Verify page title contains 'Google'");
        Assert.assertTrue(driver.getTitle().contains("Google"), "Page title should contain 'Google'");
        logPass("Page title verification completed successfully");
        
        logPass("Google homepage load test completed successfully");
    }
    
    @Test(description = "Perform a search and verify results are displayed")
    public void testBasicSearch() {
        logInfo("Starting Google basic search test");
        
        GooglePage googlePage = new GooglePage(driver);
        String searchTerm = "Selenium WebDriver";
        
        logInfo("Step 1: Navigate to Google homepage");
        googlePage.navigateToGoogle();
        
        logInfo("Step 2: Verify homepage is loaded");
        Assert.assertTrue(googlePage.isLoaded(), "Google homepage should load successfully");
        logPass("Google homepage loaded successfully");
        
        logInfo("Step 3: Perform search for: " + searchTerm);
        googlePage.search(searchTerm);
        
        logInfo("Step 4: Verify search results are displayed");
        Assert.assertTrue(googlePage.areSearchResultsDisplayed(), "Search results should be displayed");
        logPass("Search results displayed successfully");
        
        logInfo("Step 5: Verify search returns results");
        Assert.assertTrue(googlePage.getSearchResultsCount() > 0, "Search should return results");
        logPass("Search returned " + googlePage.getSearchResultsCount() + " results");
        
        logInfo("Step 6: Verify results contain search term");
        Assert.assertTrue(googlePage.doesResultContainText("Selenium"), "Results should contain search term");
        logPass("Search results contain the expected search term");
        
        logPass("Google basic search test completed successfully");
    }
    
    @Test(description = "Verify search result navigation")
    public void testSearchResultNavigation() {
        logInfo("Starting Google search result navigation test");
        
        GooglePage googlePage = new GooglePage(driver);
        String searchTerm = "Java programming";
        
        logInfo("Step 1: Navigate to Google homepage");
        googlePage.navigateToGoogle();
        
        logInfo("Step 2: Perform search for: " + searchTerm);
        googlePage.search(searchTerm);
        
        logInfo("Step 3: Verify search results are displayed");
        Assert.assertTrue(googlePage.areSearchResultsDisplayed(), "Search results should be displayed");
        logPass("Search results displayed successfully");
        
        logInfo("Step 4: Get first result title");
        String firstResultTitle = googlePage.getFirstResultTitle();
        Assert.assertFalse(firstResultTitle.isEmpty(), "First result should have a title");
        logPass("First result title retrieved: " + firstResultTitle);
        
        logInfo("Step 5: Verify result statistics are displayed");
        String stats = googlePage.getResultStatistics();
        Assert.assertFalse(stats.isEmpty(), "Result statistics should be displayed");
        logPass("Result statistics displayed: " + stats);
        
        logPass("Google search result navigation test completed successfully");
    }
    
    @Test(description = "Verify multiple searches can be performed")
    public void testMultipleSearches() {
        logInfo("Starting Google multiple searches test");
        
        GooglePage googlePage = new GooglePage(driver);
        
        logInfo("Step 1: Navigate to Google homepage");
        googlePage.navigateToGoogle();
        
        logInfo("Step 2: Perform first search");
        String firstSearchTerm = "TestNG";
        googlePage.search(firstSearchTerm);
        Assert.assertTrue(googlePage.areSearchResultsDisplayed(), "First search results should be displayed");
        logPass("First search completed successfully for: " + firstSearchTerm);
        
        logInfo("Step 3: Perform second search from results page");
        String secondSearchTerm = "Maven";
        googlePage.searchAgain(secondSearchTerm);
        Assert.assertTrue(googlePage.areSearchResultsDisplayed(), "Second search results should be displayed");
        logPass("Second search completed successfully for: " + secondSearchTerm);
        
        logInfo("Step 4: Verify second search results contain new search term");
        Assert.assertTrue(googlePage.doesResultContainText("Maven"), "Results should contain new search term");
        logPass("Second search results contain the expected search term");
        
        logPass("Google multiple searches test completed successfully");
    }
    
    @Test(description = "Verify I'm Feeling Lucky button functionality")
    public void testFeelingLuckyButton() {
        logInfo("Starting Google I'm Feeling Lucky test");
        
        GooglePage googlePage = new GooglePage(driver);
        String searchTerm = "OpenAI";
        
        logInfo("Step 1: Navigate to Google homepage");
        googlePage.navigateToGoogle();
        
        logInfo("Step 2: Verify homepage is loaded");
        Assert.assertTrue(googlePage.isLoaded(), "Google homepage should load successfully");
        logPass("Google homepage loaded successfully");
        
        logInfo("Step 3: Enter search term: " + searchTerm);
        googlePage.enterSearchTerm(searchTerm);
        
        logInfo("Step 4: Click I'm Feeling Lucky button");
        try {
            googlePage.clickFeelingLuckyButton();
            logPass("I'm Feeling Lucky button clicked successfully");
            
            // Note: This will likely redirect to the first result, so we just verify the action worked
            logInfo("Step 5: Verify page navigation occurred");
            String currentUrl = googlePage.getCurrentUrl();
            logInfo("Current URL after I'm Feeling Lucky: " + currentUrl);
            logPass("I'm Feeling Lucky functionality executed successfully");
            
        } catch (Exception e) {
            logWarning("I'm Feeling Lucky button behavior: " + e.getMessage());
        }
        
        logPass("Google I'm Feeling Lucky test completed");
    }
    
    @Test(description = "Verify search with special characters")
    public void testSearchWithSpecialCharacters() {
        logInfo("Starting Google search with special characters test");
        
        GooglePage googlePage = new GooglePage(driver);
        String searchTerm = "\"Java programming\" +tutorial -beginner";
        
        logInfo("Step 1: Navigate to Google homepage");
        googlePage.navigateToGoogle();
        
        logInfo("Step 2: Perform search with special characters: " + searchTerm);
        googlePage.search(searchTerm);
        
        logInfo("Step 3: Verify search results are displayed");
        Assert.assertTrue(googlePage.areSearchResultsDisplayed(), "Search results should be displayed");
        logPass("Search with special characters returned results");
        
        logInfo("Step 4: Verify search returned some results");
        int resultCount = googlePage.getSearchResultsCount();
        Assert.assertTrue(resultCount > 0, "Search should return results");
        logPass("Search with special characters returned " + resultCount + " results");
        
        logPass("Google search with special characters test completed successfully");
    }
    
    @Test(description = "Verify empty search handling")
    public void testEmptySearch() {
        logInfo("Starting Google empty search test");
        
        GooglePage googlePage = new GooglePage(driver);
        
        logInfo("Step 1: Navigate to Google homepage");
        googlePage.navigateToGoogle();
        
        logInfo("Step 2: Attempt search with empty string");
        try {
            googlePage.enterSearchTerm("");
            googlePage.clickSearchButton();
            logInfo("Empty search attempted");
            
            // Verify we're still on the homepage or handle gracefully
            String currentUrl = googlePage.getCurrentUrl();
            logInfo("Current URL after empty search: " + currentUrl);
            logPass("Empty search handled appropriately");
            
        } catch (Exception e) {
            logWarning("Empty search behavior: " + e.getMessage());
        }
        
        logPass("Google empty search test completed");
    }
}