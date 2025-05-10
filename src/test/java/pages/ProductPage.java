package pages;

import helper.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage extends BasePage {
    private WebDriver driver;
    private WebDriverWait wait;
//    private final By ADD_TO_CART_BUTTON = By.xpath("//a[contains(.,'Add to cart')]");

    @FindBy(css = "a[onclick*='addToCart']")
    private WebElement addToCartButton;

    @FindBy(id = "cartur")
    private WebElement cartButton;

    public ProductPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void selectProduct(String productName) {
        By productLocator = By.xpath("//a[contains(.,'" + productName + "') and @class='hrefch']");
        wait.until(ExpectedConditions.elementToBeClickable(productLocator)).click();

        wait.until(ExpectedConditions.urlContains("prod.html"));
    }

//    public void addToCart() {
//        driver.get("https://www.demoblaze.com/prod.html?idp_=3");
//
//        Utility.waitAndClickAddToCart(driver, ADD_TO_CART_BUTTON);
//    }
//        By addToCartLocator = By.xpath("//a[contains(.,'Add to cart') and contains(@onclick,'addToCart')]");
//        wait.until(ExpectedConditions.elementToBeClickable(addToCartLocator)).click();
//    }

    public boolean isOnTheProductPage() {
        return driver.getCurrentUrl().contains("demoblaze");
    }

    public void validateUrlAndAddToCart(String expectedUrlPart) {
        // Tunggu sampai URL mengandung pola yang diharapkan
        wait.until(ExpectedConditions.urlContains(expectedUrlPart));

        // Klik tombol Add to Cart
        clickElement(addToCartButton);

        // Tunggu alert muncul dan terima
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public CartPage navigateToCart() {
        // Klik tombol Cart
        clickElement(cartButton);
        return new CartPage(driver);
    }

    public void addToCart() {
        clickElement(addToCartButton);

        // Handle alert
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }
}
