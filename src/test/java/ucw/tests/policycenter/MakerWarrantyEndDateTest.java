package ucw.tests.policycenter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ucw.enums.Countries;
import ucw.pages.policycenter.PolicyCenter;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ucw.enums.Countries.*;
import static ucw.enums.Labels.*;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;


public class MakerWarrantyEndDateTest extends BaseTest {
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-1617: PC - \"Maker warranty end date\" should be editable (day & month)");
        basePage.selectManagementType(INSURED);
        policyCenter = basePage.loginPolicyCenter();
    }

    @DataProvider
    public static Object[][] getCountries() {
        return new Object[][]{
                new Object[]{France,  SPOTICAR},
                new Object[]{France,  DSC     },
                new Object[]{France,  ARC     },
                new Object[]{Italy,   SPOTICAR},
                new Object[]{Italy,   DSC     },
                new Object[]{Italy,   LC      },
                new Object[]{Italy,   ARC     },
                new Object[]{Spain,   SPOTICAR},
                new Object[]{Spain,   ARC     },
                new Object[]{Spain,   DSC     },
                new Object[]{Germany, SPOTICAR}
        };
    }

    @Test(description = "Validating the Policy Center user is able to change Date and Month " +
            "of Maker Warranty End Date when submitting new policy",
        dataProvider = "getCountries")
    public void test_submitPolicy(Countries country, String label) {
        basePage.selectLabel(label)
                .selectCountry(country)
                .selectRandomCoverageType();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().addVehicleDetails();
        basePage.selectMakerWarrantyEndDate("01/01/2040");
        policyCenter.atPolicyPage().setWarrantyEndDate();
        policyCenter.atPolicyPage().validateCoverageType();
        policyCenter.atPolicyPage().reviewPolicy();
        policyCenter.atPolicyPage().goToPayment();
        policyCenter.atPolicyPage().issuePolicy();
        assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
    }

    @Test(description = "Validating the Policy Center user is able to change Date and Month " +
            "of Maker Warranty End Date when changing new policy",
            dataProvider = "getCountries")
    public void test_changePolicy(Countries country, String label) {
        basePage.selectLabel(label)
                .selectCountry(country)
                .selectRandomCoverageType();
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        policyCenter.navigateToAccountPage();
        policyCenter.atAccountPage().createNewAccount();
        policyCenter.atAccountPage().navigateToSubmissionPage();
        policyCenter.atAccountPage().selectGVO();
        policyCenter.atPolicyPage().createNewPolicy();
        assertTrue(policyCenter.atPolicyPage().isPolicyCreated(), "Policy Center account creation is failed");
        basePage.selectAnotherRandomEnergyType()
                .selectDifferentMileage(100000)
                .selectAnotherRandomCoverageType()
                .selectMakerWarrantyEndDate("01/01/2040");
        policyCenter.atPolicyPage().changePolicyAsVehicleDataCorrection();
        policyCenter.atPolicyPage().viewUpdatedPolicy();
        assertEquals(policyCenter.atPolicyPage().getTypeOfPolicyTransaction(), "Policy Change", "Cannot change policy with modified Maker warranty end date");
    }

    @Test(description = "Validating the Policy Center user is able to change Date and Month " +
            "of Maker Warranty End Date when changing Germany EGVO policy")
    public void test_changeEGVOPolicy() {
        String[] endDates = {"01/01/2040", "19/05/2042", "22/08/2041", "30/12/2039"};
        String endDate = endDates[rand.nextInt(endDates.length)];
        basePage.selectMakerWarrantyEndDate(endDate)
                .getDataObject().setPolicyNumber("0001017055");
        policyCenter.searchPolicy();
        policyCenter.atPolicyPage().clickActionButton();
        policyCenter.atPolicyPage().changeEGVOPolicyWithVehicleDataReason();
        policyCenter.atPolicyPage().viewUpdatedEGVOPolicy();
        assertEquals(policyCenter.atPolicyPage().getTypeOfPolicyTransaction(), "Policy Change", "Cannot change EGVO policy with Maker warranty end date");
        policyCenter.atPolicyPage().clickPUWVehiclesTab();
        String changedWarrantyEndDate = policyCenter.atPolicyPage().getMakerWarrantyEndDate();
        assertEquals(changedWarrantyEndDate, endDate.replace("/20", "/"), "Changed maker warranty end date isn't matched with expected");
    }
}
