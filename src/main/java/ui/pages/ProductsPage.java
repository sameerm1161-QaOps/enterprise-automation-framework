package ui.pages;

import ui.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductsPage extends BasePage {

    private final By pageTitle      = By.cssSelector(".title");
    private final By cartBadge      = By.cssSelector(".shopping_cart_badge");
    private final By cartIcon       = By.cssSelector(".shopping_cart_link");
    private final By addBackpackBtn = By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']");
    private final By addBikeLightBtn = By.cssSelector("[data-test='add-to-cart-sauce-labs-bike-light']");

    @Step("Verify products page loaded")
    public boolean isPageLoaded() {
        return isDisplayed(pageTitle);
    }

    @Step("Get page title")
    public String getPageTitle() {
        return getText(pageTitle);
    }

    @Step("Add Backpack to cart")
    public void addBackpackToCart() {
        click(addBackpackBtn);
    }

    @Step("Add Bike Light to cart")
    public void addBikeLightToCart() {
        click(addBikeLightBtn);
    }

    @Step("Get cart badge count")
    public String getCartBadgeCount() {
        return getText(cartBadge);
    }

    @Step("Go to cart")
    public void goToCart() {
        click(cartIcon);
    }
}