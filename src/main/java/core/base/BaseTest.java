package core.base;

import core.config.ConfigManager;
import core.driver.DriverFactory;
import core.driver.DriverManager;
import listeners.RetryAnalyzer;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

/**
 * Base class for ALL UI tests.
 * Handles: driver lifecycle, config injection, logging, reporting hooks.
 * Every test class must extend this.
 */
@Listeners({listeners.TestListener.class})
public abstract class BaseTest {

    protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    protected ConfigManager config = ConfigManager.getInstance();

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        log.info("=== Test Suite Starting ===");
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "headless"})
    public void setUp(
            @Optional("chrome") String browser,
            @Optional("false") String headless) {

        String resolvedBrowser = System.getProperty("browser", browser);
        boolean isHeadless = Boolean.parseBoolean(
                System.getProperty("headless", headless)
        );
        String gridUrl = config.get("selenium.grid.url", "");

        log.info("Starting test | Browser: {} | Headless: {}", resolvedBrowser, isHeadless);

        WebDriver driver = DriverFactory.createDriver(resolvedBrowser, isHeadless, gridUrl);
        DriverManager.setDriver(driver);
        driver.get(config.get("base.url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (!result.isSuccess()) {
            log.error("Test FAILED: {}", result.getName());
            // Screenshot taken by TestListener automatically
        }

        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            driver.quit();
            DriverManager.removeDriver();
            log.info("Driver closed for thread: {}", Thread.currentThread().getId());
        }
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        log.info("=== Test Suite Complete ===");
    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }
}