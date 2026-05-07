package ui.pages;

import ui.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton   = By.id("login-button");
    private final By errorMessage  = By.cssSelector("[data-test='error']");

    @Step("Login with username: {username}")
    public void login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    @Step("Get error message")
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    @Override
    public boolean isPageLoaded() {
        return isDisplayed(loginButton);
    }
}