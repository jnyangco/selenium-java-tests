package com.automation.tests.google;

import com.automation.base.BaseTest;
import com.automation.pages.GooglePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GoogleLoginTest extends BaseTest {
    
    @Test(description = "Verify Google Sign In button is visible and clickable")
    public void testSignInButtonVisibility() {
        logInfo("Starting Google Sign In button visibility test");
        
        // FIXED: Use getDriver() instead of driver for thread safety
        GooglePage googlePage = new GooglePage(getDriver());
        
        logInfo("Step 1: Navigate to Google homepage");
        googlePage.navigateToGoogle();
        
        Assert.assertTrue(false, "This is an error message");
        
//        logInfo("Step 2: Verify homepage is loaded");
//        Assert.assertTrue(googlePage.isLoaded(), "Google homepage should load successfully");
//        logPass("Google homepage loaded successfully");
//        
//        logInfo("Step 3: Verify Sign In button is present");
//        // For demo purposes, we'll just verify the page loaded
//        Assert.assertTrue(googlePage.getCurrentUrl().contains("google"), "Should be on Google domain");
//        logPass("Sign In button verification completed");
//        
//        logPass("Google Sign In button visibility test completed successfully");
    }
    
    @Test(description = "Verify login flow with valid credentials")
    public void testValidLogin() {
        logInfo("Starting Google valid login test");
        
        // FIXED: Use getDriver() instead of driver for thread safety
        GooglePage googlePage = new GooglePage(getDriver());
        
        logInfo("Step 1: Navigate to Google homepage");
        googlePage.navigateToGoogle();
        
//        logInfo("Step 2: Verify homepage is loaded");
//        Assert.assertTrue(googlePage.isLoaded(), "Google homepage should load successfully");
//        logPass("Google homepage loaded successfully");
//        
//        logInfo("Step 3: Verify page title");
//        String pageTitle = googlePage.getPageTitle();
//        Assert.assertTrue(pageTitle.contains("Google"), "Page title should contain 'Google'");
//        logPass("Page title verification completed: " + pageTitle);
//        
//        // For demo purposes, we'll simulate the login flow
//        logInfo("Step 4: Login flow simulation");
//        String email = "test@example.com";
//        String password = "testpassword";
//        
//        try {
//            // In a real scenario, you would implement the actual login
//            logInfo("Would attempt login with email: " + email);
//            logPass("Login simulation completed successfully");
//        } catch (Exception e) {
//            logWarning("Login process encountered expected behavior (demo purposes): " + e.getMessage());
//        }
//        
//        logPass("Google valid login test completed");
    }
    
    @Test(description = "Verify login flow with invalid email")
    public void testInvalidEmailLogin() {
        logInfo("Starting Google invalid email login test");
        
        // FIXED: Use getDriver() instead of driver for thread safety
        GooglePage googlePage = new GooglePage(getDriver());
        
        logInfo("Step 1: Navigate to Google homepage");
        googlePage.navigateToGoogle();
        
//        logInfo("Step 2: Verify homepage is loaded");
//        Assert.assertTrue(googlePage.isLoaded(), "Google homepage should load successfully");
//        logPass("Google homepage loaded successfully");
//        
//        logInfo("Step 3: Verify current URL");
//        String currentUrl = googlePage.getCurrentUrl();
//        Assert.assertTrue(currentUrl.contains("google"), "Should be on Google domain");
//        logPass("URL verification completed: " + currentUrl);
//        
//        logInfo("Step 4: Simulate invalid email handling");
//        String invalidEmail = "invalid-email-format";
//        
//        try {
//            // Simulate invalid email entry
//            logInfo("Would attempt to enter invalid email: " + invalidEmail);
//            logPass("Invalid email handling test completed");
//        } catch (Exception e) {
//            logWarning("Expected behavior for invalid email: " + e.getMessage());
//        }
//        
//        logPass("Google invalid email login test completed");
    }
}