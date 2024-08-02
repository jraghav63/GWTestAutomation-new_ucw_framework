package ucw.tests.billingcenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ucw.pages.billingcenter.BillingCenter;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-311
public class ChargeValidation extends BaseTest {
    private PolicyCenter policyCenter;
    private BillingCenter billingCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("Charge validation in Billing Center after policy change");
        basePage.selectLabel(SPOTICAR)
                .selectRandomCountry()
                .selectManagementType(INSURED);
        policyCenter = basePage.loginPolicyCenter();
        billingCenter = basePage.loginBillingCenter();

        basePage.switchToBillingCenterTab();
        billingCenter.navigateToAccountsPage();
        billingCenter.atAccountsPage().createAccount();
        assertTrue(billingCenter.atAccountsPage().isAccountCreated(), "Billing Center account creation is failed");

        basePage.switchToPolicyCenterTab();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
    }

    @BeforeMethod
    public void beforeMethod() {
        basePage.selectRandomCoverageType()
                .selectRandomCar();
        basePage.switchToPolicyCenterTab();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().createNewPolicy();
        assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
        policyCenter.searchPolicy();
        policyCenter.atPolicyPage().clickActionButton();
        assertTrue(policyCenter.atPolicyPage().isChangePolicyValidated(), "Change policy validation is failed");
    }

    @Test(description = "Charge validation after policy change as Prorogation")
    public void test_chargeValidationAfterChangeAsProrogation() {
        policyCenter.searchPolicy();
        policyCenter.atPolicyPage().clickActionButton();
        policyCenter.atPolicyPage().changePolicy();
        policyCenter.atPolicyPage().setChangeCost();

        basePage.switchToBillingCenterTab();
        billingCenter.navigateToAccountsPage();
        billingCenter.atAccountsPage().setChangeCost();
        double pcChangeCost = basePage.getDataObject().getPolicyCenter().getCostChange();
        double bcChangeCost = basePage.getDataObject().getBillingCenter().getCostChange();
        assertEquals(pcChangeCost, bcChangeCost, "Policy change cost is not match with billing change cost");
    }

    @Test(description = "Charge validation after policy change as change in vehicle data")
    public void test_chargeValidationAfterChangeInVehicleData() {
        policyCenter.searchPolicy();
        policyCenter.atPolicyPage().clickActionButton();
        policyCenter.atPolicyPage().changePolicyWithVehicleDataReason();
        policyCenter.atPolicyPage().setChangeCost();

        basePage.switchToBillingCenterTab();
        billingCenter.navigateToAccountsPage();
        billingCenter.atAccountsPage().setChangeCost();
        double pcChangeCost = basePage.getDataObject().getPolicyCenter().getCostChange();
        double bcChangeCost = basePage.getDataObject().getBillingCenter().getCostChange();
        assertEquals(pcChangeCost, bcChangeCost, "Policy change cost is not match with billing change cost");
    }
}
