package stepDef;

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
}
