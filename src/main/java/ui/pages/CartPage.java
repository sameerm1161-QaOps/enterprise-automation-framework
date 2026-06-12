package ui.pages;

import ui.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CartPage extends BasePage {

    private final By cartTitle        = By.cssSelector(".title");
    private final By cartItems        = By.cssSelector(".cart_item");
    private final By checkoutBtn      = By.cssSelector("[data-test='checkout']");
    private final By continueShopBtn  = By.cssSelector("[data-test='continue-shopping']");
    private final By removeBackpack   = By.cssSelector("[data-test='remove-sauce-labs-backpack']");
    private final By removebikeLight  = By.cssSelector("[data-test='remove-sauce-labs-bike-light']");
    private final By itemName         = By.cssSelector(".inventory_item_name");
    private final By itemPrice        = By.cssSelector(".inventory_item_price");

    @Step("Verify cart page loaded")
    @Override
    public boolean isPageLoaded() {
        return isDisplayed(cartTitle);
    }

    @Step("Get cart items count")
    public int getCartItemsCount() {
        return getDriver().findElements(cartItems).size();
    }

    @Step("Click checkout")
    public void clickCheckout() {
        click(checkoutBtn);
    }

    @Step("Continue shopping")
    public void continueShopping() {
        click(continueShopBtn);
    }

    @Step("Remove backpack from cart")
    public void removeBackpack() {
        click(removeBackpack);
    }

    @Step("Remove bike light from cart")
    public void removeBikeLight() {
        click(removebikeLight);
    }

    @Step("Get first item name")
    public String getFirstItemName() {
        return getText(itemName);
    }

    @Step("Get first item price")
    public String getFirstItemPrice() {
        return getText(itemPrice);
    }
}
