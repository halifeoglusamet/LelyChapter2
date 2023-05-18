package scenarios.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import scenarios.base.BasePage;

import java.io.File;
import java.util.List;

public class TechDocPage extends BasePage {

    private static final By PRODUCT_FILTER = By.xpath("//*[@id='select2-id_q-container']");
    private static final By SEARCH_FIELD = By.xpath("//input[@class='select2-search__field']");
    private static final By CATALOGS = By.xpath("//*[@class='result-list item-list']/li");

    public boolean isFileDownloaded(String downloadDirectory, String fileName) {

        File dir = new File(downloadDirectory);
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().equals(fileName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public TechDocPage typeCatalogNameToFilter(String docName) {

        clickElement(PRODUCT_FILTER);
        fillInputField(SEARCH_FIELD, docName, false);
        return this;
    }

    public void selectCatalogNameFromDropdown(String catalogName) throws InterruptedException {

        var xpath = "//*[@role='treeitem' and text()='" + catalogName + "']";
        clickElement(By.xpath(xpath));
    }

    public List<WebElement> getCatalogs() {

        return findElements(CATALOGS);
    }

    public String checkCatalogName(int index) {

        var xpath = "//*[@class='result-list item-list']/li[" + (index + 1) + "]//following-sibling::h3";
        return findElement(By.xpath(xpath)).getText();
    }

    public void openCatalog(int index) {

        var xpath = "//*[@class='result-list item-list']/li[" + (index + 1) + "]//following-sibling::*[contains(text(),'View this document')]";
        executeScrollScript(By.xpath(xpath));
        clickElement(By.xpath(xpath));
    }

    public void getTab(int index) {
        switchTab(index);
    }

    public void clickDownloadCatalogButton(int index) {

        var xpath = "//*[@class='result-list item-list']/li[" + index + "]//following-sibling::*[contains(text(),'Download')]";
        clickElement(By.xpath(xpath));
    }
}
