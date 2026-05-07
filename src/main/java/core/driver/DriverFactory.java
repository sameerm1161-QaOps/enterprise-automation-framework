package core.driver;
import java.util.HashMap;
import java.util.Map;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.Duration;

/**
 * Factory Pattern: Creates WebDriver instances based on browser type.
 * Supports local, headless, and remote (Selenium Grid / Docker) execution.
 */
public class DriverFactory {

    public static WebDriver createDriver(String browser, boolean headless, String gridUrl) {
        return switch (browser.toLowerCase()) {
            case "chrome"   -> createChromeDriver(headless, gridUrl);
            case "firefox"  -> createFirefoxDriver(headless, gridUrl);
            case "edge"     -> createEdgeDriver(headless, gridUrl);
            default -> throw new IllegalArgumentException(
                    "Unsupported browser: " + browser + ". Use: chrome, firefox, edge"
            );
        };
    }

    private static WebDriver createChromeDriver(boolean headless, String gridUrl) {
        ChromeOptions options = new ChromeOptions();

        // Disable password manager popup permanently
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-features=PasswordLeakDetection");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");
        options.addArguments("--window-size=1920,1080");
        options.setExperimentalOption("excludeSwitches",
                java.util.Arrays.asList("enable-automation"));

        if (headless) options.addArguments("--headless=new");

        if (gridUrl != null && !gridUrl.isEmpty()) {
            return createRemoteDriver(options, gridUrl);
        }

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.manage().window().maximize();
        return driver;
    }
    private static WebDriver createFirefoxDriver(boolean headless, String gridUrl) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) options.addArguments("--headless");

        if (gridUrl != null && !gridUrl.isEmpty()) {
            return createRemoteDriver(options, gridUrl);
        }

        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver(boolean headless, String gridUrl) {
        EdgeOptions options = new EdgeOptions();
        if (headless) options.addArguments("--headless=new");

        if (gridUrl != null && !gridUrl.isEmpty()) {
            return createRemoteDriver(options, gridUrl);
        }

        WebDriverManager.edgedriver().setup();
        return new EdgeDriver(options);
    }

    private static RemoteWebDriver createRemoteDriver(Object options, String gridUrl) {
        try {
            return new RemoteWebDriver(new URL(gridUrl), (org.openqa.selenium.Capabilities) options);
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to Selenium Grid at: " + gridUrl, e);
        }
    }
}