package ucw.tests.billingcenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.billingcenter.BillingCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-982
public class SecurityZoneTest extends BaseTest {
    private BillingCenter billingCenter;

    @BeforeClass
    public void goToBillingCenter() {
        startTest("UCWAR-982: Security Zone activation - Italy");
        billingCenter = basePage.loginBillingCenter();
    }

    @Test(description = "Security zones in Italy")
    public void test_securityZones() {
        billingCenter.navigateToAdministrationPage();
        billingCenter.atAdministrationPage().goToSecurityZones();
        assertTrue(billingCenter.atAdministrationPage().validateSecurityZones(), "Security zone validation is failed");
    }
}
