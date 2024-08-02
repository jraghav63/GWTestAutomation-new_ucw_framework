package ucw.tests.policycenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static ucw.enums.Environments.DEV;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-111
public class PolicyCenterCurrencyLocalization extends BaseTest {
    private static final Logger logger = LogManager.getLogger(PolicyCenterCurrencyLocalization.class);
    private PolicyCenter policyCenter;
    private static String producerCode = "AUTO";

    @BeforeClass
    public void goToPolicyCenter() {
        startTest("Policy Center currency localization");
        logger.info("Test environment initializing");
        policyCenter = basePage.loginPolicyCenter();
        if (!basePage.getCurrentEnvironment().equals(DEV)) {
            producerCode = "001102Q";
        }
    }

    @Test(description = "Policy Center currency localization")
    public void test_currencyLocalization() {
        policyCenter.navigateToAdministrationPage();
        String actualDefaultCurrency = policyCenter.atAdministrationPage().getDefaultCurrency(producerCode);
        assertEquals(actualDefaultCurrency, "EUR","Currency localization validation in Policy Center is failed");
    }
}
