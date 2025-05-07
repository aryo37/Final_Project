package stepDef;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.WebPage;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WebStep {

    WebDriver driver;
    WebPage webPage;

    @Before
    public void beforeTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        webPage = new WebPage(driver);
    }

    @After
    public void afterTest() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("user is on sign up page")
    public void userIsOnSignUpPage() {
        driver.get("https://www.demoblaze.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement signUpLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"signin2\"]")));
        signUpLink.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("signInModal")));
    }

    @When("user input username with {string}")
    public void userInputUsernameWith(String username) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By usernameInputText = By.cssSelector("input#sign-username.form-control");
        driver.findElement(usernameInputText).sendKeys(username);
    }

    @And("user input password with {string}")
    public void userInputPasswordWith(String password) {
        By passwordInputText = By.xpath("//*[@id=\"sign-password\"]");
        driver.findElement(passwordInputText).sendKeys(password);
    }

    @And("user click sign up button")
    public void userClickSignUpButton() {
        By signUpButton = By.xpath("//*[@id=\"signInModal\"]/div/div/div[3]/button[2]");
        driver.findElement(signUpButton).click();
    }

    @Then("user will redirect to homepage")
    public void userWillRedirectToHomepage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            WebElement categoriesElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'CATEGORIES')]")));
            String actualText = categoriesElement.getText();
            Assert.assertEquals("Expected to be redirected to homepage", "CATEGORIES", actualText);
        } catch (TimeoutException e) {
            Assert.fail("Failed to find CATEGORIES element after redirect");
        }
    }

    @Given("user is on login page")
    public void userIsOnLoginPage() {
    driver.get("https://www.demoblaze.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"login2\"]")));
        loginLink.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("logInModal")));
    }

    @When("user input to username with {string}")
    public void userInputToUsernameWith(String username) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By usernameInputText = By.cssSelector("input#loginusername.form-control");
        driver.findElement(usernameInputText).sendKeys(username);
    }

    @And("user input to password with {string}")
    public void userInputToPasswordWith(String password) {
        By passwordInputText = By.xpath("//*[@id=\"loginpassword\"]");
        driver.findElement(passwordInputText).sendKeys(password);
    }

    @And("user click login button")
    public void userClickLoginButton() {
        By loginButton = By.xpath("//*[@id=\"logInModal\"]/div/div/div[3]/button[2]");
        driver.findElement(loginButton).click();
    }

    @Then("A message appears {string}")
    public void aMessageAppears(String expectedMessage) {
        try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        String actualMessage = alert.getText();

        Assert.assertTrue("Expected alert message: '" + expectedMessage + "' but found: '" + actualMessage + "'",
                actualMessage.contains(expectedMessage));

        alert.accept();
    } catch (TimeoutException e) {
            Assert.fail("Alert with message '" + expectedMessage + "' not found within timeout period");
        }
    }

    @When("user choose product {string}")
    public void user_choose_product(String productName) {
        webPage.selectProduct(productName);
    }

    @When("user click {string} button")
    public void user_click_button(String buttonName) {
        // Handle multiple buttons by name
        switch (buttonName.toLowerCase()) {
            case "add to cart":
                webPage.addToCart();
                // Tangani alert 'Product added'
                try {
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    wait.until(ExpectedConditions.alertIsPresent());
                    Alert alert = driver.switchTo().alert();
                    System.out.println("Alert text: " + alert.getText());
                    alert.accept();
                } catch (NoAlertPresentException e) {
                    // Tidak ada alert, lanjutkan
                }
                break;
            case "cart":
                webPage.goToCart();
                break;
            case "place order":
                webPage.placeOrder();
                break;
            case "purchase":
                webPage.clickPurchaseButton();
                // Tangani konfirmasi pembelian
                handlePurchaseConfirmation();
                break;
            default:
                throw new IllegalArgumentException("Button name not recognized: " + buttonName);
        }
    }

    private void handlePurchaseConfirmation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            // Tunggu hingga pesan konfirmasi muncul
            WebElement confirmationMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), 'Thank you for your purchase!')]")));

            // Ambil teks pesan konfirmasi
            String actualMessage = confirmationMessageElement.getText();
            System.out.println("Confirmation message: " + actualMessage);

            // Klik tombol OK pada modal konfirmasi
            WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.confirm.btn.btn-lg.btn-primary")));
            okButton.click();
        } catch (TimeoutException e) {
            Assert.fail("Confirmation message did not appear within the timeout period.");
        }
    }

    @When("user fill shipping information with:")
    public void user_fill_shipping_information_with(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String name = row.getOrDefault("Name", "");
            String country = row.getOrDefault("Country", "");
            String city = row.getOrDefault("City", "");
            String creditCard = row.getOrDefault("Credit Card", "");
            String month = row.getOrDefault("Month", "");
            String year = row.getOrDefault("Year", "");
            webPage.fillShippingInformation(name, country, city, creditCard, month, year);
        }
    }

    @Then("user see confirmation message {string}")
    public void user_see_confirmation_message(String expectedMessage) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            // Tunggu hingga pesan konfirmasi muncul
            WebElement confirmationField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), '" + expectedMessage + "')]")));

            String actualMessage = confirmationField.getText();
            Assert.assertEquals(expectedMessage, actualMessage);

            // Klik tombol OK setelah memverifikasi pesan
            WebElement okButton = driver.findElement(By.cssSelector("button.confirm.btn.btn-lg.btn-primary"));
            okButton.click();
        } catch (TimeoutException e) {
            Assert.fail("Confirmation message '" + expectedMessage + "' did not appear within the timeout period.");
        }
    }
}
