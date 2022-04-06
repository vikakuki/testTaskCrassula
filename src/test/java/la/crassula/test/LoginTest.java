package la.crassula.test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest extends BaseSteps {

    private final WebElement usernameInput;
    private final WebElement passwordInput;
    private final WebElement loginBtn;
    private final String baseUrl = properties.getProperty("baseURL");

    public LoginTest() {
        usernameInput = findElementById("mat-input-0");
        passwordInput = findElementById("mat-input-1");
        loginBtn = findElementByClass("btn");
    }

    @Test
    void testThatCantLoginWithoutUsernameAndSeeError() {
        usernameInput.sendKeys("");
        passwordInput.sendKeys(properties.getProperty("rightPassword"));
        loginBtn.click();

        String errorText = findElementByPath("//mat-error[contains(., 'The value should not be blank.')]").getText();
        String expectedErrorText = "The value should not be blank.";

        assertEquals(expectedErrorText, errorText,"Should show error - The value should not be blank.");
    }

    @Test
    void testThatCantLoginWithoutPasswordAndSeeError() {
        usernameInput.sendKeys(properties.getProperty("rightUsername"));
        passwordInput.sendKeys("");
        loginBtn.click();

        String errorText = findElementByPath("//mat-error[contains(., 'The value should not be blank.')]").getText();
        String expectedErrorText = "The value should not be blank.";

        assertEquals(expectedErrorText, errorText,"Should show error - The value should not be blank.");
    }

    @Test
    void testThatCantLoginWithoutDataAndSeeError() {
        usernameInput.sendKeys("");
        passwordInput.sendKeys("");

        loginBtn.click();

        String errorText = findElementByPath("//mat-error[contains(., 'The value should not be blank.')]").getText();
        String expectedErrorText = "The value should not be blank.";

        assertEquals(expectedErrorText, errorText, "Should show error - The value should not be blank.");
    }

    @Test
    void testThatCantLoginWithWrongPassAndSeeError() {
        usernameInput.sendKeys(properties.getProperty("rightUsername"));
        passwordInput.sendKeys(properties.getProperty("wrongPassword"));
        loginBtn.click();

        boolean isErrorDisplayed =findElementByPath("//span[.='Invalid credentials.']").isDisplayed();

        assertTrue(isErrorDisplayed, "Didn't show error message, Should write - Invalid credentials");
    }

    @Test
    void testThatCantLoginWithWrongUsernameAndSeeError() {
        usernameInput.sendKeys(properties.getProperty("wrongUsername"));
        passwordInput.sendKeys(properties.getProperty("rightPassword"));
        loginBtn.click();

        boolean isErrorDisplayed = findElementByPath("//span[.='Invalid credentials.']").isDisplayed();

        assertTrue(isErrorDisplayed, "Didn't show error message, Should write - Invalid credentials");
    }

    @Test
    void testSuccessfulLoginAndMoveToDashboard() {
        login();

        String expectedUrl = baseUrl + "dashboard";
        String actualUrl = webDriver.getCurrentUrl();

        assertEquals(expectedUrl, actualUrl,"Actual and expected links are different, didn't login");
        logout();
    }
}
