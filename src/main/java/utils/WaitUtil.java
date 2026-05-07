package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class WaitUtil {

    private static final Logger log = LoggerFactory.getLogger(WaitUtil.class);
    private static final int DEFAULT_WAIT = 10;

    public static WebElement waitForVisible(WebDriver driver, By locator) {
        log.debug("Waiting for visible: {}", locator);
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, By locator) {
        log.debug("Waiting for clickable: {}", locator);
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForPageLoad(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(d -> ((org.openqa.selenium.JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
    }

    public static boolean waitForUrlContains(WebDriver driver, String urlPart) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT))
                .until(ExpectedConditions.urlContains(urlPart));
    }

    public static void hardWait(int seconds) {
        try {
            Thread.sleep(Duration.ofSeconds(seconds).toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}