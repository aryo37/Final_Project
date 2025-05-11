package helper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {
    public static WebDriver createDriver() {
        // Set path ke ChromeDriver jika diperlukan
        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver"); // Ganti dengan path yang sesuai
        ChromeOptions options = new ChromeOptions();
        // Menentukan direktori data pengguna yang unik
        options.addArguments("--user-data-dir=/tmp/unique-dir-" + System.currentTimeMillis());
        return new ChromeDriver(options);
    }
}
