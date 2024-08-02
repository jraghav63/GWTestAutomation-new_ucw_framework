package ucw.tests.policycenter;

import org.testng.annotations.*;
import ucw.pages.billingcenter.BillingCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.Germany;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-149
public class LicensePlateGermany extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-149: GW Garantie-Germany: License Plate to be Non Mandatory");
        basePage.selectLabel(SPOTICAR)
                .selectManagementType(INSURED)
                .selectCountry(Germany);
        BillingCenter billingCenter = basePage.loginBillingCenter();
        billingCenter.navigateToAccountsPage();
        billingCenter.atAccountsPage().createAccount();
        assertTrue(billingCenter.atAccountsPage().isAccountCreated(), "Billing Center account creation is failed");
        policyCenter = basePage.loginPolicyCenter();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
    }

    @Test(description = "License plate validation in Germany while making new submission")
    public void test_licensePlate_addNewSubmission() {
        basePage.selectRandomCoverageType()
                .selectRandomCar()
                .getDataObject().getPolicyCenter().setLicensePlate(" ");

        policyCenter.goBackToAccSummary();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().addVehicleDetails();
        policyCenter.atPolicyPage().validateCoverageType();
        policyCenter.atPolicyPage().reviewPolicy();
        policyCenter.atPolicyPage().goToPayment();
        policyCenter.atPolicyPage().issuePolicy();
        assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Can't create policy without license plate in Germany");
    }

    @Test(description = "License plate validation in Germany while making a Policy Change Transaction")
    public void test_licensePlate_policyChange() {
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
        basePage.selectAnotherRandomEnergyType()
                .selectAnotherRandomCoverageType()
                .getDataObject().getPolicyCenter().setLicensePlate(" ");
        policyCenter.atPolicyPage().changePolicyAsVehicleDataCorrection();
        policyCenter.atPolicyPage().viewUpdatedPolicy();
        assertEquals(policyCenter.atPolicyPage().getTypeOfPolicyTransaction(), "Policy Change", "Cannot change policy with empty license plate");
    }
}
