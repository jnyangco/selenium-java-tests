package com.automation.base;

import com.automation.utils.ExtentManager;
import io.github.bonigarcia.wdm.WebDriverManager;
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
    
    // ThreadLocal for parallel safety - FIXED
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    
    // Configuration with proper defaults
    private String browser = System.getProperty("browser", "chrome");
    private boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
    private int implicitWait = Integer.parseInt(System.getProperty("implicit.wait", "10"));
    private int pageLoadTimeout = Integer.parseInt(System.getProperty("page.load.timeout", "30"));
    
    // Public getter for driver - FIXED
    protected WebDriver getDriver() {
        return driverThreadLocal.get();
    }
    
    // For backward compatibility
    protected WebDriver driver;
    
    @BeforeSuite
    public void beforeSuite() {
        ExtentManager.getInstance(); // Initialize ExtentReports
        ExtentManager.logInfo("Test Suite Started");
    }
    
    @AfterSuite
    public void afterSuite() {
        ExtentManager.logInfo("Test Suite Completed");
        ExtentManager.flush(); // Generate the report
    }
    
    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browserParam, ITestResult result) {
        // Create ExtentTest for this test method - FIXED thread safety
        String testName = result.getMethod().getMethodName();
        String testDescription = result.getMethod().getDescription();
        if (testDescription == null || testDescription.isEmpty()) {
            testDescription = "Test method: " + testName;
        }
        ExtentManager.createTest(testName, testDescription);
        
        // FIXED: Proper handling of browser parameter
        if (browserParam != null && !browserParam.trim().isEmpty() && !"chrome".equals(browserParam.trim())) {
            this.browser = browserParam.trim();
        } else {
            // Fallback to system property or default
            this.browser = System.getProperty("browser", "chrome");
        }
        
        logger.info("Setting up WebDriver for browser: {} on thread: {}", browser, Thread.currentThread().getName());
        ExtentManager.logInfo("Setting up WebDriver for browser: " + browser + " on thread: " + Thread.currentThread().getName());
        
        initializeDriver();
        configureDriver();
        
        // Set the driver for backward compatibility
        this.driver = getDriver();
        
        logger.info("WebDriver setup completed successfully on thread: {}", Thread.currentThread().getName());
        ExtentManager.logPass("WebDriver setup completed successfully");
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
        String testName = result.getName();
        WebDriver currentDriver = getDriver();
        
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("Test failed: {}", testName);
            ExtentManager.logFail("Test failed: " + testName);
            
            // Take screenshot on failure
            if (currentDriver != null) {
                String screenshot = takeScreenshotAsBase64("Test Failed: " + testName);
                if (screenshot != null) {
                    ExtentManager.addScreenshot(screenshot, "Screenshot on test failure");
                }
            }
            
            // Log the exception
            Throwable throwable = result.getThrowable();
            if (throwable != null) {
                ExtentManager.logFail("Test failure reason: " + throwable.getMessage());
            }
            
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.info("Test passed: {}", testName);
            ExtentManager.logPass("Test passed: " + testName);
            
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.warn("Test skipped: {}", testName);
            ExtentManager.logSkip("Test skipped: " + testName);
            
            // Log the skip reason
            Throwable throwable = result.getThrowable();
            if (throwable != null) {
                ExtentManager.logSkip("Test skip reason: " + throwable.getMessage());
            }
        }
        
        if (currentDriver != null) {
            logger.info("Closing WebDriver on thread: {}", Thread.currentThread().getName());
            ExtentManager.logInfo("Closing WebDriver");
            try {
                currentDriver.quit();
            } catch (Exception e) {
                logger.warn("Error closing driver: {}", e.getMessage());
            } finally {
                driverThreadLocal.remove(); // Clean up ThreadLocal
            }
        }
        
        // Remove the ExtentTest from ThreadLocal
        ExtentManager.removeTest();
    }
    
    private void initializeDriver() {
        // FIXED: Added null/empty check and better error message
        if (browser == null || browser.trim().isEmpty()) {
            browser = "chrome"; // Default fallback
            logger.warn("Browser parameter was null or empty, defaulting to Chrome");
            // ExtentManager.logWarning("Browser parameter was null or empty, defaulting to Chrome");
        }
        
        WebDriver newDriver = null;
        
        try {
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
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-web-security");
                    chromeOptions.addArguments("--disable-features=VizDisplayCompositor");
                    newDriver = new ChromeDriver(chromeOptions);
                    break;
                    
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless) {
                        firefoxOptions.addArguments("--headless");
                    }
                    newDriver = new FirefoxDriver(firefoxOptions);
                    break;
                    
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (headless) {
                        edgeOptions.addArguments("--headless");
                    }
                    newDriver = new EdgeDriver(edgeOptions);
                    break;
                    
                default:
                    logger.error("Unsupported browser: '{}'. Supported browsers: chrome, firefox, edge", browser);
                    ExtentManager.logFail("Unsupported browser: '" + browser + "'. Supported browsers: chrome, firefox, edge");
                    throw new IllegalArgumentException("Unsupported browser: '" + browser + "'. Supported browsers: chrome, firefox, edge");
            }
            
            // Store in ThreadLocal for parallel safety
            driverThreadLocal.set(newDriver);
            logger.info("Driver initialized successfully for browser: {} on thread: {}", browser, Thread.currentThread().getName());
            
        } catch (Exception e) {
            logger.error("Failed to initialize driver for browser: {}", browser, e);
            ExtentManager.logFail("Failed to initialize driver: " + e.getMessage());
            if (newDriver != null) {
                try {
                    newDriver.quit();
                } catch (Exception quitException) {
                    logger.warn("Error quitting driver after initialization failure: {}", quitException.getMessage());
                }
            }
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
    }
    
    private void configureDriver() {
        WebDriver currentDriver = getDriver();
        if (currentDriver != null) {
            currentDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
            currentDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
            currentDriver.manage().window().maximize();
            logger.info("Driver configured successfully");
        } else {
            throw new RuntimeException("Driver is null, cannot configure");
        }
    }
    
    public String takeScreenshotAsBase64(String name) {
        logger.info("Taking screenshot: {}", name);
        try {
            WebDriver currentDriver = getDriver();
            if (currentDriver != null) {
                return ((TakesScreenshot) currentDriver).getScreenshotAs(OutputType.BASE64);
            } else {
                logger.warn("Driver is null, cannot take screenshot");
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to take screenshot: {}", e.getMessage());
            ExtentManager.logFail("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }
    
    // Utility methods for test step logging - Enhanced with static access
    protected void logPass(String message) {
        ExtentManager.logPass(message);
    }
    
    protected void logFail(String message) {
        ExtentManager.logFail(message);
    }
    
    protected void logInfo(String message) {
        ExtentManager.logInfo(message);
    }
    
    protected void logWarning(String message) {
        ExtentManager.logWarning(message);
    }
    
    protected void logSkip(String message) {
        ExtentManager.logSkip(message);
    }
    
    // Static utility methods for direct access (alternative approach)
    public static void info(String message) {
        ExtentManager.logInfo(message);
    }
    
    public static void pass(String message) {
        ExtentManager.logPass(message);
    }
    
    public static void fail(String message) {
        ExtentManager.logFail(message);
    }
    
    public static void warning(String message) {
        ExtentManager.logWarning(message);
    }
    
    public static void skip(String message) {
        ExtentManager.logSkip(message);
    }
}