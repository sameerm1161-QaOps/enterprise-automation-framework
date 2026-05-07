package tests.ui;

import core.base.BaseTest;
import core.config.ConfigManager;
import ui.pages.CheckoutPage;
import ui.pages.LoginPage;
import ui.pages.ProductsPage;
import utils.VisualTestUtil;
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

        // Visual check
        if (config.getBoolean("applitools.enabled")) {
            VisualTestUtil.initEyes("Valid Login");
            VisualTestUtil.checkWindow("Products Page After Login");
            VisualTestUtil.closeEyes();
        }
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

        // Visual check
        if (config.getBoolean("applitools.enabled")) {
            VisualTestUtil.initEyes("Invalid Login");
            VisualTestUtil.checkWindow("Login Error Message");
            VisualTestUtil.closeEyes();
        }
    }

    @Test(priority = 3, description = "Complete E2E checkout flow")
    @Story("Checkout")
    @Severity(SeverityLevel.CRITICAL)
    public void testE2ECheckoutFlow() {
        // Step 1 - Login
        LoginPage loginPage = new LoginPage();
        loginPage.login(
                config.get("sauce.username"),
                config.get("sauce.password")
        );

        // Step 2 - Add products
        ProductsPage productsPage = new ProductsPage();
        productsPage.addBackpackToCart();
        productsPage.addBikeLightToCart();
        Assert.assertEquals(productsPage.getCartBadgeCount(), "2", "Cart count mismatch!");

        // Visual check - Products
        if (config.getBoolean("applitools.enabled")) {
            VisualTestUtil.initEyes("E2E Checkout Flow");
            VisualTestUtil.checkWindow("Products Page With Items");
        }

        // Step 3 - Go to cart
        productsPage.goToCart();

        // Step 4 - Dismiss Chrome password popup
        try {
            Thread.sleep(2000);
            new org.openqa.selenium.interactions.Actions(getDriver())
                    .sendKeys(org.openqa.selenium.Keys.ESCAPE)
                    .perform();
            Thread.sleep(1000);
        } catch (Exception ignored) {}

        // Visual check - Cart
        if (config.getBoolean("applitools.enabled")) {
            VisualTestUtil.checkWindow("Cart Page");
        }

        // Step 5 - Checkout
        CheckoutPage checkoutPage = new CheckoutPage();
        checkoutPage.clickCheckout();
        checkoutPage.fillCheckoutInfo("John", "Doe", "12345");

        // Visual check - Overview
        if (config.getBoolean("applitools.enabled")) {
            VisualTestUtil.checkWindow("Checkout Overview");
        }

        // Step 6 - Finish
        checkoutPage.clickFinish();

        Assert.assertEquals(
                checkoutPage.getConfirmationMessage(),
                "Thank you for your order!",
                "Order confirmation failed!"
        );

        // Visual check - Confirmation
        if (config.getBoolean("applitools.enabled")) {
            VisualTestUtil.checkWindow("Order Confirmation");
            VisualTestUtil.closeEyes();
        }
    }
}