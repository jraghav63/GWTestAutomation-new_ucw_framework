package ucw.tests.billingcenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.billingcenter.BillingCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-1174
public class DelinquencyPlanItalyTest extends BaseTest {
    private BillingCenter billingCenter;

    @BeforeClass
    public void goToBillingCenter() {
        startTest("UCWAR-1174: BC - Delinquency Plan for Italy");
        billingCenter = basePage.loginBillingCenter();
    }

    @Test(description = "Delinquency plan for Italy")
    public void test_delinquencyPlan() {
        billingCenter.navigateToAdministrationPage();
        boolean result = billingCenter.atAdministrationPage().checkDelinquencyPlanForItaly();
        assertTrue(result, "Delinquency plan for Italy validation is failed");
    }
}
