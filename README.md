# Selenium Java Test Automation Framework

A comprehensive Selenium WebDriver test automation framework built with Java, TestNG, ExtentReports, and the Page Object Model pattern.

## 🚀 Features

- **Page Object Model (POM)** design pattern
- **TestNG** for test execution and management
- **ExtentReports** for detailed HTML reporting
- **Log4j2** for comprehensive logging
- **WebDriverManager** for automatic driver management
- **Parallel execution** support
- **Cross-browser testing** (Chrome, Firefox, Edge, Safari)
- **Screenshot capture** on test failures
- **Configuration management** via properties files
- **JSON-based test data management**
- **Custom utilities** for common operations

## 📁 Project Structure

```
selenium-java-tests/
├── src/
│   ├── main/java/
│   └── test/
│       ├── java/
│       │   └── com/automation/
│       │       ├── listeners/
│       │       │   └── TestListener.java
│       │       ├── pages/
│       │       │   ├── BasePage.java
│       │       │   └── SamplePage.java
│       │       ├── tests/
│       │       │   ├── BaseTest.java
│       │       │   └── SampleTest.java
│       │       └── utils/
│       │           ├── ConfigReader.java
│       │           ├── DriverManager.java
│       │           ├── ExtentManager.java
│       │           ├── JsonUtils.java
│       │           ├── ScreenshotUtils.java
│       │           └── WaitUtils.java
│       └── resources/
│           ├── config.properties
│           ├── log4j2.xml
│           ├── testng.xml
│           └── testdata/
│               └── testdata.json
├── test-output/
│   ├── reports/
│   └── screenshots/
├── logs/
├── pom.xml
└── README.md
```

## 🛠️ Technologies Used

- **Java 11+**
- **Selenium WebDriver 4.15.0**
- **TestNG 7.8.0**
- **ExtentReports 5.1.1**
- **WebDriverManager 5.6.2**
- **Log4j2 2.21.1**
- **Maven** for dependency management

## ⚙️ Setup Instructions

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher
- Chrome/Firefox/Edge browser installed

### Installation
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd selenium-java-tests
   ```

2. Install dependencies:
   ```bash
   mvn clean install
   ```

3. Update configuration:
    - Edit `src/test/resources/config.properties` for your test environment
    - Update `src/test/resources/testdata/testdata.json` with your test data

## 🏃‍♂️ Running Tests

### Command Line Execution

#### Run all tests:
```bash
mvn clean test
```

#### Run specific test suite:
```bash
mvn clean test -Dsuite=src/test/resources/testng.xml
```

#### Run with specific browser:
```bash
mvn clean test -Dbrowser=chrome
```

#### Run specific test class:
```bash
mvn clean test -Dtest=SampleTest
```

#### Run specific test method:
```bash
mvn clean test -Dtest=SampleTest#testLoginPageElementsDisplayed
```

### TestNG Suite Execution

The framework includes pre-configured test suites in `testng.xml`:

- **SmokeTests**: Critical path tests
- **RegressionTests**: Full regression suite
- **FunctionalTests**: Feature-specific tests
- **UITests**: User interface tests

#### Run specific suite:
```bash
mvn clean test -Dgroups=smoke
mvn clean test -Dgroups=regression
mvn clean test -Dgroups=functional
mvn clean test -Dgroups=ui
```

## 📊 Reports and Logs

### ExtentReports
- **Location**: `test-output/reports/`
- **Format**: HTML with screenshots
- **Features**: Test details, execution time, pass/fail status, screenshots

### Logs
- **Location**: `logs/`
- **Format**: Rolling file appender
- **Levels**: DEBUG, INFO, WARN, ERROR

### Screenshots
- **Location**: `test-output/screenshots/`
- **When**: Automatically captured on test failures
- **Format**: PNG with timestamp

## 🔧 Configuration

### Browser Configuration (`config.properties`)
```properties
# Browser settings
browser=chrome
headless=false
implicit.wait=10
explicit.wait=20
page.load.timeout=30

# Application URLs
base.url=https://example.com
staging.url=https://staging.example.com

