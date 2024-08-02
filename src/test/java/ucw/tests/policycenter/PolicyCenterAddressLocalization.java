package ucw.tests.policycenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-110
public class PolicyCenterAddressLocalization extends BaseTest {
    private static final Logger logger = LogManager.getLogger(PolicyCenterAddressLocalization.class);
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToPolicyCenter() {
        startTest("Policy Center address localization");
        logger.info("Test environment initializing");
        policyCenter = basePage.loginPolicyCenter();
    }

    @Test(description = "Policy Center address localization")
    public void test_addressLocalization() {
        String defaultCountry = "Spain";
        String companyName = "PSA";
        policyCenter.navigateToAccountPage();
        String actualCountry = policyCenter.atAccountPage().getDefaultCountry(companyName);
        assertEquals(defaultCountry, actualCountry, "Address localization validation in Policy center is failed");
    }
}
