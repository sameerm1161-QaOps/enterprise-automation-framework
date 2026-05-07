package ui.pages;

import core.driver.DriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.time.Duration;

public class CheckoutPage extends BasePage {

    // Cart page
    private final By checkoutBtn    = By.cssSelector("[data-test='checkout']");

    // Checkout info page
    private final By firstNameField = By.cssSelector("[data-test='firstName']");
    private final By lastNameField  = By.cssSelector("[data-test='lastName']");
    private final By postalField    = By.cssSelector("[data-test='postalCode']");
    private final By continueBtn    = By.cssSelector("[data-test='continue']");

    // Overview page
    private final By finishBtn      = By.cssSelector("[data-test='finish']");

    // Confirmation page
    private final By confirmHeader  = By.cssSelector(".complete-header");

    @Step("Click checkout button")
    public void clickCheckout() {
        // Dismiss any browser popup if present
        try {
            WebDriverWait wait = new WebDriverWait(
                    DriverManager.getDriver(), Duration.ofSeconds(3)
            );
            wait.until(ExpectedConditions.alertIsPresent());
            DriverManager.getDriver().switchTo().alert().dismiss();
        } catch (Exception ignored) {}

        click(checkoutBtn);
    }


    @Step("Fill checkout info")
    public void fillCheckoutInfo(String firstName, String lastName, String postal) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(postalField, postal);
        click(continueBtn);
    }

    @Step("Click finish button")
    public void clickFinish() {
        click(finishBtn);
    }

    @Step("Get confirmation message")
    public String getConfirmationMessage() {
        return getText(confirmHeader);
    }

    @Override
    public boolean isPageLoaded() {
        return isDisplayed(checkoutBtn);
    }
}