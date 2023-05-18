package scenarios.base;

import factory.DriverFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest extends TestHelper {

    protected static Logger logger = LogManager.getLogger(BaseTest.class.getName());

    @BeforeClass(alwaysRun = true)
    public void setUp() {

        logger.log(Level.INFO, "BeforeClass is started for");
        initDriver();
        initFileDirectory();
        logger.info("BeforeClass is finished.");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(ITestContext context) {
        DriverFactory.quitDriver();
    }
}
