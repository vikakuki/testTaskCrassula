package la.crassula.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static la.crassula.test.BrowserSetting.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest {

    private WebDriver webDriver;
    private WebElement usernameInput;
    private WebElement passwordInput;
    private WebElement loginBtn;

    @BeforeEach
    public void init() {
        BrowserSetting bs = new BrowserSetting();
        webDriver = bs.BrowserSettings();

        usernameInput = webDriver.findElement(By.id("mat-input-0"));
        passwordInput = webDriver.findElement(By.id("mat-input-1"));
        loginBtn = webDriver.findElement(By.className("btn"));
    }

    @AfterEach
    public void quit() {
       webDriver.quit();
    }

    @Test
    void testSuccessfulLogin() {
        usernameInput.sendKeys(rightUsername);
        passwordInput.sendKeys(rightPassword);
        loginBtn.click();

        WebDriverWait wait = new WebDriverWait(webDriver, 15);
        wait.until(ExpectedConditions.titleContains("Dashboard"));

        String expectedUrl = "https://client.demo.crassu.la/dashboard";
        String actualUrl = webDriver.getCurrentUrl();

        assertEquals(expectedUrl, actualUrl,"Should login, something went wrong");
    }

    @Test
    void testLoginWithoutUsernameFail() {
        usernameInput.sendKeys(emptyLine);
        passwordInput.sendKeys(rightPassword);
        loginBtn.click();

        boolean isErrorDisplayed = webDriver.findElement(By.id("mat-error-1")).isDisplayed();

        assertTrue(isErrorDisplayed, "Should write about blank username");
    }

    @Test
    void testLoginWithoutPassFail() {
        usernameInput.sendKeys(rightUsername);
        passwordInput.sendKeys(emptyLine);
        loginBtn.click();

        boolean isErrorDisplayed = webDriver.findElement(By.id("mat-error-1")).isDisplayed();

        assertTrue(isErrorDisplayed, "Should write about blank password");
    }

    @Test
    void testLoginWithoutDataFail() {
        usernameInput.sendKeys(emptyLine);
        passwordInput.sendKeys(emptyLine);
        loginBtn.click();

        boolean isErrorDisplayed = webDriver.findElement(By.id("mat-error-1")).isDisplayed();

        assertTrue(isErrorDisplayed, "Should write about blank username and password");
    }

    @Test
    void testLoginWithWrongPassFail() {
        usernameInput.sendKeys(rightUsername);
        passwordInput.sendKeys(wrongPassword);
        loginBtn.click();

        boolean isErrorDisplayed = webDriver.findElement(By.xpath("//span[.='Invalid credentials.']")).isDisplayed();

        assertTrue(isErrorDisplayed, "Should write - Invalid credentials");
    }

    @Test
    void testLoginWithWrongUsernameFail() {
        usernameInput.sendKeys(wrongUsername);
        passwordInput.sendKeys(rightPassword);
        loginBtn.click();

        boolean isErrorDisplayed = webDriver.findElement(By.xpath("//span[.='Invalid credentials.']")).isDisplayed();

        assertTrue(isErrorDisplayed, "Should write - Invalid credentials");
    }

}
