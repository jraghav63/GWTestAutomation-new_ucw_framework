package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.Germany;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class ExistingVINTest extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-1321: Prevent policy creation with existing VIN");
        basePage.selectLabel(SPOTICAR)
                .selectManagementType(INSURED);
        policyCenter = basePage.loginPolicyCenter();
    }

    @Test(description = "Policy with existing VIN validation")
    public void test_policyWithExistingVIN() {
        basePage.selectRandomCountry()
                .selectRandomCoverageType();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().createNewPolicy();
        assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
        policyCenter.goBackToAccSummary();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
        policyCenter.atPolicyPage().addVehicleBody();
        String existingVIN = basePage.getDataObject().getPolicyCenter().getVin();
        policyCenter.atPolicyPage().inputVin(existingVIN);
        String vinErrorMsg = policyCenter.atPolicyPage().getErrorMessageAtVehicleScreen();
        String expectedErrorMsg = "VIN : A warranty contract already exists for this vehicle (VIN: " + existingVIN + ").\n" +
                "If you wish to subscribe a new one, please contact info@garantiestellantis.fr with the VIN number and the License plate of the vehicle to request the cancellation of the existing contract.";
        assertEquals(vinErrorMsg, expectedErrorMsg, "Error message for existing VIN does not match with expected");
    }

    @Test(description = "Policy with existing LGG JV policy VIN validation")
    public void test_policyWithExistingLGGJVVIN() {
        basePage.selectCountry(Germany);
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
        policyCenter.goBackToAccSummary();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
        policyCenter.atPolicyPage().addVehicleBody();
        policyCenter.atPolicyPage().setLicensePlate();
        policyCenter.atPolicyPage().inputVin("VF1548U6GTWFU3330");
        String vinErrorMsg = policyCenter.atPolicyPage().getErrorMessageAtVehicleScreen();
        String expectedErrorMsg = "VIN : LGG-JV / Extended care Policy already exist for same VIN number";
        assertEquals(vinErrorMsg, expectedErrorMsg, "Error message for existing VIN does not match with expected");
    }
}
