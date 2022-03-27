package la.crassula.test;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class BrowserSetting {

    public WebDriver BrowserSettings() {

        WebDriverSettings wds = new WebDriverSettings();
        WebDriver driver = wds.driverSettings();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.get("https://client.demo.crassu.la/");
        return driver;
    }
}
