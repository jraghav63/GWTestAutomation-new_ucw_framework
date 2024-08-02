package ucw.tests.claimcenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.claimcenter.ClaimCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.France;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.SELFMANAGED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-408
public class SelfManagedPolicies extends BaseTest {
    private PolicyCenter policyCenter;
    private ClaimCenter claimCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-408: Self insured policies//Display a message at policy search in backoffice and portal");
        basePage.selectLabel(SPOTICAR)
                .selectManagementType(SELFMANAGED)
                .selectCountry(France)
                .selectRandomCoverageType();
        policyCenter = basePage.loginPolicyCenter();
        claimCenter = basePage.loginClaimCenter();
    }

    @Test(description = "Self managed policy")
    public void test_selfManagedPolicies() {
        basePage.switchToPolicyCenterTab();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().createNewPolicy();
        assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");

        basePage.switchToClaimCenterTab();
        claimCenter.navigateToNewClaimPage();
        assertTrue(claimCenter.atClaimPage().isSelfManageErrorDisplayed(), "Self managed error is not shown");
    }
}
