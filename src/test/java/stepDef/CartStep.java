package stepDef;

import helper.Utility;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en_scouse.An;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;

import java.time.Duration;

import static java.sql.DriverManager.getDriver;
import static org.junit.Assert.assertTrue;

public class CartStep extends BaseSteps{
    private final By CART_BUTTON = By.id("cartur");

    private ProductPage productPage;
    private HomePage homePage;
    private CartPage cartPage;

    @When("I validate product URL contains {string} and add to cart")
    public void validateUrlAndAddToCart(String urlPart) {
        productPage = new ProductPage(driver);
        productPage.validateUrlAndAddToCart(urlPart);
    }

    @And("user click Cart button")
    public void userClickCartButton() {
        WebDriverWait wait = Utility.getWait(BaseSteps.driver);
        homePage = new HomePage(driver);
        cartPage = productPage.navigateToCart();
    }

    @Then("user should be redirected to Cart page with title {string}")
    public void userShouldBeRedirectedToCartPageWithTitle(String expectedTitle) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.titleContains(expectedTitle));

        Assert.assertTrue("Actual title: " + driver.getTitle(),
                driver.getTitle().contains(expectedTitle));
    }
}