# Report settings
take.screenshot.on.failure=true
screenshot.path=test-output/screenshots/
extent.report.path=test-output/reports/
```

### Test Data (`testdata.json`)
```json
{
  "users": {
    "validUser": {
      "username": "user@example.com",
      "password": "password123"
    }
  },
  "testData": {
    "loginPage": {
      "pageTitle": "Login Page",
      "headerText": "Welcome"
    }
  }
}
```

## 📝 Writing Tests

### Creating a Page Object
```java
public class LoginPage extends BasePage {
    @FindBy(id = "username")
    private WebElement usernameField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    public LoginPage enterCredentials(String username, String password) {
        typeText(usernameField, username);
        typeText(passwordField, password);
        return this;
    }
}
```

### Creating a Test Class
```java
public class LoginTest extends BaseTest {
    
    @Test(description = "Verify successful login")
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        
        String username = JsonUtils.getUsername("validUser");
        String password = JsonUtils.getPassword("validUser");
        
        loginPage.enterCredentials(username, password);
        
        Assert.assertTrue(loginPage.isLoginSuccessful());
        logPass("Login successful");
    }
}
```

## 🔄 Parallel Execution

### TestNG Configuration
```xml
<suite name="ParallelSuite" parallel="methods" thread-count="3">
    <test name="ParallelTests">
        <classes>
            <class name="com.automation.tests.SampleTest"/>
        </classes>
    </test>
</suite>
```

### Maven Configuration
```bash
mvn clean test -Dparallel=methods -DthreadCount=3
```

## 🌐 Cross-Browser Testing

### Supported Browsers
- Chrome (default)
- Firefox
- Edge
- Safari (macOS only)

### Browser Selection
```bash
# Via command line
mvn clean test -Dbrowser=firefox

# Via TestNG parameter
<parameter name="browser" value="edge"/>

# Via config.properties
browser=chrome
```

## 🎯 Best Practices

### Page Objects
- Use `@FindBy` annotations for element location
- Implement method chaining for fluent interfaces
- Add proper logging for debugging
- Use meaningful element names

### Test Classes
- Extend `BaseTest` for common functionality
- Use descriptive test method names
- Add proper test descriptions
- Implement proper assertions
- Use test data from JSON files

### Utilities
- Use `WaitUtils` for explicit waits
- Use `ScreenshotUtils` for capturing screenshots
- Use `ConfigReader` for configuration management
- Use `JsonUtils` for test data management

## 🐛 Troubleshooting

### Common Issues

#### WebDriver Issues
```bash
# Clear WebDriverManager cache
mvn clean test -Dwdm.clearCache=true
```

#### Memory Issues
```bash
# Increase heap size
export MAVEN_OPTS="-Xmx2048m -XX:MaxPermSize=512m"
```

#### Browser Compatibility
- Update browser to latest version
- Update Selenium WebDriver version
- Check WebDriverManager compatibility

### Debug Mode
```bash
# Enable debug logging
mvn clean test -Dlog4j.configurationFile=log4j2-debug.xml
```

## 📚 Documentation

### Key Classes

#### BasePage
- Common page operations
- Element interaction methods
- Wait utilities integration
- Logging capabilities

#### BaseTest
- WebDriver lifecycle management
- ExtentReports integration
- Screenshot capture
- Test result handling

#### ConfigReader
- Properties file management
- Environment configuration
- Browser settings

#### DriverManager
- WebDriver initialization
- Browser configuration
- ThreadLocal driver management

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For questions and support:
- Create an issue in the repository
- Check the documentation
- Review existing test examples

## 🔄 Continuous Integration

### Jenkins Pipeline Example
```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Reports') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'test-output/reports',
                    reportFiles: '*.html',
                    reportName: 'Automation Report'
                ])
            }
        }
    }
}
```

### GitHub Actions Example
```yaml
name: Test Automation
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 11
      uses: actions/setup-java@v2
      with:
        java-version: '11'
    - name: Run tests
      run: mvn clean test
    - name: Upload reports
      uses: actions/upload-artifact@v2
      with:
        name: test-reports
        path: test-output/
```# selenium-java-tests
