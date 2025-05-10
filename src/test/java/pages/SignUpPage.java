package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignUpPage {

    WebDriver driver;

    private By usernameInput = By.id("sign-username");
    private By passwordInput = By.id("sign-password");
    private By signUpButton = By.xpath("//*[@id=\"signInModal\"]/div/div/div[3]/button[2]");


    public SignUpPage(WebDriver driver) {
        this.driver = driver;
    }

    public void inputUsername(String username) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).sendKeys(username);
    }

    public void inputPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickSignUpButton() {
        driver.findElement(signUpButton).click();
    }
}
