package ucw.tests.policycenter;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.enums.Countries;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.*;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class EGVOCreationWithExpiredVIN extends BaseTest {
    private PolicyCenter policyCenter;
    private static final Map<Countries, String> expiredVins = new HashMap<>();
    static {
        expiredVins.put(Italy, "KJLUYEWQ763448734");
        expiredVins.put(Spain, "ABCDEFG123456UDAS");
        expiredVins.put(France, "3G1JC1245WS848211");
    }

    @BeforeClass
    public void goToCenters() {
        startTest("EGVO SA creation with expired/cancelled VIN");
        policyCenter = basePage.loginPolicyCenter();
        basePage.selectLabel(SPOTICAR)
                .selectRandomCountryExcept(Germany)
                .selectManagementType(INSURED);
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
    }

    @AfterMethod
    public void returnAccountSummaryPage() {
        policyCenter.goBackToAccSummary();
    }

    @Test(description = "EGVO creation for an expired VIN")
    public void test_createEGVO_WithAnExpiredVIN() {
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectEGVO();
        policyCenter.atPolicyPage().createEGVOSAWithVIN(expiredVins.get(basePage.getCurrentCountry()));
        String vinErrorMsg = policyCenter.atPolicyPage().getErrorMessageAtVehicleScreen();
        String expectedErrorMsg = "VIN : The GVO policy associated with this VIN is not active.";
        assertEquals(vinErrorMsg, expectedErrorMsg, "Error message for expired VIN does not match with expected");
    }

    @Test(description = "EGVO creation for a cancelled VIN")
    public void test_createEGVO_WithACancelledVIN() {
        basePage.selectRandomCoverageType();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().createNewPolicy();
        assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
        policyCenter.atPolicyPage().clickActionButton();
        policyCenter.atPolicyPage().cancel();

        policyCenter.goBackToAccSummary();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectEGVO();
        policyCenter.atPolicyPage().createEGVOSAWithVIN(basePage.getDataObject().getPolicyCenter().getVin());
        String vinErrorMsg = policyCenter.atPolicyPage().getErrorMessageAtVehicleScreen();
        String expectedErrorMsg = "VIN : The GVO policy associated with this VIN is not active.";
        assertEquals(vinErrorMsg, expectedErrorMsg, "Error message for cancelled VIN does not match with expected");
    }
}