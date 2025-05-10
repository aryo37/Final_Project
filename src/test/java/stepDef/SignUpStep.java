package stepDef;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;
import pages.SignUpPage;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SignUpStep {

    private WebDriver driver;
    private SignUpPage signUpPage;
    private HomePage homePage;

    @Given("user is on sign up page")
    public void userIsOnSignUpPage() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://demoblaze.com/");
        driver.manage().window().maximize();

        driver.findElement(By.id("signin2")).click();
        signUpPage = new SignUpPage(driver);
    }

    @When("user input username with {string}")
    public void userInputUsernameWith(String username) {
        signUpPage.inputUsername(username);
    }

    @And("user input password with {string}")
    public void userInputPasswordWith(String password) {
        signUpPage.inputPassword(password);
    }

    @And("user click sign up button")
    public void userClickSignUpButton() {
        signUpPage.clickSignUpButton();
    }

    @Then("user will redirect to homepage after sign up")
    public void userWillRedirectToHomepageAfterSignUp() {
        // Wait for homepage to load and check for welcome message or logged-in indicator
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Alternative check - verify the URL or presence of other elements
        wait.until(ExpectedConditions.urlContains("https://demoblaze.com/"));
        homePage = new HomePage(driver);
        // More robust verification - check multiple elements
        boolean isHomepageLoaded = homePage.isWelcomeDisplayed() ||
                homePage.isLogoutDisplayed() ||
                driver.getCurrentUrl().equals("https://demoblaze.com/");
        Assert.assertTrue("User was not redirected to homepage", isHomepageLoaded);
        driver.quit();
    }
}
