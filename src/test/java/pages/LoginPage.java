package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;

    private By usernameInput = By.id("loginusername");
    private By passwordInput = By.id("loginpassword");
    private By loginButton = By.xpath("//button[contains(text(),'Log in')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void inputUsername(String username) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).sendKeys(username);
    }

    public void inputPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public String getAlertMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String message = alert.getText();
            alert.accept();
            return message;
        } catch (Exception e) {
            return "No alert present";
        }
    }
}
