package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebPage {

    WebDriver driver;
    WebDriverWait wait;

    By usernameInputText = By.cssSelector("input#sign-username.form-control");
    By passwordInputText = By.xpath("//*[@id=\"sign-password\"]");
    By signUpButton = By.xpath("//*[@id=\"signInModal\"]/div/div/div[3]/button[2]");
    By nameInputText = By.id("name");
    By countryInputText = By.id("country");
    By cityInputText = By.id("city");
    By creditCardInputText = By.id("card");
    By monthInputText = By.id("month");
    By yearInputText = By.id("year");
    By addToCartButton = By.cssSelector("a.btn.btn-success.btn-lg");
    By cartButton = By.id("cartur");
    By placeOrderButton = By.cssSelector("button.btn.btn-success");
    By purchaseButton = By.cssSelector("button.btn.btn-primary");
    By confirmationMessage = By.cssSelector("h2");

    public WebPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void clickElement(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected void sendKeysToElement(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
    }

    public void inputUsername(String username) {
        WebElement usernameField = driver.findElement(usernameInputText);
        sendKeysToElement(usernameField, username);
    }

    public void inputPassword(String password) {
        WebElement passwordField = driver.findElement(passwordInputText);
        sendKeysToElement(passwordField, password);
    }

    public void clickSignUpButton() {
        WebElement signUpButtonField = driver.findElement(signUpButton);
        clickElement(signUpButtonField);
    }

    public void fillShippingInformation(String name, String country, String city, String creditCard, String month, String year) {
        WebElement nameField = driver.findElement(nameInputText);
        sendKeysToElement(nameField, name);

        WebElement countryField = driver.findElement(countryInputText);
        sendKeysToElement(countryField, country);

        WebElement cityField = driver.findElement(cityInputText);
        sendKeysToElement(cityField, city);

        WebElement cardField = driver.findElement(creditCardInputText);
        sendKeysToElement(cardField, creditCard);

        WebElement monthField = driver.findElement(monthInputText);
        sendKeysToElement(monthField, month);

        WebElement yearField = driver.findElement(yearInputText);
        sendKeysToElement(yearField, year);
    }

    public void selectProduct(String productName) {
        By productLocator = By.xpath("//*[@id=\"tbodyid\"]/div[1]/div/div/h4/a" + productName + "')]");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement productElement = wait.until(ExpectedConditions.refreshed(
                ExpectedConditions.elementToBeClickable(productLocator)));
        productElement.click();
    }

    public void addToCart() {
        WebElement addToCartBtn = driver.findElement(addToCartButton);
        clickElement(addToCartBtn);

        // Tangani alert 'Product added' yang muncul setelah klik Add to Cart
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert text: " + alert.getText()); // Opsional, untuk debugging
            alert.accept(); // Klik OK pada alert
        } catch (NoAlertPresentException e) {
            // Jika tidak ada alert, lanjutkan seperti biasa
        } catch (StaleElementReferenceException e) {
            // Jika elemen menjadi stale, coba cari ulang dan klik lagi
            addToCart();
        }

    }

    public void goToCart() {
        WebElement cartBtn = driver.findElement(cartButton);
        clickElement(cartBtn);
    }

    public void placeOrder() {
        WebElement placeOrderBtn = driver.findElement(placeOrderButton);
        clickElement(placeOrderBtn);
    }

    public void clickPurchaseButton() {
        WebElement purchaseBtn = driver.findElement(purchaseButton);
        clickElement(purchaseBtn);
    }

    public String getConfirmationMessageText() {
        WebElement confirmationField = driver.findElement(confirmationMessage);
        return confirmationField.getText();
    }

    public void login(String username, String password) {
        inputUsername(username);
        inputPassword(password);
        clickSignUpButton();

        // Tangani peringatan jika muncul
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert text: " + alert.getText());
            alert.accept(); // Menerima peringatan
        } catch (NoAlertPresentException e) {
            // Tidak ada peringatan yang muncul, lanjutkan dengan pengujian
        }
    }
}
