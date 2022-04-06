package la.crassula.test;

import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Configuration
public class BrowserSetting {

    public WebDriver BrowserSettings() {

        ConfigProperties configProperties = new ConfigProperties();
        Properties properties = configProperties.getConfigProperties();

        WebDriverSettings wds = new WebDriverSettings();
        WebDriver driver = wds.driverSettings();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.get(properties.getProperty("baseURL"));
        return driver;
    }
}
