package scenarios.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import scenarios.base.BaseTest;
import scenarios.page.HomePage;
import scenarios.page.TechDocPage;
import util.UtilPage;

public class TechDocsTest extends BaseTest {

    private static final String KEYWORD = "Luna EUR";
    private static final String CATALOG_NAME = "LUNA EUR (Multi Language)";
    private static final String PDF_NAME = "D-S006VT_-.pdf";
    private static final String[] CATALOG_URL_LIST = new String[]{
            "https://www.lelynet.com/_layouts/15/document/TechDocHandler.aspx?name=D-S006VT_-.pdf&mode=view",
            "https://www.lelynet.com/_layouts/15/document/TechDocHandler.aspx?name=D-S032VT_-.pdf&mode=view"};
    HomePage hp;
    TechDocPage tp;
    UtilPage utp;
    String filePath;

    @BeforeClass
    public void beforeClass() {

        hp = new HomePage();
        tp = new TechDocPage();
        utp = new UtilPage();
        driver.get().get(config.getTechDocsUrl());
        filePath = downloadedFilesDirPath.get();
    }

    @Test
    public void checkCatalogs() throws InterruptedException {

        hp.acceptCookie();
        utp.waitUntilGivenTime(3);
        tp.typeCatalogNameToFilter(KEYWORD)
                .selectCatalogNameFromDropdown(KEYWORD);

        for (var index = 0; index < tp.getCatalogs().size(); index++) {
            Assert.assertEquals(tp.checkCatalogName(index), CATALOG_NAME);
            tp.openCatalog(index);
            tp.getTab(index + 1);
            Assert.assertEquals(driver.get().getCurrentUrl(), CATALOG_URL_LIST[index]);
            tp.getTab(0);
        }
        tp.clickDownloadCatalogButton(1);
        Assert.assertTrue(tp.isFileDownloaded(filePath, PDF_NAME));
    }
}
