# Selenium Java Test Automation Framework

A modern, scalable test automation framework using Selenium WebDriver, TestNG, and ExtentReports.

## 🏗️ Framework Structure

### Updated Architecture
- **Single Page Classes**: One comprehensive page class per application (GooglePage, AmazonPage)
- **Multiple Test Classes**: Separate test files for different functionalities (Login, Search, Order, etc.)
- **ExtentReports**: Rich HTML reporting with screenshots and detailed logs
- **Step Logging**: Built-in methods for test step logging (logPass, logInfo, logFail, etc.)

## 📁 Project Structure

```
src/
├── main/java/com/automation/
│   ├── base/
│   │   ├── BasePage.java          # Base page class with common methods
│   │   └── BaseTest.java          # Base test class with setup/teardown
│   ├── pages/
│   │   ├── GooglePage.java        # Single page class for all Google functionality
│   │   └── AmazonPage.java        # Single page class for all Amazon functionality
│   ├── utils/
│   │   └── ExtentManager.java     # ExtentReport management and logging utilities
│   └── listeners/
│       └── TestListener.java     # TestNG listener for ExtentReports integration
└── test/java/com/automation/tests/
    ├── google/
    │   ├── GoogleSearchTest.java  # Google search functionality tests
    │   └── GoogleLoginTest.java   # Google login functionality tests
    └── amazon/
        ├── AmazonLoginTest.java   # Amazon login functionality tests
        └── AmazonOrderTest.java   # Amazon order functionality tests
```

## 🚀 Key Features

### 1. Single Page Classes
Each application has one comprehensive page class containing all locators and methods:
- **GooglePage.java**: Search, login, signup, results navigation
- **AmazonPage.java**: Search, login, signup, cart, orders, product details

### 2. Multiple Test Classes
Tests are organized by functionality:
- **GoogleSearchTest**: Homepage, search, results, pagination
- **GoogleLoginTest**: Sign in, signup, validation
- **AmazonLoginTest**: Login flows, account creation
- **AmazonOrderTest**: Product search, cart, order placement

### 3. ExtentReports Integration
- **Rich HTML Reports**: Beautiful, detailed test reports
- **Screenshots**: Automatic screenshot capture on failures
- **Step Logging**: Detailed test step documentation
- **Timeline View**: Visual test execution timeline

### 4. Built-in Logging Methods
Available in all test classes:
```java
logPass("Step completed successfully");
logFail("Step failed due to...");
logInfo("Informational message");
logWarning("Warning message");
logSkip("Step was skipped");
```

## 🛠️ Dependencies

### Core Testing
- **Selenium WebDriver**: 4.26.0
- **TestNG**: 7.10.2
- **WebDriverManager**: 5.9.2

### Reporting
- **ExtentReports**: 5.1.2

### Utilities
- **SLF4J + Logback**: Logging
- **Jackson**: JSON processing
- **Apache Commons IO**: File utilities

## 📊 Reports

### ExtentReports Features
- **Dashboard Overview**: Test suite summary with pass/fail statistics
- **Detailed Test Steps**: Each test shows detailed steps with timestamps
- **Screenshots**: Automatic capture on test failures
- **Filtering**: Filter tests by status (Pass/Fail/Skip)
- **Timeline View**: Visual representation of test execution
- **System Information**: Browser, OS, Java version details

### Report Location
Reports are generated in: `reports/extent-reports/Test-Report-{timestamp}.html`

## 🔧 Configuration

### Browser Configuration
Set browser via system property or TestNG parameter:
```bash
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge
```

### Headless Mode
```bash
mvn test -Dheadless=true
```

### Parallel Execution
Configure in `testng.xml`:
```xml
<suite name="Test Suite" parallel="methods" thread-count="3">
```

## 📝 Writing Tests

### Example Test Structure
```java
@Test(description = "Verify login functionality")
public void testLogin() {
    logInfo("Starting login test");
    
    GooglePage googlePage = new GooglePage(driver);
    
    logInfo("Step 1: Navigate to homepage");
    googlePage.navigateToGoogle();
    
    logInfo("Step 2: Perform login");
    googlePage.login("user@example.com", "password");
    
    logPass("Login test completed successfully");
}
```

### Page Object Usage
```java
// Single page class with all functionality
GooglePage googlePage = new GooglePage(driver);

// Search functionality
googlePage.search("selenium webdriver");
googlePage.getSearchResultsCount();
googlePage.clickSearchResult(0);

// Login functionality
googlePage.clickSignIn();
googlePage.login("email", "password");

// Results navigation
googlePage.goToNextPage();
googlePage.areSearchResultsDisplayed();
```

## 🚀 Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Suite
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

### Run Specific Test Class
```bash
mvn test -Dtest=GoogleSearchTest
```

### Run with Custom Browser
```bash
mvn test -Dbrowser=firefox -Dheadless=false
```

## 📈 Benefits of New Structure

### 1. Simplified Maintenance
- **Single Page Classes**: All locators and methods for an app in one place
- **Reduced Duplication**: Common functionality shared across test types
- **Easier Updates**: Changes to UI require updates in only one location

### 2. Better Test Organization
- **Functional Separation**: Tests grouped by functionality (login, search, order)
- **Clear Naming**: Easy to understand what each test class covers
- **Scalable Structure**: Easy to add new test types for existing apps

### 3. Enhanced Reporting
- **Visual Reports**: ExtentReports provides rich, visual test reports
- **Step-by-Step Logging**: Detailed test execution flow
- **Failure Analysis**: Screenshots and detailed error information
- **Historical Tracking**: Compare test runs over time

### 4. Improved Debugging
- **Detailed Logs**: Comprehensive logging at each step
- **Screenshot Capture**: Visual evidence of failures
- **Error Context**: Clear error messages with context
- **Timeline View**: See exactly when tests failed

## 🔄 Migration from Allure

The framework has been successfully migrated from Allure to ExtentReports:
- ✅ **Removed**: All Allure dependencies and annotations
- ✅ **Added**: ExtentReports with comprehensive logging
- ✅ **Enhanced**: Better step logging with logPass, logInfo, logFail methods
- ✅ **Improved**: Screenshot integration and failure handling
- ✅ **Maintained**: All existing test functionality

## 🎯 Best Practices

1. **Use Descriptive Test Names**: Clear, descriptive test method names
2. **Log Each Step**: Use logInfo, logPass, logFail for detailed reporting
3. **Single Responsibility**: Each test should test one specific functionality
4. **Page Object Methods**: Keep page methods focused and reusable
5. **Error Handling**: Proper exception handling with meaningful messages
6. **Data Separation**: Use external data files for test data when needed

This updated framework provides a robust, maintainable, and scalable foundation for UI test automation with comprehensive reporting and logging capabilities.