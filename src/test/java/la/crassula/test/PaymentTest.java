package la.crassula.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static la.crassula.test.BrowserSetting.rightPassword;
import static la.crassula.test.BrowserSetting.rightUsername;

public class PaymentTest {

    public WebDriver driver;

    private WebElement amount;
    private WebElement accountNumber;
    private WebElement details;
    private WebElement templateName;

    @BeforeEach
    public void init() {
        BrowserSetting bs = new BrowserSetting();
        driver = bs.BrowserSettings();

        WebElement usernameInput = driver.findElement(By.id("mat-input-0"));
        WebElement passwordInput = driver.findElement(By.id("mat-input-1"));
        WebElement loginBtn = driver.findElement(By.className("btn"));

        usernameInput.sendKeys(rightUsername);
        passwordInput.sendKeys(rightPassword);
        loginBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.titleContains("Dashboard"));

        driver.findElement(By.xpath("//*[contains(@class,'hidden-m-down')]//a[@href='/payments']")).click();

        wait.until(ExpectedConditions.titleContains("Transfer history"));

        driver.findElement(By.xpath("//a[@href='/payments/internal']")).click();

        amount = driver.findElement(By.id("amount"));
        accountNumber = driver.findElement(By.id("mat-input-3"));
        details = driver.findElement(By.id("transfer-details"));
        templateName = driver.findElement(By.id("mat-input-5"));
    }

    @AfterEach
    public void quit() {
        driver.quit();
    }

    @Test
    public void testSuccessfulPayment() {

        driver.findElement(By.id("mat-select-2")).click();
        driver.findElement(By.xpath("//mat-option/span[contains(.,'USD')]")).click();

        amount.sendKeys("3.57");
        accountNumber.sendKeys("16258069471");
        details.sendKeys("payment in usd to friend");
        templateName.sendKeys("testTemplate");

        WebElement payBtn = driver.findElement(By.xpath("//div[contains(@class,'btn-group--mob-full-reverse')]//button[contains(., 'Continue') and not(@disabled)]"));
        payBtn.click();

        driver.findElement(By.xpath("//button[contains(., 'Confirm') and not(@disabled)]")).click();
    }

    @Test
    public void testPaymentWrongAccountNumberFail() {

        driver.findElement(By.id("mat-select-2")).click();
        driver.findElement(By.xpath("//mat-option/span[contains(.,'USD')]")).click();

        amount.sendKeys("3.57");
        accountNumber.sendKeys("16258069471");
        details.sendKeys("payment in usd to friend");
        templateName.sendKeys("testTemplate");

        WebElement payBtn = driver.findElement(By.xpath("//div[contains(@class,'btn-group--mob-full-reverse')]//button[contains(., 'Continue') and not(@disabled)]"));
        payBtn.click();
    }
}
