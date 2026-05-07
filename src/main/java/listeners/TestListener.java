package listeners;

import core.driver.DriverManager;
import utils.ScreenshotUtil;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;

import java.io.ByteArrayInputStream;

/**
 * TestNG listener that hooks into the test lifecycle.
 * Auto-attaches screenshots to Allure on failures.
 */
public class TestListener implements ITestListener, ISuiteListener {

    private static final Logger log = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info("▶ TEST START: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("✅ TEST PASSED: {}", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("❌ TEST FAILED: {} | Reason: {}",
                result.getName(), result.getThrowable().getMessage());

        // Auto-screenshot to Allure on failure
        if (DriverManager.hasDriver()) {
            byte[] screenshot = ScreenshotUtil.takeScreenshotAsBytes();
            Allure.addAttachment(
                    "Failure Screenshot - " + result.getName(),
                    new ByteArrayInputStream(screenshot)
            );
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("⏭ TEST SKIPPED: {}", result.getName());
    }

    @Override
    public void onStart(ISuite suite) {
        log.info("=== SUITE STARTED: {} ===", suite.getName());
    }

    @Override
    public void onFinish(ISuite suite) {
        log.info("=== SUITE FINISHED: {} ===", suite.getName());
    }
}