package la.crassula.test;

import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Properties;


public class BaseSteps {
    public static WebDriver webDriver;
    static Properties properties;

    public BaseSteps() {
        BrowserSetting bs = new BrowserSetting();
        webDriver = bs.BrowserSettings();

        ConfigProperties configProperties = new ConfigProperties();
        properties = configProperties.getConfigProperties();
    }

    public WebElement findElementById(String id) {
        return webDriver.findElement(By.id(id));
    }

    public WebElement findElementByClass(String className) {
        return webDriver.findElement(By.className(className));
    }

    public WebElement findElementByPath(String path) {
        return webDriver.findElement(By.xpath(path));
    }

    public List<WebElement> findElementsByPath(String path) {
        return webDriver.findElements(By.xpath(path));
    }

    public void login() {
        WebElement usernameInput = findElementById("mat-input-0");
        WebElement passwordInput = findElementById("mat-input-1");
        WebElement loginBtn = findElementByClass("btn");

        usernameInput.sendKeys(properties.getProperty("rightUsername"));
        passwordInput.sendKeys(properties.getProperty("rightPassword"));
        loginBtn.click();

        WebDriverWait wait = new WebDriverWait(webDriver, 15);
        wait.until(ExpectedConditions.titleContains("Dashboard"));
    }

    public void logout() {
        findElementByPath("//mat-icon[contains(.,'keyboard_arrow_down')]").click();
        findElementByPath("//cl-option[contains(.,'Sign out')]").click();

        WebDriverWait wait = new WebDriverWait(webDriver, 15);
        wait.until(ExpectedConditions.titleContains("Sign in"));
    }

    @AfterEach
    public void quit() {
        webDriver.quit();
    }
}
