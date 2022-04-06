package la.crassula.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogoutTest extends BaseSteps  {

    private final String baseUrl = properties.getProperty("baseURL");

    @Test
    void testLogoutSuccessful() {
        login();
        logout();

        String expectedUrl = baseUrl + "sign-in";
        String actualUrl = webDriver.getCurrentUrl();

        assertEquals(expectedUrl, actualUrl,"Actual and expected links are different, didn't logout");
    }
}
