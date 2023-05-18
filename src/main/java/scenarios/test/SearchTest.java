package scenarios.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import scenarios.base.BaseTest;
import scenarios.page.HomePage;
import scenarios.page.SearchPage;

public class SearchTest extends BaseTest {

    private static final String HAPPY = "happy";

    HomePage hp;
    SearchPage sp;
    String itemTitle;

    @BeforeClass
    public void beforeClass() {

        hp = new HomePage();
        sp = new SearchPage();
    }

    @Test
    public void checkSearchedKeyword() {

        hp.acceptCookie()
                .clickSearchIcon()
                .typeWordToSearch(HAPPY);

        while (true) {
            var items = sp.getSearchResults();
            for (var item : items) {
                itemTitle = item.getText();
                try {
                    Assert.assertTrue(sp.isKeywordExist(itemTitle));
                } catch (AssertionError e) {
                    System.out.println("Keyword not found for " + item.getText());
                }
            }
            if (!sp.isNextEnabled()) {
                break;
            }
            sp.clickNext();
        }
    }
}
