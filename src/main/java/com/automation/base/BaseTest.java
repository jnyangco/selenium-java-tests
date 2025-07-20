package com.automation.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;

public class BaseTest {
    
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    
    // ThreadLocal for parallel safety
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    
    // Keep this for backward compatibility
    protected WebDriver driver;
    
    // Configuration with proper defaults
    private String browser = System.getProperty("browser", "chrome");
    private boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
    private int implicitWait = Integer.parseInt(System.getProperty("implicit.wait", "10"));
    private int pageLoadTimeout = Integer.parseInt(System.getProperty("page.load.timeout", "30"));
    
    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browserParam) {
        // FIXED: Proper handling of browser parameter
        if (browserParam != null && !browserParam.trim().isEmpty()) {
            this.browser = browserParam.trim();
        } else {
            // Fallback to system property or default
            this.browser = System.getProperty("browser", "chrome");
        }
        
        logger.info("Setting up WebDriver for browser: {}", browser);
        initializeDriver();
        configureDriver();
        logger.info("WebDriver setup completed successfully");
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("Test failed: {}", result.getName());
            takeScreenshot("Test Failed: " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.info("Test passed: {}", result.getName());
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.warn("Test skipped: {}", result.getName());
        }
        
        if (driver != null) {
            logger.info("Closing WebDriver");
            driver.quit();
            driverThreadLocal.remove(); // Clean up ThreadLocal
        }
    }
    
    private void initializeDriver() {
        // FIXED: Added null/empty check and better error message
        if (browser == null || browser.trim().isEmpty()) {
            browser = "chrome"; // Default fallback
            logger.warn("Browser parameter was null or empty, defaulting to Chrome");
        }
        
        switch (browser.toLowerCase().trim()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless");
                }
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--window-size=1920,1080");
                driver = new ChromeDriver(chromeOptions);
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless");
                }
                driver = new EdgeDriver(edgeOptions);
                break;
                
            default:
                logger.error("Unsupported browser: '{}'. Supported browsers: chrome, firefox, edge", browser);
                throw new IllegalArgumentException("Unsupported browser: '" + browser + "'. Supported browsers: chrome, firefox, edge");
        }
        
        // Store in ThreadLocal for parallel safety
        driverThreadLocal.set(driver);
    }
    
    private void configureDriver() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        driver.manage().window().maximize();
    }
    
    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] takeScreenshot(String name) {
        logger.info("Taking screenshot: {}", name);
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            logger.error("Failed to take screenshot: {}", e.getMessage());
            return new byte[0];
        }
    }
    
//    protected void navigateTo(String url) {
//        logger.info("Navigating to URL: {}", url);
//        driver.get(url);
//        logger.info("Successfully navigated to: {}", url);
//    }
//    
//    protected String getCurrentUrl() {
//        return driver.getCurrentUrl();
//    }
//    
//    protected String getPageTitle() {
//        return driver.getTitle();
//    }
}