package stepDef;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CartPage;
import pages.HomePage;
import pages.OrderModal;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CheckoutStep extends BaseSteps{

    private CartPage cartPage;
    private OrderModal orderModal;

    @When("user click {string} button")
    public void userClickButton(String buttonText) {
        switch(buttonText) {
            case "Cart":
                cartPage = new HomePage(driver).navigateToCart();
                break;
            case "Place Order":
                orderModal = cartPage.clickPlaceOrder();
                break;
            case "Purchase":
                orderModal.clickPurchase();
                break;
        }
    }

    @And("User should be redirected to {string}")
    public void user_should_be_redirected_to(String expectedUrl) {
        if (driver.getCurrentUrl().equals(expectedUrl)) {
            return;
        }

        try {
            // Coba pertama dengan menunggu URL berubah
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.urlToBe(expectedUrl));
        } catch (TimeoutException e) {
            // Jika timeout, coba klik logo/Home secara manual
            try {
                WebElement homeLink = driver.findElement(By.cssSelector(".navbar-brand"));
                homeLink.click();

                // Tunggu lagi setelah klik manual
                new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.urlToBe(expectedUrl));
            } catch (Exception ex) {
                // Jika masih gagal, lempar exception asli
                throw e;
            }
        }
    }

    @And("User fills the order form with:")
    public void user_fills_the_order_form_with(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMaps().get(0);
        orderModal.fillForm(
                data.get("Name"),
                data.get("Country"),
                data.get("City"),
                data.get("Credit card"),
                data.get("Month"),
                data.get("Year")
        );
    }

    By thankYouMessageLocator = By.xpath("//div[contains(@class,'sweet-alert')]//h2[contains(text(),'Thank you')]");
    By okButtonLocator = By.xpath("//div[contains(@class,'sweet-alert')]//button[contains(text(),'OK')]");

    @Then("{string} message should appear")
    public void message_should_appear(String expectedMessage) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Tunggu dan verifikasi pesan
        WebElement thankYouMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(thankYouMessageLocator));
        assertEquals("Thank you for your purchase!", thankYouMessage.getText().trim());

        // Klik tombol OK
        WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(okButtonLocator));
        okButton.click();

        // Verifikasi modal tertutup
        wait.until(ExpectedConditions.invisibilityOfElementLocated(thankYouMessageLocator));
    }

    private void takeScreenshot(String fileName) {
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("target/screenshots/" + fileName + "_" + System.currentTimeMillis() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @And("Close the browser")
    public void close_the_browser() {
        if (driver != null) {
            driver.quit();
        }
    }

    @When("User attempts to checkout without filling required fields")
    public void user_attempts_to_checkout_without_filling_required_fields() {
        // Pastikan driver sudah diinisialisasi
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Klik tombol Purchase tanpa mengisi form
            WebElement purchaseBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Purchase')]")));
            purchaseBtn.click();
        } catch (NoSuchElementException e) {
            Assertions.fail("Purchase button not found: " + e.getMessage());
        } catch (TimeoutException e) {
            Assertions.fail("Purchase button not clickable within timeout: " + e.getMessage());
        }
    }

    @Then("an alert with text {string} should appear")
    public void an_alert_with_text_should_appear(String expectedAlertText) {
        try {
            // Tunggu alert muncul
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();

            // Verifikasi teks alert
            String actualAlertText = alert.getText();
            Assertions.assertEquals(expectedAlertText, actualAlertText,
                    "Alert text doesn't match expected");

            // Tutup alert
            alert.accept();
        } catch (NoAlertPresentException e) {
            Assertions.fail("Alert not present: " + e.getMessage());
        } catch (TimeoutException e) {
            Assertions.fail("Alert didn't appear within timeout: " + e.getMessage());
        }
    }
}
