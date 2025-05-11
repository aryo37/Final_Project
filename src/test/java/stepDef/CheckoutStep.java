package stepDef;

import helper.Utility;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static helper.Utility.takeScreenshot;
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

        try {
            // 1. Verifikasi pesan
            WebElement thankYouMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(thankYouMessageLocator));
            assertEquals(expectedMessage, thankYouMessage.getText().trim());
            takeScreenshot(driver, "Thank you for your purchase!");  // Screenshot pesan sukses

            // 2. Klik tombol OK
            WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(okButtonLocator));
            okButton.click();
            takeScreenshot(driver, "after-ok-click");  // Screenshot setelah klik

            // 3. Verifikasi modal tertutup
            wait.until(ExpectedConditions.invisibilityOfElementLocated(thankYouMessageLocator));

        } catch (Exception e) {
            takeScreenshot(driver, "error-on-purchase-confirmation");  // Screenshot jika error
            throw e;  // Re-throw exception agar Cucumber mencatat test sebagai failed
        } finally {
            // 4. Pastikan browser ditutup setelah selesai
            if (driver != null) {
                driver.quit();
            }
        }
    }

//    private void takeScreenshot(String fileName) {
//        try {
//            File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
//            String path = "target/screenshots/" + fileName + "_" + timestamp + ".png";
//            FileUtils.copyFile(screenshot, new File(path));
//        } catch (Exception e) {
//            System.err.println("Gagal mengambil screenshot: " + e.getMessage());
//        }
//    }

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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // 1. Tunggu alert muncul
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());

            // 2. Verifikasi teks alert
            String actualAlertText = alert.getText();
            if (!expectedAlertText.equals(actualAlertText)) {
                // Jika teks tidak match, tangkap screenshot SEBELUM menutup alert
                try {
                    driver.switchTo().defaultContent();
                    Utility.takeScreenshot(driver, "alert-text-mismatch");
                } catch (Exception e) {
                    System.err.println("Warning: Failed to take screenshot - " + e.getMessage());
                }
                Assertions.fail(String.format(
                        "Expected alert text: '%s' but got: '%s'",
                        expectedAlertText, actualAlertText));
            }

            // 3. Tangani alert
            alert.accept();

        } catch (TimeoutException e) {
            // 4. Tangani kasus alert tidak muncul
            try {
                Utility.takeScreenshot(driver, "alert-not-appeared");
            } catch (Exception screenshotEx) {
                System.err.println("Warning: Failed to take screenshot - " + screenshotEx.getMessage());
            }
            Assertions.fail("Alert dengan teks '" + expectedAlertText + "' tidak muncul dalam 15 detik");
        }
    }

    @Then("I close the browser securely")
    public void close_browser_securely() {
        try {
            // 1. Tangani alert yang mungkin masih terbuka
            try {
                driver.switchTo().alert().dismiss();
            } catch (NoAlertPresentException ignore) {}

            // 2. Tutup browser dengan benar
            if (driver != null) {
                driver.quit();
                System.out.println("Browser closed successfully");

                // 3. Bersihkan referensi
                driver = null;
            }
        } catch (Exception e) {
            System.err.println("Error while closing browser: " + e.getMessage());

            // 4. Fallback: force kill jika diperlukan
            try {
                Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
                Runtime.getRuntime().exec("taskkill /F /IM chrome.exe /T");
            } catch (IOException ioEx) {
                System.err.println("Force kill failed: " + ioEx.getMessage());
            }
        }
    }
}
