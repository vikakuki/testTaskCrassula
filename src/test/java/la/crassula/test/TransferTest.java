package la.crassula.test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferTest extends BaseSteps {

    private final WebElement amount;

    public TransferTest() throws InterruptedException {
        login();

        findElementByPath("//*[contains(@class,'hidden-m-down')]//a[@href='/payments']").click();

        WebDriverWait wait = new WebDriverWait(webDriver, 15);
        wait.until(ExpectedConditions.titleContains("Transfer history"));

        findElementByPath("//a[@href='/payments/between-my-accounts']").click();

        amount = findElementById("amount");
        Thread.sleep(2000L);
    }

    @Test
    public void testSuccessfulTransferAndSeePopUpWithCompleteMessage() throws InterruptedException {
        amount.sendKeys(properties.getProperty("amount"));

        WebElement payBtn = findElementByPath("//div[contains(@class,'btn-group--mob-full-reverse')]//button[contains(., 'Continue') and not(@disabled)]");
        payBtn.click();

        Thread.sleep(2000L);
        //Potential bug - need to click twice on Continue btn
        payBtn.click();

        findElementByPath("//button[contains(., 'Confirm') and not(@disabled)]").click();

        boolean transferCompleteMsgIsVisible = findElementByPath("//h1[contains(., 'Transfer completed')]").isDisplayed();

        assertTrue(transferCompleteMsgIsVisible, "Transfer failed - should be popUp with Transfer completed");

        findElementByPath("//div[contains(@id,'cdk-overlay-0')]//a[contains(., 'To dashboard')]").click();
    }

    @Test
    public void testThatCantTransferZeroAndSeeError() throws InterruptedException {
        amount.sendKeys("0");
        amount.sendKeys(Keys.ENTER);

        Thread.sleep(2000L);

        String errorText = findElementByPath("//mat-error[contains(., 'The amount must be greater than 0')]").getText();
        String expectedErrorText = "The amount must be greater than 0";

        assertEquals(expectedErrorText, errorText, "Should show error - The amount must be greater than 0");
    }

    @Test
    public void testThatCantTransferNegativeAmountAndSeeError() throws InterruptedException {
        amount.sendKeys("-5");
        amount.sendKeys(Keys.ENTER);

        Thread.sleep(2000L);

        String errorText = findElementByPath("//mat-error[contains(., 'The amount is invalid')]").getText();
        String expectedErrorText = "The amount is invalid";

        assertEquals(expectedErrorText, errorText, "Should show error - The amount is invalid");
    }

    @Test
    public void testThatCantTransferLettersAndSeeError() throws InterruptedException {
        amount.sendKeys("sadasdsad");
        amount.sendKeys(Keys.ENTER);

        Thread.sleep(2000L);

        String errorText = findElementByPath("//mat-error[contains(., 'The amount is invalid')]").getText();
        String expectedErrorText = "The amount is invalid";

        assertEquals(expectedErrorText, errorText, "Should show error - The amount is invalid");
    }

    @Test
    public void testThatToAccountAutomaticallyChangesWhenWeChangeFromAccount() throws InterruptedException {
        Thread.sleep(2000L);

        findElementByClass("select--from-account").click();
        findElementByPath("//div[contains(@class, 'select--from-account')]//div[contains(.,'Second')]").click();

        boolean rightSendToAccount = findElementByPath("//ng-select[contains(@formcontrolname,'toAccount')]//div[contains(@class,'select__description')]//div[contains(., 'Primary account')]").isDisplayed();

        assertTrue(rightSendToAccount, "'To account' should be changed, when change 'From account'");
    }

    @Test
    public void testThatFromAccountAutomaticallyChangesWhenWeChangeToAccount() throws InterruptedException {
        Thread.sleep(2000L);

        findElementByPath("//ng-select[contains(@formcontrolname,'toAccount')]").click();
        findElementByPath("//ng-select[contains(@formcontrolname, 'toAccount')]/ng-dropdown-panel/div/div/div/div/div/div[contains(.,'Primary account')]").click();

        boolean rightSendFromAccount = findElementByClass("select--from-account").getText().contains("Second");

        assertTrue(rightSendFromAccount, "'From account' should be changed, when change 'To account'");
    }
}
