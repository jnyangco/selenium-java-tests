package com.automation.utils;

import com.automation.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Driver Manager class to handle WebDriver instances
 */
public class DriverManager {
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * Initialize WebDriver based on browser configuration
     * @param browser Browser name
     * @return WebDriver instance
     */
    public static WebDriver initializeDriver(String browser) {
        WebDriver driver = null;

        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    driver = createChromeDriver();
                    break;
                case "firefox":
                    driver = createFirefoxDriver();
                    break;
                case "edge":
                    driver = createEdgeDriver();
                    break;
                case "safari":
                    driver = createSafariDriver();
                    break;
                default:
                    logger.error("Browser '" + browser + "' not supported. Using Chrome as default.");
                    driver = createChromeDriver();
            }

            // Set timeouts
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getPageLoadTimeout()));
            driver.manage().window().maximize();

            setDriver(driver);
            logger.info("WebDriver initialized successfully for browser: " + browser);

        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver: " + e.getMessage());
            throw new RuntimeException("WebDriver initialization failed", e);
        }

        return driver;
    }

    /**
     * Create Chrome WebDriver
     * @return ChromeDriver instance
     */
    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless");
        }

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--remote-allow-origins=*");

        return new ChromeDriver(options);
    }

    /**
     * Create Firefox WebDriver
     * @return FirefoxDriver instance
     */
    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless");
        }

        return new FirefoxDriver(options);
    }

    /**
     * Create Edge WebDriver
     * @return EdgeDriver instance
     */
    private static WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();

        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless");
        }

        return new EdgeDriver(options);
    }

    /**
     * Create Safari WebDriver
     * @return SafariDriver instance
     */
    private static WebDriver createSafariDriver() {
        return new SafariDriver();
    }

    /**
     * Create Remote WebDriver for Grid execution
     * @param browser Browser name
     * @param hubUrl Grid hub URL
     * @return RemoteWebDriver instance
     */
    public static WebDriver createRemoteDriver(String browser, String hubUrl) {
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            WebDriver driver = new RemoteWebDriver(new URL(hubUrl), options);
            setDriver(driver);
            return driver;

        } catch (MalformedURLException e) {
            logger.error("Invalid Grid Hub URL: " + hubUrl);
            throw new RuntimeException("Failed to create RemoteWebDriver", e);
        }
    }

    /**
     * Set WebDriver instance to ThreadLocal
     * @param driver WebDriver instance
     */
    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    /**
     * Get WebDriver instance from ThreadLocal
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    /**
     * Quit WebDriver and remove from ThreadLocal
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
                logger.info("WebDriver quit successfully");
            } catch (Exception e) {
                logger.error("Error while quitting WebDriver: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }
}