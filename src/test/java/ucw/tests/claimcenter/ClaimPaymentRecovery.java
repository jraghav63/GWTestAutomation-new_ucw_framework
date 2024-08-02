package ucw.tests.claimcenter;

import org.testng.annotations.*;
import ucw.pages.claimcenter.ClaimCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.enums.AccountType.Company;
import static ucw.enums.Countries.Italy;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-771
public class ClaimPaymentRecovery extends BaseTest {
    private PolicyCenter policyCenter;
    private ClaimCenter claimCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-771: Italy - Claim Center country initialization");
        basePage.selectLabel(SPOTICAR)
                .selectManagementType(INSURED)
                .selectCountry(Italy);
        policyCenter = basePage.loginPolicyCenter();
        claimCenter = basePage.loginClaimCenter();
    }

    @Test(description = "Claim payment recovery")
    public void test_claimPaymentRecovery() {
        basePage.selectRandomCoverageType();
        basePage.getDataObject().setCompanyName("PCCompany2023081505");
        basePage.switchToPolicyCenterTab();
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

        claimCenter.atClaimPage().addQuote();
        claimCenter.atClaimPage().addInvoiceAndPay();

        claimCenter.navigateToInternalPage();
        claimCenter.atInternalSystemPage().runFinancialEscalationBatch();

        claimCenter.atSearchPage().searchClaim();
        assertTrue(claimCenter.atClaimPage().addRecoveryDetail(), "Recovery is not created successfully");
    }
}
