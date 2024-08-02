package ucw.tests.claimcenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.claimcenter.ClaimCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.AccountType.Company;
import static ucw.enums.Countries.Italy;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-770
public class ClaimVATCalculationTest extends BaseTest {
    private ClaimCenter claimCenter;
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-770: Italy - VAT Configuration for Claim Center");
        basePage.selectLabel(SPOTICAR)
                .selectManagementType(INSURED)
                .selectCountry(Italy)
                .selectRandomCoverageType();
        policyCenter = basePage.loginPolicyCenter();
        claimCenter = basePage.loginClaimCenter();
    }

    @Test(description = "Claim VAT validation")
    public void test_claimVATValidation() {
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
        String actualVAT = claimCenter.atClaimPage().getVAT();
        assertEquals(actualVAT, "22,00€", "Claim VAT validation is failed");
    }
}
