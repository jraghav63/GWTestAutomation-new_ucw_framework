package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-104
public class PolicyCancelTest extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("Policy cancel");
        basePage.selectRandomCountry()
                .selectLabel(SPOTICAR)
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
        policyCenter.atPolicyPage().clickActionButton();
    }

    @Test(description = "Policy cancel as insurer")
    public void test_insurerValidation() {
        policyCenter.atPolicyPage().cancelAsInsurer();
    }

    @Test(description = "Policy cancel reason validation")
    public void test_reasonValidation() {
        assertTrue(policyCenter.atPolicyPage().validateDifferentReasons(), "Cancel policy with different reason is failed");
    }

    @Test(description = "Policy cancel with reason 'Satisfied or Reimbursed'")
    public void test_withReasonSatisfiedOrReimbursed() {
        policyCenter.atPolicyPage().cancelWithSpecificReason("Satisfied or Reimbursed");
    }

    @Test(description = "Policy cancel with reason 'Conformity Warranty Issue'")
    public void test_withReasonConformityWarrantyIssue() {
        policyCenter.atPolicyPage().cancelWithSpecificReason("Conformity Warranty Issue");
    }

    @Test(description = "Policy cancel with reason 'Other'")
    public void test_withReasonOther() {
        policyCenter.atPolicyPage().cancelWithSpecificReason("Other");
    }

    @Test(description = "Policy cancel with insurer decision")
    public void test_withInsurerDecision() {
        assertTrue(policyCenter.atPolicyPage().cancelInsurerDecision(), "Cancel with Insurer decision is failed");
    }

    @Test(description = "Policy cancel with insurer cost of change validation", enabled = false)
    public void test_insurerCostOfChange() {
        String actualCostOfChange = policyCenter.atPolicyPage().getCostOfChangeInsurer();
        assertEquals(actualCostOfChange, "0", "The change of cost is not displayed as expected");
    }

    @Test(description = "Policy cancel with change in cost for Full Reimbursement", enabled = false)
    public void test_changeInCostForFR() {
        assertTrue(policyCenter.atPolicyPage().checkChangeInCostForFullReimbursement(), "Cost in change for Full Reimbursement validation is failed");
    }
}
