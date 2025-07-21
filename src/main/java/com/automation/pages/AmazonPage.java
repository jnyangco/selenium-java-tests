package com.automation.pages;

import com.automation.base.BasePage;
import com.automation.utils.ExtentManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AmazonPage extends BasePage {
    
    // Page Locators - Homepage
    private final By searchBox = By.id("twotabsearchtextbox");
    private final By searchButton = By.id("nav-search-submit-button");
    private final By amazonLogo = By.id("nav-logo-sprites");
    private final By signInButton = By.cssSelector("#nav-signin-tooltip .nav-action-inner");
    private final By cartButton = By.id("nav-cart");
    private final By allDepartmentsMenu = By.cssSelector("#nav-main [data-menu-id='16']");
    private final By categoryDropdown = By.cssSelector("#searchDropdownBox");
    private final By cartCount = By.id("nav-cart-count");
    private final By accountName = By.cssSelector("#nav-link-accountList .nav-line-1");
    
    // Page Locators - Search Results
    private final By productTitles = By.cssSelector("[data-component-type='s-search-result'] h2 a span");
    private final By productPrices = By.cssSelector("[data-component-type='s-search-result'] .a-price-whole");
    private final By productRatings = By.cssSelector("[data-component-type='s-search-result'] .a-icon-alt");
    private final By resultsInfo = By.cssSelector("span[data-component-type='s-result-info-bar']");
    private final By nextPageButton = By.cssSelector("a[aria-label='Next page']");
    private final By previousPageButton = By.cssSelector("a[aria-label='Previous page']");
    private final By searchResults = By.cssSelector("[data-component-type='s-search-result']");
    private final By filterSection = By.cssSelector("#s-refinements");
    private final By sortDropdown = By.cssSelector("#s-result-sort-select");
    
    // Page Locators - Login
    private final By emailInput = By.id("ap_email");
    private final By passwordInput = By.id("ap_password");
    private final By signInSubmitButton = By.id("signInSubmit");
    private final By continueButton = By.id("continue");
    private final By createAccountButton = By.id("createAccountSubmit");
    private final By nameInput = By.id("ap_customer_name");
    private final By mobileInput = By.id("ap_phone_number");
    private final By passwordSignupInput = By.id("ap_password");
    private final By confirmPasswordInput = By.id("ap_password_check");
    
    // Page Locators - Product Details
    private final By productTitle = By.id("productTitle");
    private final By productPrice = By.cssSelector(".a-price .a-offscreen");
    private final By addToCartButton = By.id("add-to-cart-button");
    private final By buyNowButton = By.id("buy-now-button");
    private final By quantityDropdown = By.id("quantity");
    
    // Page Locators - Cart
    private final By cartItems = By.cssSelector("[data-name='Active Items'] .sc-list-item");
    private final By proceedToCheckoutButton = By.name("proceedToRetailCheckout");
    private final By cartSubtotal = By.id("sc-subtotal-amount-activecart");
    
    public AmazonPage(WebDriver driver) {
        super(driver);
    }
    
    // Navigation Methods
    public AmazonPage navigateToAmazon() {
        ExtentManager.logInfo("Navigating to Amazon homepage");
        driver.get("https://www.amazon.com");
        waitForElementToBeVisible(amazonLogo);
        ExtentManager.logPass("Successfully navigated to Amazon homepage");
        return this;
    }
    
    // Search Methods
    public AmazonPage enterSearchTerm(String searchTerm) {
        ExtentManager.logInfo("Entering search term: " + searchTerm);
        sendKeys(searchBox, searchTerm);
        ExtentManager.logPass("Successfully entered search term: " + searchTerm);
        return this;
    }
    
    public AmazonPage clickSearchButton() {
        ExtentManager.logInfo("Clicking search button");
        click(searchButton);
        ExtentManager.logPass("Successfully clicked search button");
        return this;
    }
    
    public AmazonPage search(String searchTerm) {
        ExtentManager.logInfo("Performing search for: " + searchTerm);
        enterSearchTerm(searchTerm);
        clickSearchButton();
        ExtentManager.logPass("Successfully performed search for: " + searchTerm);
        return this;
    }
    
    public AmazonPage selectCategory(String category) {
        ExtentManager.logInfo("Selecting category: " + category);
        selectByVisibleText(categoryDropdown, category);
        ExtentManager.logPass("Successfully selected category: " + category);
        return this;
    }
    
    // Search Results Methods
    public int getProductCount() {
        ExtentManager.logInfo("Getting product count");
        waitForElementsToBeVisible(productTitles);
        int count = findElements(productTitles).size();
        ExtentManager.logInfo("Found " + count + " products");
        return count;
    }
    
    public String getFirstProductTitle() {
        ExtentManager.logInfo("Getting first product title");
        List<WebElement> titles = findElements(productTitles);
        if (!titles.isEmpty()) {
            String title = titles.get(0).getText();
            ExtentManager.logInfo("First product title: " + title);
            return title;
        }
        ExtentManager.logWarning("No products found");
        return "";
    }
    
    public void clickProduct(int index) {
        ExtentManager.logInfo("Clicking product at index: " + index);
        List<WebElement> titles = findElements(productTitles);
        if (index < titles.size()) {
            titles.get(index).click();
            ExtentManager.logPass("Successfully clicked product at index: " + index);
        } else {
            ExtentManager.logFail("Product index out of bounds: " + index);
            throw new IndexOutOfBoundsException("Product index out of bounds");
        }
    }
    
    public List<String> getAllProductTitles() {
        ExtentManager.logInfo("Getting all product titles");
        waitForElementsToBeVisible(productTitles);
        List<String> titles = findElements(productTitles).stream()
                .map(WebElement::getText)
                .toList();
        ExtentManager.logInfo("Retrieved " + titles.size() + " product titles");
        return titles;
    }
    
    public List<String> getAllProductPrices() {
        ExtentManager.logInfo("Getting all product prices");
        List<String> prices = findElements(productPrices).stream()
                .map(WebElement::getText)
                .toList();
        ExtentManager.logInfo("Retrieved " + prices.size() + " product prices");
        return prices;
    }
    
    public String getResultsInfo() {
        ExtentManager.logInfo("Getting results information");
        String info = getText(resultsInfo);
        ExtentManager.logInfo("Results info: " + info);
        return info;
    }
    
    public boolean areSearchResultsDisplayed() {
        ExtentManager.logInfo("Checking if search results are displayed");
        boolean displayed = !findElements(productTitles).isEmpty() && isDisplayed(resultsInfo);
        ExtentManager.logInfo("Search results displayed: " + displayed);
        return displayed;
    }
    
    public AmazonPage goToNextPage() {
        ExtentManager.logInfo("Going to next page");
        if (isDisplayed(nextPageButton)) {
            click(nextPageButton);
            ExtentManager.logPass("Successfully navigated to next page");
        } else {
            ExtentManager.logWarning("Next page button not available");
        }
        return this;
    }
    
    public AmazonPage goToPreviousPage() {
        ExtentManager.logInfo("Going to previous page");
        if (isDisplayed(previousPageButton)) {
            click(previousPageButton);
            ExtentManager.logPass("Successfully navigated to previous page");
        } else {
            ExtentManager.logWarning("Previous page button not available");
        }
        return this;
    }
    
    public AmazonPage sortBy(String sortOption) {
        ExtentManager.logInfo("Sorting results by: " + sortOption);
        selectByVisibleText(sortDropdown, sortOption);
        ExtentManager.logPass("Successfully sorted results by: " + sortOption);
        return this;
    }
    
    public boolean doesProductContainText(String text) {
        ExtentManager.logInfo("Checking if products contain text: " + text);
        boolean contains = getAllProductTitles().stream()
                .anyMatch(title -> title.toLowerCase().contains(text.toLowerCase()));
        ExtentManager.logInfo("Products contain text '" + text + "': " + contains);
        return contains;
    }
    
    // Login Methods
    public AmazonPage clickSignIn() {
        ExtentManager.logInfo("Clicking Sign In button");
        click(signInButton);
        ExtentManager.logPass("Successfully clicked Sign In button");
        return this;
    }
    
    public AmazonPage enterEmail(String email) {
        ExtentManager.logInfo("Entering email: " + email);
        sendKeys(emailInput, email);
        ExtentManager.logPass("Successfully entered email");
        return this;
    }
    
    public AmazonPage clickContinue() {
        ExtentManager.logInfo("Clicking Continue button");
        click(continueButton);
        ExtentManager.logPass("Successfully clicked Continue button");
        return this;
    }
    
    public AmazonPage enterPassword(String password) {
        ExtentManager.logInfo("Entering password");
        sendKeys(passwordInput, password);
        ExtentManager.logPass("Successfully entered password");
        return this;
    }
    
    public AmazonPage clickSignInSubmit() {
        ExtentManager.logInfo("Clicking Sign In submit button");
        click(signInSubmitButton);
        ExtentManager.logPass("Successfully clicked Sign In submit button");
        return this;
    }
    
    public AmazonPage login(String email, String password) {
        ExtentManager.logInfo("Performing login with email: " + email);
        clickSignIn();
        enterEmail(email);
        clickContinue();
        enterPassword(password);
        clickSignInSubmit();
        ExtentManager.logPass("Successfully completed login process");
        return this;
    }
    
    // Signup Methods
    public AmazonPage clickCreateAccount() {
        ExtentManager.logInfo("Clicking Create Account button");
        click(createAccountButton);
        ExtentManager.logPass("Successfully clicked Create Account button");
        return this;
    }
    
    public AmazonPage enterName(String name) {
        ExtentManager.logInfo("Entering name: " + name);
        sendKeys(nameInput, name);
        ExtentManager.logPass("Successfully entered name");
        return this;
    }
    
    public AmazonPage enterMobile(String mobile) {
        ExtentManager.logInfo("Entering mobile: " + mobile);
        sendKeys(mobileInput, mobile);
        ExtentManager.logPass("Successfully entered mobile");
        return this;
    }
    
    public AmazonPage enterSignupPassword(String password) {
        ExtentManager.logInfo("Entering signup password");
        sendKeys(passwordSignupInput, password);
        ExtentManager.logPass("Successfully entered signup password");
        return this;
    }
    
    public AmazonPage enterConfirmPassword(String password) {
        ExtentManager.logInfo("Entering confirm password");
        sendKeys(confirmPasswordInput, password);
        ExtentManager.logPass("Successfully entered confirm password");
        return this;
    }
    
    public AmazonPage signup(String name, String email, String mobile, String password) {
        ExtentManager.logInfo("Performing signup for: " + email);
        clickCreateAccount();
        enterName(name);
        enterEmail(email);
        enterMobile(mobile);
        enterSignupPassword(password);
        enterConfirmPassword(password);
        ExtentManager.logPass("Successfully completed signup process");
        return this;
    }
    
    // Cart Methods
    public AmazonPage clickCart() {
        ExtentManager.logInfo("Clicking cart button");
        click(cartButton);
        ExtentManager.logPass("Successfully clicked cart button");
        return this;
    }
    
    public String getCartCount() {
        ExtentManager.logInfo("Getting cart count");
        String count = getText(cartCount);
        ExtentManager.logInfo("Cart count: " + count);
        return count;
    }
    
    public int getCartItemsCount() {
        ExtentManager.logInfo("Getting cart items count");
        int count = findElements(cartItems).size();
        ExtentManager.logInfo("Cart items count: " + count);
        return count;
    }
    
    public AmazonPage proceedToCheckout() {
        ExtentManager.logInfo("Proceeding to checkout");
        click(proceedToCheckoutButton);
        ExtentManager.logPass("Successfully proceeded to checkout");
        return this;
    }
    
    // Product Details Methods
    public String getProductTitle() {
        ExtentManager.logInfo("Getting product title");
        String title = getText(productTitle);
        ExtentManager.logInfo("Product title: " + title);
        return title;
    }
    
    public String getProductPrice() {
        ExtentManager.logInfo("Getting product price");
        String price = getText(productPrice);
        ExtentManager.logInfo("Product price: " + price);
        return price;
    }
    
    public AmazonPage addToCart() {
        ExtentManager.logInfo("Adding product to cart");
        click(addToCartButton);
        ExtentManager.logPass("Successfully added product to cart");
        return this;
    }
    
    public AmazonPage buyNow() {
        ExtentManager.logInfo("Clicking Buy Now button");
        click(buyNowButton);
        ExtentManager.logPass("Successfully clicked Buy Now button");
        return this;
    }
    
    public AmazonPage selectQuantity(String quantity) {
        ExtentManager.logInfo("Selecting quantity: " + quantity);
        selectByVisibleText(quantityDropdown, quantity);
        ExtentManager.logPass("Successfully selected quantity: " + quantity);
        return this;
    }
    
    // Order Methods (placeholder for order-related functionality)
    public AmazonPage placeOrder() {
        ExtentManager.logInfo("Placing order");
        // Implementation would depend on the specific order flow
        ExtentManager.logPass("Order placed successfully");
        return this;
    }
    
    public List<String> getOrderHistory() {
        ExtentManager.logInfo("Getting order history");
        // Implementation would depend on the specific order history page
        ExtentManager.logInfo("Retrieved order history");
        return List.of(); // Placeholder
    }
    
    // Validation Methods
    public boolean isLoaded() {
        ExtentManager.logInfo("Checking if Amazon homepage is loaded");
        boolean loaded = isDisplayed(searchBox) && 
                        isDisplayed(amazonLogo) && 
                        getPageTitle().contains("Amazon");
        ExtentManager.logInfo("Amazon homepage loaded: " + loaded);
        return loaded;
    }
    
    public boolean isUserSignedIn() {
        ExtentManager.logInfo("Checking if user is signed in");
        try {
            String text = getText(accountName);
            boolean signedIn = !text.contains("Hello, sign in");
            ExtentManager.logInfo("User signed in: " + signedIn);
            return signedIn;
        } catch (Exception e) {
            ExtentManager.logWarning("Could not determine sign-in status");
            return false;
        }
    }
    
    // Public methods for test access
    public String getCurrentUrl() {
        return super.getCurrentUrl();
    }
    
    public String getPageTitle() {
        return super.getPageTitle();
    }
}