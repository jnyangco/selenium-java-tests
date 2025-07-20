package com.automation.tests.google;

import com.automation.base.BaseTest;
import com.automation.pages.google.GoogleHomePage;
import com.automation.pages.google.GoogleSearchResultsPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Google Search Tests")
@Feature("Search Functionality")
public class GoogleTest extends BaseTest {
    
    @Test(description = "Simple smoke test to verify setup")
    @Story("Basic Setup Verification")
    @Severity(SeverityLevel.CRITICAL)
    public void smokeTest() {
        // Just navigate to Google and check title
        driver.get("https://www.google.com");
        
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());
        
        Assert.assertTrue(driver.getTitle().contains("Google"), "Page title should contain 'Google'");
        System.out.println("✅ Smoke test passed - Browser and WebDriver working!");
    }
    
//    @Test(description = "Verify Google homepage loads successfully")
//    @Story("Homepage Verification")  
//    @Severity(SeverityLevel.CRITICAL)
//    public void testGoogleHomepageLoads() {
//        GoogleHomePage homePage = new GoogleHomePage(driver);
//        
//        homePage.navigateToGoogle();
//        
//        // Add some debugging information
//        System.out.println("Current URL: " + driver.getCurrentUrl());
//        System.out.println("Page Title: " + driver.getTitle());
//        
//        // Check individual conditions
//        Assert.assertTrue(driver.getTitle().contains("Google"), "Page title should contain 'Google'");
//        Assert.assertTrue(homePage.isLoaded(), "Google homepage should load successfully");
//    }
    
//    @Test(description = "Perform a search and verify results are displayed")
//    @Story("Search Results")
//    @Severity(SeverityLevel.CRITICAL)
//    public void testGoogleSearch() {
//        GoogleHomePage homePage = new GoogleHomePage(driver);
//        String searchTerm = "Selenium WebDriver";
//        
//        homePage.navigateToGoogle();
//        GoogleSearchResultsPage resultsPage = homePage.search(searchTerm);
//        
//        Assert.assertTrue(resultsPage.areSearchResultsDisplayed(), "Search results should be displayed");
//        Assert.assertTrue(resultsPage.getSearchResultsCount() > 0, "Search should return results");
//        Assert.assertTrue(resultsPage.doesResultContainText("Selenium"), "Results should contain search term");
//    }
    
//    @Test(description = "Verify search result navigation")
//    @Story("Search Navigation")
//    @Severity(SeverityLevel.NORMAL)
//    public void testSearchResultNavigation() {
//        GoogleHomePage homePage = new GoogleHomePage(driver);
//        String searchTerm = "Java programming";
//        
//        homePage.navigateToGoogle();
//        GoogleSearchResultsPage resultsPage = homePage.search(searchTerm);
//        
//        String firstResultTitle = resultsPage.getFirstResultTitle();
//        Assert.assertFalse(firstResultTitle.isEmpty(), "First result should have a title");
//        
//        // Verify we can get result statistics
//        String stats = resultsPage.getResultStatistics();
//        Assert.assertFalse(stats.isEmpty(), "Result statistics should be displayed");
//    }
    
//    @Test(description = "Verify multiple searches can be performed")
//    @Story("Multiple Searches")
//    @Severity(SeverityLevel.NORMAL)
//    public void testMultipleSearches() {
//        GoogleHomePage homePage = new GoogleHomePage(driver);
//        
//        homePage.navigateToGoogle();
//        
//        // First search
//        GoogleSearchResultsPage resultsPage = homePage.search("TestNG");
//        Assert.assertTrue(resultsPage.areSearchResultsDisplayed(), "First search results should be displayed");
//        
//        // Second search from results page
//        resultsPage.searchAgain("Maven");
//        Assert.assertTrue(resultsPage.areSearchResultsDisplayed(), "Second search results should be displayed");
//        Assert.assertTrue(resultsPage.doesResultContainText("Maven"), "Results should contain new search term");
//    }
}