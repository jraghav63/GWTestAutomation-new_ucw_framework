package ucw.tests.claimcenter;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.claimcenter.ClaimCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.AccountType.Company;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-1810
public class ClaimCancelServiceTest extends BaseTest {
    private PolicyCenter policyCenter;
    private ClaimCenter claimCenter;
    private static final String cancelStatus = "Canceled";

    @BeforeClass
    public void goToClaimCenter() {
        startTest("UCWAR-1810: Enable cancellation of service in all cases (for claim report purpose)");
        basePage.selectLabel(SPOTICAR)
                .selectManagementType(INSURED)
                .selectRandomCountry()
                .selectRandomCoverageType()
                .setAccountTypeAs(Company);
        policyCenter = basePage.loginPolicyCenter();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().createNewPolicy();
        assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");

        claimCenter = basePage.loginClaimCenter();
    }

    @AfterMethod
    public void returnToDesktop() {
        claimCenter.navigateToDesktopPage();
    }

    @Test(description = "Cancel Service after claim creation")
    public void test_cancelService_afterCreation() {
        claimCenter.navigateToNewClaimPage();
        claimCenter.atClaimPage().createClaim();
        assertTrue(claimCenter.atClaimPage().isClaimCreated(), "Claim creation is failed");
        claimCenter.atClaimPage().cancelService();
        assertEquals(claimCenter.atClaimPage().getProgress(), cancelStatus, "Current progress value is not Canceled");
    }

    @Test(description = "Cancel Service after pay invoice")
    public void test_cancelService_afterPayInvoice() {
        claimCenter.navigateToNewClaimPage();
        claimCenter.atClaimPage().createClaim();
        assertTrue(claimCenter.atClaimPage().isClaimCreated(), "Claim creation is failed");

        claimCenter.atClaimPage().addQuote();
        claimCenter.atClaimPage().addInvoiceAndPay();
        claimCenter.navigateToInternalPage();
        claimCenter.atInternalSystemPage().runFinancialEscalationBatch();
        claimCenter.atSearchPage().searchClaim();

        claimCenter.atClaimPage().cancelService();
        assertEquals(claimCenter.atClaimPage().getProgress(), cancelStatus, "Current progress value is not Canceled");
    }

    @Test(description = "Cancel service after adding quote")
    public void test_cancelService_afterAddQuote() {
        claimCenter.navigateToNewClaimPage();
        claimCenter.atClaimPage().createClaim();
        assertTrue(claimCenter.atClaimPage().isClaimCreated(), "Claim creation is failed");
        claimCenter.atClaimPage().addQuote();
        claimCenter.atClaimPage().cancelService();
        assertEquals(claimCenter.atClaimPage().getProgress(), cancelStatus, "Current progress value is not Canceled");
    }

    @Test(description = "Cancel Service with open invoice")
    public void test_cancelService_afterAddInvoice() {
        claimCenter.navigateToNewClaimPage();
        claimCenter.atClaimPage().createClaim();
        assertTrue(claimCenter.atClaimPage().isClaimCreated(), "Claim creation is failed");
        claimCenter.atClaimPage().addQuote();
        claimCenter.atClaimPage().addInvoiceAndPay();
        claimCenter.atClaimPage().cancelService();
        assertEquals(claimCenter.atClaimPage().getProgress(), cancelStatus, "Current progress value is not Canceled");
    }
}
