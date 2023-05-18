package util;

import scenarios.base.BasePage;

public class UtilPage extends BasePage {

    public UtilPage waitUntilGivenTime(int second) throws InterruptedException {

        Thread.sleep(second * 1000L);
        return this;
    }
}
