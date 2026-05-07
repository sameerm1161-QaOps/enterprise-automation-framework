package tests.ui;

import core.base.BaseTest;
import core.config.ConfigManager;
import ui.pages.CheckoutPage;
import ui.pages.LoginPage;
import ui.pages.ProductsPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("SauceDemo E2E")
@Feature("Shopping Flow")
public class SauceDemoE2ETest extends BaseTest {

    @Test(priority = 1, description = "Valid login test")
    @Story("Login")
    @Severity(SeverityLevel.BLOCKER)
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.login(
                config.get("sauce.username"),
                config.get("sauce.password")
        );

        ProductsPage productsPage = new ProductsPage();
        Assert.assertTrue(productsPage.isPageLoaded(), "Products page not loaded!");
        Assert.assertEquals(productsPage.getPageTitle(), "Products");
    }

    @Test(priority = 2, description = "Invalid login test")
    @Story("Login")
    @Severity(SeverityLevel.NORMAL)
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("wrong_user", "wrong_pass");

        Assert.assertTrue(
                loginPage.getErrorMessage().contains("Username and password do not match"),
                "Error message not displayed!"
        );
    }

    @Test(priority = 3, description = "Complete E2E checkout flow")
    @Story("Checkout")
    @Severity(SeverityLevel.CRITICAL)
    public void testE2ECheckoutFlow() {
        // Step 1 - Login
        LoginPage loginPage = new LoginPage();
        loginPage.login(config.get("sauce.username"), config.get("sauce.password"));

        // Step 2 - Add products
        ProductsPage productsPage = new ProductsPage();
        productsPage.addBackpackToCart();
        productsPage.addBikeLightToCart();
        Assert.assertEquals(productsPage.getCartBadgeCount(), "2");

        // Step 3 - Go to cart
        productsPage.goToCart();

        // Step 4 - Dismiss Chrome password popup using ESCAPE
        try {
            Thread.sleep(2000);
            new org.openqa.selenium.interactions.Actions(getDriver())
                    .sendKeys(org.openqa.selenium.Keys.ESCAPE)
                    .perform();
            Thread.sleep(1000);
        } catch (Exception ignored) {}

        // Step 5 - Checkout
        CheckoutPage checkoutPage = new CheckoutPage();
        checkoutPage.clickCheckout();
        checkoutPage.fillCheckoutInfo("John", "Doe", "12345");
        checkoutPage.clickFinish();

        Assert.assertEquals(
                checkoutPage.getConfirmationMessage(),
                "Thank you for your order!"
        );
    }
}