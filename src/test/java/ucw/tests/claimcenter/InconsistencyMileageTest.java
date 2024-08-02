package ucw.tests.claimcenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.claimcenter.ClaimCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;


public class InconsistencyMileageTest extends BaseTest {
    private PolicyCenter policyCenter;
    private ClaimCenter claimCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-490: [Evolution - RX] FNOL step 2 mileage: add coherency check with mileage at subscription");
        basePage.selectManagementType(INSURED)
                .selectRandomCountry()
                .selectRandomLabel();
        policyCenter = basePage.loginPolicyCenter();
        claimCenter = basePage.loginClaimCenter();
    }

    @Test(description = "Inconsistency Mileage check")
    public void test_inconsistencyMileage() {
        basePage.selectRandomCoverageType();
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
        claimCenter.atClaimPage().createClaimWithInconsistencyMileage();
        assertTrue(claimCenter.atClaimPage().isClaimCreated(), "Claim creation with inconsistent mileage is failed");
    }
}
