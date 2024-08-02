package ucw.tests.policycenter;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.billingcenter.BillingCenter;
import ucw.pages.claimcenter.ClaimCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.France;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.DELEGATED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class ManageTypeTest extends BaseTest {
    private PolicyCenter policyCenter;
    private BillingCenter billingCenter;
    private ClaimCenter claimCenter;

    @BeforeClass
    public void goToCenters() {
        basePage.selectLabel(SPOTICAR)
                .selectCountry(France);
        startTest("Manage type test in PC, BC and CC");
        policyCenter = basePage.loginPolicyCenter();
        billingCenter = basePage.loginBillingCenter();
        claimCenter = basePage.loginClaimCenter();
    }

    @Test(description = "Delegated management type validation")
    public void test_manageType_Delegated() {
        basePage.selectManagementType(DELEGATED)
                .selectRandomCar()
                .selectRandomCoverageType();

        basePage.switchToBillingCenterTab();
        billingCenter.navigateToAccountsPage();
        billingCenter.atAccountsPage().createAccount();
        assertTrue(billingCenter.atAccountsPage().isAccountCreated(), "Billing Center account creation is failed");

        basePage.switchToPolicyCenterTab();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().createNewPolicy();
        assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
        String managementTypePCUI = policyCenter.atPolicyPage().getManagementTypeValue();
        assertEquals(managementTypePCUI, DELEGATED, "Validation of Management Type in Policy Center is failed");

        basePage.switchToBillingCenterTab();
        billingCenter.navigateToPoliciesPage();
        assertTrue(billingCenter.atPolicyPage().searchPolicy(basePage.getDataObject().getPolicyNumber()));
        String managementTypeBCUI = billingCenter.atPolicyPage().getManagementType();
        assertEquals(managementTypeBCUI, DELEGATED, "Validation of Management Type in Billing Center is failed");
        billingCenter.navigateToDesktopPage();

        basePage.switchToClaimCenterTab();
        claimCenter.navigateToNewClaimPage();
        claimCenter.atClaimPage().createClaim();
        assertTrue(claimCenter.atClaimPage().isClaimCreated(), "Claim creation is failed");
        String managementTypeCCUI = claimCenter.atClaimPage().getManagementType();
        assertEquals(managementTypeCCUI, DELEGATED, "Validation of Management Type in Claim Center is failed");
        claimCenter.navigateToDesktopPage();
    }
}
