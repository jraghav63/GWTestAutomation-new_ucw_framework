package ucw.tests.policycenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-112
public class PolicyCenterPhoneLocalization extends BaseTest {
    private static final Logger logger = LogManager.getLogger(PolicyCenterPhoneLocalization.class);
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToPolicyCenter() {
        startTest("Policy Center phone localization");
        logger.info("Test environment initializing");
        policyCenter = basePage.loginPolicyCenter();
    }

    @Test(description = "Policy Center phone localization")
    public void test_phoneLocalization() {
        String spainMobileMsg = "Current Region: Spain (34) <br>Example: 810 12 34 56 ext. 1234<br>Enter '+' and the country code for an international number";
        policyCenter.navigateToAccountPage();
        String phoneMessage = policyCenter.atAccountPage().getPhoneMessage("PSA");
        logger.info("Full phone message is " + phoneMessage);
        assertEquals(phoneMessage, spainMobileMsg, "Phone localization validation in Policy Center is failed");
    }
}
