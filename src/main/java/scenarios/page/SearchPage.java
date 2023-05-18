package scenarios.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import scenarios.base.BasePage;

import java.util.List;

public class SearchPage extends BasePage {

    private static final By SEARCH_RESULT = By.xpath("//*[@class='page-section search-results']" +
            "//following-sibling::*[@class='item-list search-results-list']//following::h3[@class='item-title']");

    private static final By NEXT_LINK = By.xpath("//*[@class='page-link' and contains(text(),'Next')]");

    public List<WebElement> getSearchResults() {
        waitUntilElementAppears(SEARCH_RESULT);
        return findElements(SEARCH_RESULT);
    }

    public boolean isKeywordExist(String itemTitle) {

        var xpath = "//*[text()='" + itemTitle + "']/ancestor::*[@class='item-section']/p/span[1]";
        return isElementExist(By.xpath(xpath), 3);
    }

    public boolean isNextEnabled() {
        return isElementExist(NEXT_LINK, 3);
    }

    public void clickNext() {
        clickElement(NEXT_LINK);
    }
}
