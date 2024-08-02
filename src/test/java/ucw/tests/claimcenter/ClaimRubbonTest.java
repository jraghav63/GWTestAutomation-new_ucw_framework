package ucw.tests.claimcenter;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.claimcenter.ClaimCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-491
public class ClaimRubbonTest extends BaseTest {
    private PolicyCenter policyCenter;
    private ClaimCenter claimCenter;

    @BeforeClass
    public void goToClaimCenter() {
        startTest("UCWAR-491: [Evolution - RX] CC // Evolution // Add parameters in rubbon");
        basePage.selectLabel(SPOTICAR)
                .selectManagementType(INSURED)
                .selectRandomCountry()
                .selectRandomCoverageType();
        policyCenter = basePage.loginPolicyCenter();
        claimCenter = basePage.loginClaimCenter();
    }

    @Test(description = "Claim rubbon validation")
    public void test_claimRubbon() {
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
        claimCenter.atClaimPage().createClaim();
        assertTrue(claimCenter.atClaimPage().isClaimCreated(), "Claim creation is failed");
        claimCenter.atClaimPage().checkStatus();
        claimCenter.atClaimPage().checkProductName();
        String expectedDiffValue = claimCenter.atClaimPage().getDiffValue();
        String actualDiffValue = claimCenter.atClaimPage().getActualDiffDate();
        assertEquals(actualDiffValue, expectedDiffValue, "Check diff is failed");

        String expectedMileageDistance = claimCenter.atClaimPage().getMileageDistanceValue();
        String actualMileageDistance = claimCenter.atClaimPage().getMileageDistance();
        assertEquals(actualMileageDistance, expectedMileageDistance, "Check distance is failed");
    }
}
