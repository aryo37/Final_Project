package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OrderModal extends BasePage {

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "country")
    private WebElement countryInput;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "card")
    private WebElement cardInput;

    @FindBy(id = "month")
    private WebElement monthInput;

    @FindBy(id = "year")
    private WebElement yearInput;

    @FindBy(xpath = "//button[contains(text(),'Purchase')]")
    private WebElement purchaseButton;

    public OrderModal(WebDriver driver) {
        super(driver);
    }

    public void fillForm(String name, String country, String city,
                         String card, String month, String year) {
        wait.until(ExpectedConditions.visibilityOf(nameInput));
        nameInput.sendKeys(name);
        countryInput.sendKeys(country);
        cityInput.sendKeys(city);
        cardInput.sendKeys(card);
        monthInput.sendKeys(month);
        yearInput.sendKeys(year);
    }

    public void clickPurchase() {
        clickElement(purchaseButton);
    }
}
