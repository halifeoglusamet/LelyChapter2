package scenarios.base;

import factory.DriverFactory;
import io.qameta.allure.Step;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

public class BasePage {

    protected static final int DEFAULT_WAIT = 10;
    protected static Logger logger = LogManager.getLogger(BasePage.class.getName());
    protected RemoteWebDriver driver = DriverFactory.getDriver();
    protected WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT);

    /**
     * Use this method click to element
     */
    @Step("Click to element")
    protected void clickElement(By by) {

        logger.log(Level.DEBUG, "Trying to click element : {}", by);
        waitUntilClickableAndGetElement(by).click();
        logger.log(Level.DEBUG, "Element is clicked successfully : {}", by);
    }

    /**
     * Use this method to find elements by By
     *
     * @return A list of MobileElements, or an empty if nothing matches
     */
    @Step("Find elements")
    protected List<WebElement> findElements(By by) {

        logger.log(Level.DEBUG, "Trying to find elements : {}", by);
        return driver.findElements(by);
    }

    /**
     * Use this method to find element by By
     *
     * @return element, or an empty if nothing matches
     */
    @Step("Find element")
    protected WebElement findElement(By by) {

        waitUntilElementAppears(by);
        logger.log(Level.DEBUG, "Trying to find elements : {}", by);
        return driver.findElement(by);
    }

    /**
     * Use this method to simulate typing into an element if it is enable.
     * Send enter if pressEnter is true, do nothing otherwise.
     * Note : Before sending operation, element is cleared.
     */
    @Step("Fill input field element")
    protected void fillInputField(By by, String text, boolean pressEnter) {

        WebElement element = waitUntilClickableAndGetElement(by);

        logger.log(Level.DEBUG, "Trying to fill input field of element : {} with text : {}", by, text);
        clearElementField(by);
        element.sendKeys(text);
        logger.log(Level.DEBUG, "Input field of element is filled successfully : {} with text : {}", by, text);

        if (pressEnter) element.sendKeys(Keys.ENTER);
    }

    /**
     * Use this method to clear element
     */
    @Step("Clear element field")
    protected void clearElementField(By by) {

        WebElement element = waitUntilAppearsAndGetElement(by);
        executeClickScript(by);

        logger.log(Level.DEBUG, "Trying to clear input field of element : {}", by);
        element.clear();
        logger.log(Level.DEBUG, "Input field of element is cleared successfully : {}", by);
    }

    /**
     * Click to the element with Javascript Executor
     */
    @Step("Execute click script")
    protected void executeClickScript(By by) {

        WebElement element = driver.findElement(by);

        logger.log(Level.DEBUG, "Trying to execute click script to element : {}", by);
        driver.executeScript("arguments[0].click();", element);
        logger.log(Level.DEBUG, "Executed successfully click script to element : {}", by);
    }

    /**
     * Wait until element to be clickable
     */
    @Step("Wait until clickable and get element")
    protected WebElement waitUntilClickableAndGetElement(By by) {
        logger.log(Level.DEBUG, "Trying to wait element until clickable : {}", by);
        WebElement element = wait.ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(by));
        logger.log(Level.DEBUG, "Element is clickable : {}", element);
        return element;
    }

    /**
     * Wait until element appears
     */
    @Step("Wait until element appears by time")
    protected void waitUntilElementAppears(By by, int waitTime) {

        logger.log(Level.DEBUG, "Trying to wait element until appears : {} with given time : {}", by, waitTime);
        new WebDriverWait(driver, waitTime).until(ExpectedConditions.visibilityOfElementLocated(by));
        logger.log(Level.DEBUG, "Element is appeared : {} with given time : {}", by, waitTime);
    }

    /**
     * Wait until element appears
     */
    @Step("Wait until element appears")
    protected void waitUntilElementAppears(By by) {

        logger.log(Level.DEBUG, "Trying to wait element until appears : {}", by);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        logger.log(Level.DEBUG, "Element is appeared : {}", by);
    }

    /**
     * Wait until element appears and get element
     */
    @Step("Wait until element appears and get element")
    protected WebElement waitUntilAppearsAndGetElement(By by) {

        logger.log(Level.DEBUG, "Trying to wait element until appears : {}", by);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        logger.log(Level.DEBUG, "Element is appeared : {}", by);
        return element;
    }

    /**
     * @return True if element exists, false otherwise.
     */
    @Step("Is element exist by time")
    protected boolean isElementExist(By by, int waitTime, boolean... checkExistence) {

        if (checkExistence.length == 0 || checkExistence[0]) {

            try {

                waitUntilElementAppears(by, waitTime);
                return findElements(by).size() == 1;
            } catch (TimeoutException e) {

                return false;
            }
        }

        try {

            waitUntilElementDisappears(by, waitTime);
            return true;
        } catch (TimeoutException e) {

            return false;
        }
    }

    /**
     * Wait until element disappears
     *
     * @param by
     * @param waitTime
     */
    @Step("Wait until element disappears by time")
    protected void waitUntilElementDisappears(By by, int waitTime) {

        logger.log(Level.DEBUG, "Trying to wait element until disappears : {} with given time : {}", by, waitTime);
        new WebDriverWait(driver, waitTime).until(ExpectedConditions.invisibilityOfElementLocated(by));
        logger.log(Level.DEBUG, "Element is disappeared successfully : {} with given time : {}", by, waitTime);
    }

    /**
     * Switch tab to given index
     */
    @Step("Switch to tab by index")
    protected void switchTab(int tabIndex) {

        logger.log(Level.DEBUG, "Trying to switch tab with index : {}", tabIndex);

        await("Waiting for new tab to open")
                .pollDelay(0, TimeUnit.SECONDS)
                .pollInterval(1, TimeUnit.SECONDS)
                .atMost(5, TimeUnit.SECONDS)
                .until(() -> driver.getWindowHandles().size() >= tabIndex);

        var tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabIndex));
        logger.log(Level.DEBUG, "Switched to tab successfully with index : {}", tabIndex);
    }

    /**
     * Vertical scroll to the element with Javascript Executor
     */
    @Step("Execute scroll script")
    protected void executeScrollScript(By by) {

        logger.log(Level.DEBUG, "Trying to scroll to element with execute scroll script By : {}", by);
        driver.executeScript("arguments[0].scrollIntoView(true);", waitUntilAppearsAndGetElement(by));
        logger.log(Level.DEBUG, "Scroll to element with execute scroll script successfully By : {}", by);
    }

    /**
     * Wait until element disappears
     */
    @Step("Wait until element disappears")
    protected void waitUntilElementDisappears(By by) {

        logger.log(Level.DEBUG, "Trying to wait element until disappears : {}", by);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        logger.log(Level.DEBUG, "Element is disappeared : {}", by);
    }

}
