package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WebPage {

    WebDriver driver;

    By usernameInputText = By.cssSelector("input#sign-username.form-control");
    By passwordInputText = By.xpath("//*[@id=\"sign-password\"]");
    By signUpButton = By.xpath("//*[@id=\"signInModal\"]/div/div/div[3]/button[2]");

    public WebPage(WebDriver driver) { this.driver = driver; }

    public void inputUsername(String username) {
        driver.findElement(usernameInputText).sendKeys(username);
    }

    public void inputPassword(String password) {
        driver.findElement(passwordInputText).sendKeys(password);
    }

    public void clickSignUpButton() {
        driver.findElement(signUpButton).click();
    }
}
