package la.crassula.test;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.TimeUnit;

public class BrowserSetting {
    @Value("https://client.demo.crassu.la/")
    public String baseURL;

    public static final String rightUsername = "QAno2FA@crassula.io";
    public static final String rightPassword= "V85lPdks6cUm6#RM^Zd";
    public static final String wrongUsername = "crassula.io";
    public static final String wrongPassword= "Um6#RM^Zd";
    public static final String emptyLine= "";

    public WebDriver BrowserSettings() {

        WebDriverSettings wds = new WebDriverSettings();
        WebDriver driver = wds.driverSettings();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.get(this.baseURL);
        return driver;
    }
}
