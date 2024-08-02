package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class PolicyReinstatementTest extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("Policy reinstatement");
        basePage.selectLabel(SPOTICAR)
                .selectRandomCountry()
                .selectManagementType(INSURED);
        policyCenter = basePage.loginPolicyCenter();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
    }

    @BeforeMethod
    public void createPolicy() {
        basePage.selectRandomCoverageType()
                .selectRandomCar();
        policyCenter.goBackToAccSummary();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().addVehicleDetails();
        policyCenter.atPolicyPage().validateCoverageType();
        policyCenter.atPolicyPage().reviewPolicy();
        policyCenter.atPolicyPage().goToPayment();
        policyCenter.atPolicyPage().issuePolicy();
        assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
        policyCenter.goBackToAccSummary();
    }

    @Test(description = "Policy reinstatement with Insurer validation")
    public void test_insurerValidation() {
        policyCenter.searchPolicy();
        policyCenter.atPolicyPage().clickActionButton();
        policyCenter.atPolicyPage().cancel();
        assertTrue(policyCenter.atPolicyPage().getCancelMsg().contains("Canceled"), "Policy cancel message doesn't match with expected");
        assertEquals(policyCenter.atPolicyPage().getTransactionType(), "Cancellation");
        policyCenter.atPolicyPage().clickActionButton();
        policyCenter.atPolicyPage().reinstate();
        assertTrue(policyCenter.atPolicyPage().getReinstateMessage().contains("In Force"), "Reinstatement message isn't matched with expected");
        assertEquals(policyCenter.atPolicyPage().getTransactionType(), "Reinstatement", "The policy " + basePage.getDataObject().getPolicyNumber() + " reinstated successfully");
    }

    @Test(description = "Policy reinstatement with reinstate button validation after Policy Cancellation as No Reimbursement")
    public void test_reinstateButton() {
        policyCenter.searchPolicy();
        policyCenter.atPolicyPage().clickActionButton();
        policyCenter.atPolicyPage().cancelNoReimbursement();
        policyCenter.searchPolicy();
        policyCenter.atPolicyPage().clickActionButton();
        assertFalse(policyCenter.atPolicyPage().isReinstatementButtonPresent(), "Reinstatement button is displaying, which is not expected");
    }

    @Test(description = "Policy reinstatement with effective date of reinstatement will be the effective date of the cancellation")
    public void test_effectiveDate() {
        policyCenter.searchPolicy();
        policyCenter.atPolicyPage().clickActionButton();
        policyCenter.atPolicyPage().cancelEffectiveDate();
        policyCenter.searchPolicy();
        policyCenter.atPolicyPage().clickActionButton();
        assertTrue(policyCenter.atPolicyPage().isReinstateEffectiveDateMatched(), "Reinstatement effective date validation is failed");
    }

    @Test(description = "Policy reinstatement popup message validation")
    public void test_popupMessage() {
        policyCenter.searchPolicy();
        policyCenter.atPolicyPage().clickActionButton();
        policyCenter.atPolicyPage().cancel();
        policyCenter.searchPolicy();
        policyCenter.atPolicyPage().clickActionButton();
        assertTrue(policyCenter.atPolicyPage().isPopupMessageDisplayAsExpected(), "Reinstatement popup message validation is failed");
    }
}
