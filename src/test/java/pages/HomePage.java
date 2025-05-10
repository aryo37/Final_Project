package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
    @FindBy(linkText = "Nexus 6")
    private WebElement nexus6Product;
    private WebDriver driver;
    private By logoutLink = By.id("logout2");
    private By welcomeMessage = By.id("nameofuser");
    private By loginLink = By.id("login2");
    @FindBy(id = "cartur")
    private WebElement cartLink;

    private WebElement cartButton;

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLogoutDisplayed() {
        try {
            return driver.findElement(logoutLink).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isWelcomeDisplayed() {
        try {
            return driver.findElement(welcomeMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginDisplayed() {
        try {
            return driver.findElement(loginLink).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void selectProduct(String productName) {
        if(productName.equals("Nexus 6")) {
            nexus6Product.click();
        }
    }

    public CartPage navigateToCart() {
        clickElement(cartLink);
        return new CartPage(driver);
    }
}
