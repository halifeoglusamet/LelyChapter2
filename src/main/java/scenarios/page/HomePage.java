package scenarios.page;

import org.openqa.selenium.By;
import scenarios.base.BasePage;

public class HomePage extends BasePage {

    private static final By ACCEPT_COOKIE = By.id("cookienotice-button-accept");
    private static final By SEARCH_ICON = By.xpath("//*[@data-do='trigger-search']");
    private static final By SEARCH_FIELD = By.id("global-search");

    public HomePage acceptCookie() {
        clickElement(ACCEPT_COOKIE);
        waitUntilElementDisappears(ACCEPT_COOKIE);
        return this;
    }

    public HomePage clickSearchIcon() {
        clickElement(SEARCH_ICON);
        return this;
    }

    public HomePage typeWordToSearch(String keyword) {
        fillInputField(SEARCH_FIELD, keyword, true);
        return this;
    }
}
