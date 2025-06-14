package com.automation.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Environment-specific configuration management utility
 */
public class EnvironmentConfig {
    private static final Logger logger = LogManager.getLogger(EnvironmentConfig.class);
    private static final String ENV = System.getProperty("env", "test");

    /**
     * Get environment-specific base URL
     * @return Base URL for the current environment
     */
    public static String getEnvironmentUrl() {
        String url = switch (ENV.toLowerCase()) {
            case "dev", "development" -> ConfigReader.getProperty("dev.url");
            case "staging", "stage" -> ConfigReader.getProperty("staging.url");
            case "prod", "production" -> ConfigReader.getProperty("prod.url");
            default -> ConfigReader.getProperty("base.url");
        };

        logger.info("Using environment: {} with URL: {}", ENV, url);
        return url;
    }

    /**
     * Get current environment name
     * @return Current environment
     */
    public static String getCurrentEnvironment() {
        return ENV;
    }

    /**
     * Check if running in production environment
     * @return true if production environment
     */
    public static boolean isProduction() {
        return ENV.toLowerCase().equals("prod") || ENV.toLowerCase().equals("production");
    }

    /**
     * Check if running in development environment
     * @return true if development environment
     */
    public static boolean isDevelopment() {
        return ENV.toLowerCase().equals("dev") || ENV.toLowerCase().equals("development");
    }

    /**
     * Check if running in staging environment
     * @return true if staging environment
     */
    public static boolean isStaging() {
        return ENV.toLowerCase().equals("staging") || ENV.toLowerCase().equals("stage");
    }

    /**
     * Get environment-specific API base URL
     * @return API base URL for current environment
     */
    public static String getEnvironmentApiUrl() {
        return switch (ENV.toLowerCase()) {
            case "dev", "development" -> ConfigReader.getProperty("dev.api.url", "https://api-dev.example.com");
            case "staging", "stage" -> ConfigReader.getProperty("staging.api.url", "https://api-staging.example.com");
            case "prod", "production" -> ConfigReader.getProperty("prod.api.url", "https://api.example.com");
            default -> ConfigReader.getProperty("api.base.url", "https://api-test.example.com");
        };
    }

    /**
     * Get environment-specific database URL
     * @return Database URL for current environment
     */
    public static String getEnvironmentDatabaseUrl() {
        return switch (ENV.toLowerCase()) {
            case "dev", "development" -> ConfigReader.getProperty("dev.db.url");
            case "staging", "stage" -> ConfigReader.getProperty("staging.db.url");
            case "prod", "production" -> ConfigReader.getProperty("prod.db.url");
            default -> ConfigReader.getProperty("db.url");
        };
    }

    /**
     * Get environment-specific timeout values
     * @return Timeout in seconds based on environment
     */
    public static int getEnvironmentTimeout() {
        return switch (ENV.toLowerCase()) {
            case "dev", "development" -> 60; // Longer timeout for dev (debugging)
            case "staging", "stage" -> 45;
            case "prod", "production" -> 30; // Shorter timeout for production
            default -> ConfigReader.getExplicitWait();
        };
    }

    /**
     * Should take screenshots on failure based on environment
     * @return true if screenshots should be taken
     */
    public static boolean shouldTakeScreenshots() {
        // Don't take screenshots in production to avoid sensitive data
        if (isProduction()) {
            return Boolean.parseBoolean(ConfigReader.getProperty("prod.screenshots.enabled", "false"));
        }
        return ConfigReader.takeScreenshotOnFailure();
    }

    /**
     * Get retry count based on environment
     * @return Number of retries for flaky tests
     */
    public static int getRetryCount() {
        return switch (ENV.toLowerCase()) {
            case "dev", "development" -> 0; // No retries in dev for faster feedback
            case "staging", "stage" -> 2; // Some retries in staging
            case "prod", "production" -> 3; // More retries in production
            default -> 1;
        };
    }
}