package la.crassula.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {

    static Properties properties;

    ConfigProperties() {
        properties = new Properties();
        try {
            InputStream input = new FileInputStream("src/test/resources/config.properties");
            properties.load(input);
            System.out.println(properties.getProperty("browser"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Properties getConfigProperties() {
        return properties;
    }
}
