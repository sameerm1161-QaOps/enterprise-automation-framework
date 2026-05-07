package ui.base;

import core.driver.DriverManager;
import utils.ScreenshotUtil;
import utils.WaitUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base Page: All page objects extend this.
 * Contains reusable, wrapped Selenium actions.
 * Never use raw WebDriver calls in page classes.
 */
public abstract class BasePage {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    protected WebDriver driver;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    @Step("Click on element: {locator}")
    protected void click(By locator) {
        log.debug("Clicking element: {}", locator);
        WaitUtil.waitForClickable(driver, locator).click();
    }

    @Step("Type '{text}' into element: {locator}")
    protected void type(By locator, String text) {
        log.debug("Typing '{}' into: {}", text, locator);
        WebElement element = WaitUtil.waitForVisible(driver, locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return WaitUtil.waitForVisible(driver, locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return WaitUtil.waitForVisible(driver, locator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    protected void selectByText(By locator, String text) {
        new Select(WaitUtil.waitForVisible(driver, locator)).selectByVisibleText(text);
    }

    protected void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void jsClick(By locator) {
        WebElement element = WaitUtil.waitForClickable(driver, locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void waitForPageLoad() {
        WaitUtil.waitForPageLoad(driver);
    }

    public abstract boolean isPageLoaded();
}