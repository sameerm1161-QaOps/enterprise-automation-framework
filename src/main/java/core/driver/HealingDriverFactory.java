package core.driver;

import com.epam.healenium.SelfHealingDriver;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wraps WebDriver with Healenium Self-Healing capability.
 * When locator breaks — Healenium auto-finds element
 * and suggests updated locator.
 */
public class HealingDriverFactory {

    private static final Logger log = LoggerFactory.getLogger(HealingDriverFactory.class);

    public static WebDriver createHealingDriver(String browser, boolean headless) {
        WebDriver delegate = DriverFactory.createDriver(browser, headless, "");
        SelfHealingDriver driver = SelfHealingDriver.create(delegate);
        log.info("Self-healing driver created for browser: {}", browser);
        return driver;
    }
}