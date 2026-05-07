package core.driver;

import org.openqa.selenium.WebDriver;

/**
 * Thread-safe WebDriver holder using ThreadLocal.
 * Critical for parallel test execution — each thread
 * gets its own isolated WebDriver instance.
 */
public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() { /* utility class — no instantiation */ }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    public static void removeDriver() {
        driverThreadLocal.remove();
    }

    public static boolean hasDriver() {
        return driverThreadLocal.get() != null;
    }
}