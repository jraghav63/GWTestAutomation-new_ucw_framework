package ucw.tests.claimcenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.claimcenter.ClaimCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ucw.enums.AccountType.Company;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-1216
public class ClaimTimeProcessQuote extends BaseTest {
    private ClaimCenter claimCenter;
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-1216: Time to process the quote enhancement");
        basePage.selectLabel(SPOTICAR)
                .selectManagementType(INSURED)
                .selectRandomCountry()
                .selectRandomCoverageType();
        policyCenter = basePage.loginPolicyCenter();
        claimCenter = basePage.loginClaimCenter();
    }

    @Test(description = "Claim time process quote validation")
    public void test_timeProcessQuote() {
        basePage.getDataObject().setCompanyName("PCCompany2023081505");
        basePage.setAccountTypeAs(Company);
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
        claimCenter.atClaimPage().addQuote();
        String processTime = claimCenter.atClaimPage().getTimeToProcessQuote();
        assertFalse(processTime.isEmpty(), "Cannot get time of quote processing");
    }
}
