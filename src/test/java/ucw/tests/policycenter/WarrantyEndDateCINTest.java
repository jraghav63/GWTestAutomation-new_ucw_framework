package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertNotEquals;
import static ucw.enums.Countries.Germany;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class WarrantyEndDateCINTest extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToPolicyCenter() {
        startTest("UCWAR-2987: CIN ==> The maker warranty end date is not filled by default automatically in Policy Center.");
        basePage.selectLabel(SPOTICAR)
                .selectManagementType(INSURED)
                .selectRandomCountryExcept(Germany);
        policyCenter = basePage.loginPolicyCenter();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
    }

    @Test(description = "Maker warranty end date should be filled by default automatically when the first registration date is sent by CIN")
    public void test_filledMakerWarrantyEndDate() {
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.goBackToAccSummary();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().navigateToPUWVehicleScreen();
        policyCenter.atPolicyPage().addVehicleBody();
        policyCenter.atPolicyPage().setLicensePlate("BE288AB");
        String actualMakerEndDate = policyCenter.atPolicyPage().getWarrantyEndDateFromField();
        assertNotEquals(actualMakerEndDate, "", "Actual maker warranty end date is not filled automatically");
    }
}
