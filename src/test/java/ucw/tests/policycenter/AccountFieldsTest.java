package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-1097
public class AccountFieldsTest extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToPolicyCenter() {
        startTest("UCWAR-1097: PC Account Fields (Name, City, Email)");
        policyCenter = basePage.loginPolicyCenter();
    }

    @Test(description = "Account fields validation")
    public void test_accountFields() {
        policyCenter.navigateToAccountPage();
        assertTrue(policyCenter.atAccountPage().validateCreationScreenFields(), "Account fields validation is not expected");
    }
}
