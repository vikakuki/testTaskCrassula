package la.crassula.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferHistoryTest extends BaseSteps {

    public TransferHistoryTest() throws InterruptedException {
        login();

        findElementByPath("//*[contains(@class,'hidden-m-down')]//a[@href='/payments']").click();

        WebDriverWait wait = new WebDriverWait(webDriver, 15);
        wait.until(ExpectedConditions.titleContains("Transfer history"));

        findElementByPath("//div[contains(@class, 'table-header')]/a[contains(., 'Spent')]").click();
        Thread.sleep(2000L);

        findElementByPath("//mat-table[contains(@class, 'mat-table')]/mat-row").click();
    }

    @Test
    public void testThatLastTransferInformationInHistoryHaveRightInfoAboutMadeTransfer() {
        WebElement amountElement = findElementByPath("//div[contains(@  class, 'summary')]/div[contains(@class,'summary__amount') and (contains(., '-3.57 EUR'))]");
        assertEquals(properties.getProperty("amount") + " EUR", amountElement.getText(),  "Should be transferred -3.57 euro, got wrong amount");

        String detailInfoPath = "//div[contains(@class, 'transaction-details__info')]/div[contains(@class,'transaction-details__info-value') and (contains(.,";
        WebElement typeElement = findElementByPath(detailInfoPath + " 'Transfer'))]");
        assertEquals("Transfer", typeElement.getText(), "Should be type - Transfer, got wrong type");

        WebElement statusElement = findElementByPath(detailInfoPath + " 'Completed'))]");
        assertEquals("Completed", statusElement.getText(),"Status should be - Completed, wrong Status");

        WebElement fromAccountNameElement = findElementByPath(detailInfoPath + " 'QAno2FA QAno2FA'))]");
        assertEquals("QAno2FA QAno2FA", fromAccountNameElement.getText(), "From account should be - QAno2FA QAno2FA, got wrong From Account name from where we did transfer");

        WebElement fromAccountNumberElement = findElementByPath(detailInfoPath + " '30708889032'))]");
        assertEquals("30708889032", fromAccountNumberElement.getText(), "From account id should be - 30708889032, got wrong From Account id from where we did transfer");

        WebElement toAccountNameElement = findElementByPath(detailInfoPath + " 'QAno2FA Q.'))]");
        assertEquals("QAno2FA Q.", toAccountNameElement.getText(),  "To account should be - QAno2FA Q., got wrong To Account name from where we did transfer");

        WebElement toAccountNumberElement = findElementByPath(detailInfoPath + " '52716697397'))]");
        assertEquals("52716697397", toAccountNumberElement.getText(), "To account id should be - 52716697397, got wrong To Account id from where we did transfer");
    }

    @Test
    public void downloadTransferDetailsCSV() throws IOException {
        List<String[]> list = new ArrayList<>();

        String transferAmount = findElementByPath("//div[contains(@class, 'summary__type')]").getText();
        String transferDetails = findElementByPath("//div[contains(@class, 'summary__amount')]").getText();

        list.add(new String[] {"\"" + transferAmount + "\",\"" + transferDetails + "\""});

        List<WebElement> transferDetailsList = findElementsByPath("//div[contains(@class, 'transaction-details__info-item')]");

        int i = 0;
        for (WebElement detailElement : transferDetailsList) {
            if (transferDetailsList.size() - 1 == i) {
                break;
            }
            i++;

            String detail = detailElement.getText().replaceAll("\n", "\",\"");
            list.add(new String[] {"\"" + detail + "\""});
        }

        File csvOutputFile = new File("src/test/resources/testdata.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            list.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        }

        assertTrue(csvOutputFile.exists());
    }

    public String convertToCSV(String[] data) {
        return String.join(",", data);
    }
}
