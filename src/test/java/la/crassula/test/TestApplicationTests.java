package la.crassula.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestApplicationTests {

    public WebDriver driver;

    @BeforeAll
    public void init() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
    }

    @Test
    void login() {
        driver.get("https://client.demo.crassu.la/");

        WebElement username = driver.findElement(By.id("mat-input-0"));
        WebElement password = driver.findElement(By.id("mat-input-1"));
        WebElement loginBtn = driver.findElement(By.className("btn"));

        username.sendKeys("QAno2FA@crassula.io");
        password.sendKeys("V85lPdks6cUm6#RM^Zd");
        loginBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.titleContains("Dashboard"));

        String expectedUrl = "https://client.demo.crassu.la/dashboard";
        String actualUrl = driver.getCurrentUrl();

        Assertions.assertEquals(expectedUrl, actualUrl);
    }

}
