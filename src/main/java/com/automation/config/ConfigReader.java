package com.automation.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to read configuration properties
 */
public class ConfigReader {
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";

    static {
        loadProperties();
    }

    /**
     * Load properties from config file
     */
    private static void loadProperties() {
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(fis);
            fis.close();
            logger.info("Configuration properties loaded successfully");
        } catch (IOException e) {
            logger.error("Failed to load configuration properties: " + e.getMessage());
            throw new RuntimeException("Configuration file not found at: " + CONFIG_FILE_PATH);
        }
    }

    /**
     * Get property value by key
     * @param key Property key
     * @return Property value
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property '" + key + "' not found in configuration file");
        }
        return value;
    }

    /**
     * Get property value with default fallback
     * @param key Property key
     * @param defaultValue Default value if key not found
     * @return Property value or default value
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get browser name from config
     * @return Browser name
     */
    public static String getBrowser() {
        return getProperty("browser", "chrome").toLowerCase();
    }

    /**
     * Get base URL from config
     * @return Base URL
     */
    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    /**
     * Get implicit wait timeout
     * @return Implicit wait timeout in seconds
     */
    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }

    /**
     * Get explicit wait timeout
     * @return Explicit wait timeout in seconds
     */
    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "20"));
    }

    /**
     * Get page load timeout
     * @return Page load timeout in seconds
     */
    public static int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout", "30"));
    }

    /**
     * Check if browser should run in headless mode
     * @return true if headless, false otherwise
     */
    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }

    /**
     * Check if screenshot should be taken on failure
     * @return true if screenshot on failure enabled
     */
    public static boolean takeScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("take.screenshot.on.failure", "true"));
    }

    /**
     * Get screenshot path
     * @return Screenshot directory path
     */
    public static String getScreenshotPath() {
        return getProperty("screenshot.path", "test-output/screenshots/");
    }

    /**
     * Get extent report path
     * @return Extent report directory path
     */
    public static String getExtentReportPath() {
        return getProperty("extent.report.path", "test-output/reports/");
    }

    /**
     * Get extent report name
     * @return Extent report file name
     */
    public static String getExtentReportName() {
        return getProperty("extent.report.name", "AutomationReport.html");
    }
}