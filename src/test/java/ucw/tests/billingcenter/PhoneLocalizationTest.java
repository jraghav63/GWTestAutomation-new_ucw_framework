package ucw.tests.billingcenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.billingcenter.BillingCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-122
public class PhoneLocalizationTest extends BaseTest {
    private BillingCenter billingCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("Billing Center phone localization");
        billingCenter = basePage.loginBillingCenter();
    }

    @Test(description = "Billing Center phone localization")
    public void test_phoneLocalization() {
        billingCenter.navigateToAccountsPage();
        assertTrue(billingCenter.atAccountsPage().validatePhoneLocalization(), "Phone localization validation in Billing Center is failed");
    }
}
