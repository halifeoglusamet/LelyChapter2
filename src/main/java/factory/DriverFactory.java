package factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {

    private static final ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

    public static RemoteWebDriver createDriver() {

        var options = new ChromeOptions();
        WebDriverManager.chromedriver().setup();

        driver.set(new ChromeDriver(options));
        driver.get().manage().window().maximize();
        return driver.get();
    }

    public static RemoteWebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        driver.get().quit();
        driver.remove();
    }
}
