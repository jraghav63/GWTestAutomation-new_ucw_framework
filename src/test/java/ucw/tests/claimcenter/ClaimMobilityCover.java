package ucw.tests.claimcenter;

import org.testng.annotations.*;
import ucw.pages.claimcenter.ClaimCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.enums.AccountType.Company;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;


public class ClaimMobilityCover extends BaseTest {
    private PolicyCenter policyCenter;
    private ClaimCenter claimCenter;

    @BeforeClass
    public void goToClaimCenter() {
        startTest("UCWAR-735: CC//Mobility Cover//Create new service subcategory 'Mobility Cover'");
        basePage.selectLabel(SPOTICAR)
                .selectManagementType(INSURED)
                .selectRandomCountry();
        policyCenter = basePage.loginPolicyCenter();
        claimCenter = basePage.loginClaimCenter();
    }

    @Test(description = "Claim mobility cover")
    public void test_mobilityCover() {
        basePage.switchToPolicyCenterTab();
        basePage.selectRandomCoverageType()
                .getDataObject().setCompanyName("PCCompany2023081505");
        basePage.setAccountTypeAs(Company);
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
        claimCenter.atClaimPage().addMobilityCoverService();
        claimCenter.atClaimPage().addQuote();
        assertTrue(claimCenter.atClaimPage().validateDocument(), "Quote document validation is failed");
    }
}
