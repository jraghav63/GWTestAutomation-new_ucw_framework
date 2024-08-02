package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertTrue;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

// UCWAR-245 and UCWAR-247
public class PolicyChangeTest extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("Policy change");
        basePage.selectLabel(SPOTICAR)
                .selectRandomCountry()
                .selectManagementType(INSURED);
        policyCenter = basePage.loginPolicyCenter();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
    }

    @BeforeMethod
    public void createPolicy() {
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
    }

    @Test(description = "Policy change reason field validation")
    public void test_reasonField() {
        policyCenter.atPolicyPage().clickActionButton();
        assertTrue(policyCenter.atPolicyPage().isChangePolicyValidated(), "Change policy validation is failed");
    }

    @Test(description = "Policy change with warranty expiration date validation")
    public void test_warrantyExpDateValidation() {
        policyCenter.atPolicyPage().clickActionButton();
        assertTrue(policyCenter.atPolicyPage().isWarrantyExpirationDateExtended(), "Change policy expiration date validation is failed");
    }

    @Test(description = "Policy change with PUW screen validation")
    public void test_PUWScreenValidation() {
        basePage.selectAnotherRandomEnergyType()
                .selectDifferentMileage(100000)
                .selectAnotherRandomCoverageType();
        policyCenter.atPolicyPage().changePolicyAsVehicleDataCorrection();
        assertTrue(policyCenter.atPolicyPage().isPUWScreenValidated(), "PUW screen validation is failed");
    }
}
