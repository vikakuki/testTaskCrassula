package la.crassula.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

class TestApplicationTests {

    public WebDriver driver;

    @BeforeEach
    public void init() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        driver.get("https://client.demo.crassu.la/");

        WebElement username = driver.findElement(By.id("mat-input-0"));
        WebElement password = driver.findElement(By.id("mat-input-1"));
        WebElement loginBtn = driver.findElement(By.className("btn"));

        username.sendKeys("QAno2FA@crassula.io");
        password.sendKeys("V85lPdks6cUm6#RM^Zd");
        loginBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.titleContains("Dashboard"));
    }

    @AfterEach
    public void quit() {
        driver.quit();
    }

    @Test
    void login() {
        String expectedUrl = "https://client.demo.crassu.la/dashboard";
        String actualUrl = driver.getCurrentUrl();

        Assertions.assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void makePayment() {
        driver.findElement(By.xpath("//*[contains(@class,'hidden-m-down')]//a[@href='/payments']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.titleContains("Transfer history"));

        driver.findElement(By.xpath("//a[@href='/payments/internal']")).click();

        driver.findElement(By.id("mat-select-2")).click();
        driver.findElement(By.xpath("//mat-option/span[contains(.,'USD')]")).click();

        WebElement amount = driver.findElement(By.id("amount"));
        WebElement accountNumber = driver.findElement(By.id("mat-input-3"));
        WebElement details = driver.findElement(By.id("transfer-details"));
        WebElement templateName = driver.findElement(By.id("mat-input-5"));

        amount.sendKeys("3.57");
        accountNumber.sendKeys("16258069471");
        details.sendKeys("payment in usd to friend");
        templateName.sendKeys("testTemplate");

        WebElement payBtn = driver.findElement(By.xpath("//div[contains(@class,'btn-group--mob-full-reverse')]//button[contains(., 'Continue') and not(@disabled)]"));
        payBtn.click();

        driver.findElement(By.xpath("//button[contains(., 'Confirm') and not(@disabled)]")).click();
    }

}
