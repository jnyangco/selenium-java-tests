package com.automation.pages;

import com.automation.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

// SOLUTION 1: Static imports for simplified logging (RECOMMENDED)


public class LeetcodePage extends BasePage {

    // Page Locators - Search
    private final By searchBox = By.name("q");
    private final By searchButton = By.name("btnK");
    private final By feelingLuckyButton = By.name("btnI");
    private final By googleSearchButton = By.cssSelector("input[value='Google Search']");


    public LeetcodePage(WebDriver driver) {
        super(driver);
    }

    public LeetcodePage openLeetcodeSite() {
        logInfo("Opening leetcode site");
        driver.get("https://letcode.in/test");
        return this;
    }

    // Navigation Methods - NOW WITH SIMPLIFIED LOGGING!
    public LeetcodePage navigateToGoogle() {
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

}