package utils;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import core.config.ConfigManager;
import core.driver.DriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisualTestUtil {

    private static final Logger log = LoggerFactory.getLogger(VisualTestUtil.class);
    private static EyesRunner runner;
    private static Eyes eyes;
    private static final ConfigManager config = ConfigManager.getInstance();

    public static void initEyes(String testName) {
        runner = new ClassicRunner();
        eyes = new Eyes(runner);
        eyes.setApiKey(config.get("applitools.api.key"));
        eyes.open(
                DriverManager.getDriver(),
                config.get("applitools.app.name"),
                testName,
                new RectangleSize(1920, 1080)
        );
        log.info("Applitools Eyes initialized: {}", testName);
    }

    public static void checkWindow(String checkpointName) {
        if (eyes != null) {
            eyes.checkWindow(checkpointName);
            log.info("Visual checkpoint: {}", checkpointName);
        }
    }

    public static void closeEyes() {
        if (eyes != null) {
            eyes.closeAsync();
            TestResultsSummary results = runner.getAllTestResults(false);
            log.info("Visual results: {}", results);
        }
    }
}