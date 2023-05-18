package scenarios.base;

import factory.DriverFactory;
import org.openqa.selenium.remote.RemoteWebDriver;
import util.Configuration;

import java.io.File;

public class TestHelper {

    protected static final ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
    protected static final Configuration config = Configuration.getInstance();
    protected static final ThreadLocal<String> downloadedFilesDirPath = new ThreadLocal<>();
    protected static final String RESOURCES_DIR = System.getProperty("user.home");

    protected static void initDriver() {

        driver.set(DriverFactory.createDriver());
        driver.get().get(config.getBaseUrl());
    }

    protected static void initFileDirectory() {
        downloadedFilesDirPath.set(RESOURCES_DIR + File.separator + "Downloads");
    }
}
