package ucw.tests.billingcenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.billingcenter.BillingCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-120
public class AddressLocalizationTest extends BaseTest {
    private BillingCenter billingCenter;

    @BeforeClass
    public void goToBillingCenter() {
        startTest("Billing Center address localization");
        billingCenter = basePage.loginBillingCenter();
    }

    @Test(description = "Billing Center address localization")
    public void test_addressLocalization() {
        billingCenter.navigateToAccountsPage();
        String actualCountry = billingCenter.atAccountsPage().getDefaultCountry();
        assertEquals(actualCountry, billingCenter.getConfig().getDefaultRegion(), "Address localization validation in Billing center is failed");
    }
}
