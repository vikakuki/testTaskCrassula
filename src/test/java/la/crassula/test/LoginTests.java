package la.crassula.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class LoginTests {

    public WebDriver driver;
    public WebElement username;
    public WebElement password;
    public WebElement loginBtn;

    @BeforeEach
    public void init() {
        BrowserSetting bs = new BrowserSetting();
        driver = bs.BrowserSettings();

        username = driver.findElement(By.id("mat-input-0"));
        password = driver.findElement(By.id("mat-input-1"));
        loginBtn = driver.findElement(By.className("btn"));
    }

    @AfterEach
    public void quit() {
       driver.quit();
    }

    @Test
    void loginSuccessful() {
        username.sendKeys("QAno2FA@crassula.io");
        password.sendKeys("V85lPdks6cUm6#RM^Zd");
        loginBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.titleContains("Dashboard"));

        String expectedUrl = "https://client.demo.crassu.la/dashboard";
        String actualUrl = driver.getCurrentUrl();

        Assertions.assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void loginNoUsernameFail() {
        username.sendKeys("");
        password.sendKeys("V85lPdks6cUm6#RM^Zd");
        loginBtn.click();

        boolean displayed = driver.findElement(By.id("mat-error-1")).isDisplayed();

        Assertions.assertTrue(displayed);
    }

    @Test
    void loginNoPassFail() {
        username.sendKeys("QAno2FA@crassula.io");
        password.sendKeys("");
        loginBtn.click();

        boolean displayed = driver.findElement(By.id("mat-error-1")).isDisplayed();

        Assertions.assertTrue(displayed);
    }

    @Test
    void loginNoDataFail() {
        username.sendKeys("");
        password.sendKeys("");
        loginBtn.click();

        boolean displayed = driver.findElement(By.id("mat-error-1")).isDisplayed();

        Assertions.assertTrue(displayed);
    }

    @Test
    void loginWrongPassFail() {
        username.sendKeys("QAno2FA@crassula.io");
        password.sendKeys("#RM^Zd");
        loginBtn.click();

        boolean displayed = driver.findElement(By.xpath("//span[.='Invalid credentials.']")).isDisplayed();

        Assertions.assertTrue(displayed);
    }

    @Test
    void loginWrongUsernameFail() {
        username.sendKeys("crassula.io");
        password.sendKeys("V85lPdks6cUm6#RM^Zd");
        loginBtn.click();

        boolean displayed = driver.findElement(By.xpath("//span[.='Invalid credentials.']")).isDisplayed();

        Assertions.assertTrue(displayed);
    }

}
