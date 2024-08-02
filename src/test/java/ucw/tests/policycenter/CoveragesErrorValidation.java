package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class CoveragesErrorValidation extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToPolicyCenter() {
        startTest("Policy Center coverage error validation");
        basePage.selectRandomCountry()
                .selectLabel(SPOTICAR)
                .selectManagementType(INSURED);
        policyCenter = basePage.loginPolicyCenter();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
    }

    @Test(description = "Validate authorized weight error")
    public void test_authorizedWt() {
        basePage.selectRandomCoverageType();
        basePage.getDataObject().getPolicyCenter().setAuthorizedWeight(4);
        policyCenter.goBackToAccSummary();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
        policyCenter.atPolicyPage().addVehicleBody();
        policyCenter.atPolicyPage().setAuthorizedWeight();
        policyCenter.atPolicyPage().setFirstRegistrationDate(); // Fill other field to trigger the error message
        String actualMsg = policyCenter.atPolicyPage().getErrorMessageAtVehicleScreen();
        assertEquals(actualMsg, "Authorized loaded weight : Your vehicle is not eligible.");
    }

    @Test(description = "Validate first registration date error")
    public void test_firstRegDate() {
        basePage.selectRandomCoverageType()
                        .selectRegistrationDate(20);
        policyCenter.goBackToAccSummary();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
        policyCenter.atPolicyPage().addVehicleBody();
        policyCenter.atPolicyPage().setFirstRegistrationDate();
        String actualMsg = policyCenter.atPolicyPage().getErrorMessageAtVehicleScreen();
        assertEquals(actualMsg, "First Registration Date : Your vehicle is not eligible.");
    }

    @Test(description = "Validate mileage error")
    public void test_mileage() {
        basePage.selectRandomCoverageType()
                .selectMileageInRange(200001, 999999);
        policyCenter.goBackToAccSummary();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
        policyCenter.atPolicyPage().addVehicleBody();
        policyCenter.atPolicyPage().setMileage();
        String actualMsg = policyCenter.atPolicyPage().getErrorMessageAtVehicleScreen();
        assertEquals(actualMsg, "Mileage : Your vehicle is not eligible.");
    }
}
