package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage{

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[contains(text(),'Place Order')]")
    private WebElement placeOrderButton;

    public void verifyPageTitle(String expectedTitle) {
        wait.until(ExpectedConditions.titleIs(expectedTitle));
        Assert.assertEquals("Page title doesn't match",
                expectedTitle,
                driver.getTitle());
    }

    public OrderModal clickPlaceOrder() {
        clickElement(placeOrderButton);
        return new OrderModal(driver);
    }
}
