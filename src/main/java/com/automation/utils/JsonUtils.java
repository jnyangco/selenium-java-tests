package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utility class for handling JSON test data
 */
public class JsonUtils {
    private static final Logger logger = LogManager.getLogger(JsonUtils.class);
    private static JSONObject testData;
    private static final String TEST_DATA_PATH = "src/test/resources/testdata/testdata.json";

    static {
        loadTestData();
    }

    /**
     * Load test data from JSON file
     */
    private static void loadTestData() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(TEST_DATA_PATH)));
            testData = new JSONObject(content);
            logger.info("Test data loaded successfully from: " + TEST_DATA_PATH);
        } catch (IOException e) {
            logger.error("Failed to load test data: " + e.getMessage());
            throw new RuntimeException("Test data file not found at: " + TEST_DATA_PATH);
        }
    }

    /**
     * Get test data as JSONObject
     * @return JSONObject containing all test data
     */
    public static JSONObject getTestData() {
        return testData;
    }

    /**
     * Get user data by user type
     * @param userType Type of user (validUser, adminUser, invalidUser)
     * @return JSONObject containing user data
     */
    public static JSONObject getUserData(String userType) {
        try {
            return testData.getJSONObject("users").getJSONObject(userType);
        } catch (Exception e) {
            logger.error("User data not found for type: " + userType);
            throw new RuntimeException("User data not found: " + userType);
        }
    }

    /**
     * Get username for specific user type
     * @param userType Type of user
     * @return Username string
     */
    public static String getUsername(String userType) {
        return getUserData(userType).getString("username");
    }

    /**
     * Get password for specific user type
     * @param userType Type of user
     * @return Password string
     */
    public static String getPassword(String userType) {
        return getUserData(userType).getString("password");
    }

    /**
     * Get login page data
     * @return JSONObject containing login page data
     */
    public static JSONObject getLoginPageData() {
        try {
            return testData.getJSONObject("testData").getJSONObject("loginPage");
        } catch (Exception e) {
            logger.error("Login page data not found");
            throw new RuntimeException("Login page data not found");
        }
    }

    /**
     * Get validation messages
     * @return JSONObject containing validation messages
     */
    public static JSONObject getValidationMessages() {
        try {
            return testData.getJSONObject("testData").getJSONObject("validationMessages");
        } catch (Exception e) {
            logger.error("Validation messages not found");
            throw new RuntimeException("Validation messages not found");
        }
    }

    /**
     * Get specific validation message
     * @param messageKey Key for the validation message
     * @return Validation message string
     */
    public static String getValidationMessage(String messageKey) {
        return getValidationMessages().getString(messageKey);
    }

    /**
     * Get URL data
     * @return JSONObject containing URL data
     */
    public static JSONObject getUrlData() {
        try {
            return testData.getJSONObject("testData").getJSONObject("urls");
        } catch (Exception e) {
            logger.error("URL data not found");
            throw new RuntimeException("URL data not found");
        }
    }

    /**
     * Get specific URL
     * @param urlKey Key for the URL
     * @return URL string
     */
    public static String getUrl(String urlKey) {
        return getUrlData().getString(urlKey);
    }

    /**
     * Get search data
     * @return JSONObject containing search data
     */
    public static JSONObject getSearchData() {
        try {
            return testData.getJSONObject("searchData");
        } catch (Exception e) {
            logger.error("Search data not found");
            throw new RuntimeException("Search data not found");
        }
    }

    /**
     * Get valid search terms
     * @return JSONArray containing valid search terms
     */
    public static JSONArray getValidSearchTerms() {
        return getSearchData().getJSONArray("validSearchTerms");
    }

    /**
     * Get invalid search terms
     * @return JSONArray containing invalid search terms
     */
    public static JSONArray getInvalidSearchTerms() {
        return getSearchData().getJSONArray("invalidSearchTerms");
    }

    /**
     * Get form data by form type
     * @param formType Type of form (contactForm, registrationForm)
     * @return JSONObject containing form data
     */
    public static JSONObject getFormData(String formType) {
        try {
            return testData.getJSONObject("formData").getJSONObject(formType);
        } catch (Exception e) {
            logger.error("Form data not found for type: " + formType);
            throw new RuntimeException("Form data not found: " + formType);
        }
    }

    /**
     * Get contact form data
     * @return JSONObject containing contact form data
     */
    public static JSONObject getContactFormData() {
        return getFormData("contactForm");
    }

    /**
     * Get registration form data
     * @return JSONObject containing registration form data
     */
    public static JSONObject getRegistrationFormData() {
        return getFormData("registrationForm");
    }

    /**
     * Get API endpoints data
     * @return JSONObject containing API endpoints
     */
    public static JSONObject getApiEndpoints() {
        try {
            return testData.getJSONObject("apiEndpoints");
        } catch (Exception e) {
            logger.error("API endpoints data not found");
            throw new RuntimeException("API endpoints data not found");
        }
    }

    /**
     * Get base API URL
     * @return Base API URL string
     */
    public static String getApiBaseUrl() {
        return getApiEndpoints().getString("baseUrl");
    }

    /**
     * Get specific API endpoint
     * @param endpointKey Key for the endpoint
     * @return Endpoint URL string
     */
    public static String getApiEndpoint(String endpointKey) {
        return getApiEndpoints().getJSONObject("endpoints").getString(endpointKey);
    }

    /**
     * Get browser settings
     * @return JSONObject containing browser settings
     */
    public static JSONObject getBrowserSettings() {
        try {
            return testData.getJSONObject("browserSettings");
        } catch (Exception e) {
            logger.error("Browser settings not found");
            throw new RuntimeException("Browser settings not found");
        }
    }

    /**
     * Get default timeout from browser settings
     * @return Default timeout in seconds
     */
    public static int getDefaultTimeout() {
        return getBrowserSettings().getInt("defaultTimeout");
    }

    /**
     * Get environment data by environment name
     * @param environment Environment name (development, staging, production)
     * @return JSONObject containing environment data
     */
    public static JSONObject getEnvironmentData(String environment) {
        try {
            return testData.getJSONObject("testEnvironments").getJSONObject(environment);
        } catch (Exception e) {
            logger.error("Environment data not found for: " + environment);
            throw new RuntimeException("Environment data not found: " + environment);
        }
    }

    /**
     * Get base URL for specific environment
     * @param environment Environment name
     * @return Base URL string
     */
    public static String getEnvironmentBaseUrl(String environment) {
        return getEnvironmentData(environment).getString("baseUrl");
    }

    /**
     * Get API URL for specific environment
     * @param environment Environment name
     * @return API URL string
     */
    public static String getEnvironmentApiUrl(String environment) {
        return getEnvironmentData(environment).getString("apiUrl");
    }

    /**
     * Get screen resolutions for responsive testing
     * @return JSONArray containing screen resolutions
     */
    public static JSONArray getScreenResolutions() {
        return getBrowserSettings().getJSONArray("resolutions");
    }

    /**
     * Get specific resolution by name
     * @param resolutionName Name of the resolution
     * @return JSONObject containing width, height, and name
     */
    public static JSONObject getResolution(String resolutionName) {
        JSONArray resolutions = getScreenResolutions();
        for (int i = 0; i < resolutions.length(); i++) {
            JSONObject resolution = resolutions.getJSONObject(i);
            if (resolution.getString("name").equalsIgnoreCase(resolutionName)) {
                return resolution;
            }
        }
        throw new RuntimeException("Resolution not found: " + resolutionName);
    }
}