package com.automation.tests.amazon;

import com.automation.base.BaseTest;
import com.automation.pages.amazon.AmazonHomePage;
import com.automation.pages.amazon.AmazonSearchResultsPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Amazon Tests")
@Feature("Product Search and Navigation")
public class AmazonTest extends BaseTest {
    
    @Test(description = "Verify Amazon homepage loads successfully")
    @Story("Homepage Verification")
    @Severity(SeverityLevel.CRITICAL)
    public void testAmazonHomepageLoads() {
        AmazonHomePage homePage = new AmazonHomePage(driver);
        
        homePage.navigateToAmazon();
        
        Assert.assertTrue(homePage.isLoaded(), "Amazon homepage should load successfully");
        Assert.assertTrue(driver.getTitle().contains("Amazon"), "Page title should contain 'Amazon'");
    }
    
//    @Test(description = "Perform a product search and verify results")
//    @Story("Product Search")
//    @Severity(SeverityLevel.CRITICAL)
//    public void testProductSearch() {
//        AmazonHomePage homePage = new AmazonHomePage(driver);
//        String searchTerm = "iPhone";
//        
//        homePage.navigateToAmazon();
//        AmazonSearchResultsPage resultsPage = homePage.search(searchTerm);
//        
//        Assert.assertTrue(resultsPage.areSearchResultsDisplayed(), "Search results should be displayed");
//        Assert.assertTrue(resultsPage.getProductCount() > 0, "Search should return products");
//        Assert.assertTrue(resultsPage.doesProductContainText("iPhone"), "Results should contain search term");
//    }
    
//    @Test(description = "Verify product details are displayed")
//    @Story("Product Information")
//    @Severity(SeverityLevel.NORMAL)
//    public void testProductDetails() {
//        AmazonHomePage homePage = new AmazonHomePage(driver);
//        String searchTerm = "laptop";
//        
//        homePage.navigateToAmazon();
//        AmazonSearchResultsPage resultsPage = homePage.search(searchTerm);
//        
//        String firstProductTitle = resultsPage.getFirstProductTitle();
//        Assert.assertFalse(firstProductTitle.isEmpty(), "First product should have a title");
//        
//        // Verify we can get product prices
//        var prices = resultsPage.getAllProductPrices();
//        Assert.assertFalse(prices.isEmpty(), "Products should have prices displayed");
//    }
    
//    @Test(description = "Verify search result sorting functionality")
//    @Story("Search Sorting")
//    @Severity(SeverityLevel.NORMAL)
//    public void testSearchSorting() {
//        AmazonHomePage homePage = new AmazonHomePage(driver);
//        String searchTerm = "books";
//        
//        homePage.navigateToAmazon();
//        AmazonSearchResultsPage resultsPage = homePage.search(searchTerm);
//        
//        Assert.assertTrue(resultsPage.areSearchResultsDisplayed(), "Search results should be displayed");
//        
//        // Get initial results count
//        int initialCount = resultsPage.getProductCount();
//        
//        // Try to sort (this might not always work due to Amazon's dynamic nature)
//        try {
//            resultsPage.sortBy("Price: Low to High");
//            // Verify results are still displayed after sorting
//            Assert.assertTrue(resultsPage.areSearchResultsDisplayed(), "Results should still be displayed after sorting");
//        } catch (Exception e) {
//            // Sort dropdown might not be available, just log and continue
//            System.out.println("Sorting not available for this search: " + e.getMessage());
//        }
//    }
    
//    @Test(description = "Verify cart functionality is accessible")
//    @Story("Cart Access")
//    @Severity(SeverityLevel.NORMAL)
//    public void testCartAccess() {
//        AmazonHomePage homePage = new AmazonHomePage(driver);
//        
//        homePage.navigateToAmazon();
//        
//        // Verify cart is accessible
//        String cartCount = homePage.getCartCount();
//        Assert.assertNotNull(cartCount, "Cart count should be displayed");
//        
//        // Cart count should be numeric (0 or more)
//        Assert.assertTrue(cartCount.matches("\\d+"), "Cart count should be numeric");
//    }
}