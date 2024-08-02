package ucw.tests.portal;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import ucw.pages.policycenter.PolicyCenter;
import ucw.pages.portal.Portal;
import ucw.tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static ucw.enums.ClockTypes.FUTURE;
import static ucw.enums.ClockTypes.PRESENT;
import static ucw.enums.ContactType.Personal;
import static ucw.enums.Countries.*;
import static ucw.enums.CoverageTypes.*;
import static ucw.enums.Labels.SPOTICAR;
import static ucw.enums.ManagementType.INSURED;
import static ucw.utils.extentreports.ExtentTestManager.startTest;

public class ReplacementPercentageTest extends BaseTest {
    private Portal portal;
    private PolicyCenter policyCenter;

    @BeforeClass
    public void goToCenters() {
        startTest("UCWAR-2788: Germany_display_of_replacement_%_to_be_modified");
        basePage.selectLabel(SPOTICAR)
                .selectManagementType(INSURED)
                .selectCountry(Germany);
        policyCenter = basePage.loginPolicyCenter();
        policyCenter.atInternalSystemPage().changeClock(PRESENT);
        policyCenter.navigateToAdministrationPage();
        policyCenter.atAdministrationPage().setupDealer();
        portal = basePage.loginPortal();
        portal.navigateToSubmissionPage();
        portal.cancelSubmission();
        portal.atSubmissionPage().createNewContact(Personal);
        portal.atSubmissionPage().submitContactInfo();
        portal.atSubmissionPage().goToVehicleDetails();

    }

    @AfterClass
    public void revertSystemClock() {
        basePage.switchToPolicyCenterTab();
        policyCenter.atInternalSystemPage().changeClock(FUTURE);
    }

    @AfterMethod
    public void backToVehicles() {
        portal.atSubmissionPage().goBackToVehiclesScreen();
    }

    @DataProvider
    public Object[][] prepareCoverageTypes() {
        return new Object[][] {
                new Object[] {GWSpoticarPremium1_12M, 10000, 40000, 100},
                new Object[] {GWSpoticarPremium1_24M, 10000, 40000, 100},
                new Object[] {GWSpoticarPremium2_12M, 40001, 50000, 100},
                new Object[] {GWSpoticarPremium2_12M, 50001, 60000, 90},
                new Object[] {GWSpoticarPremium2_12M, 60001, 70000, 80},
                new Object[] {GWSpoticarPremium2_12M, 70001, 80000, 70},
                new Object[] {GWSpoticarPremium2_12M, 80001, 90000, 60},
                new Object[] {GWSpoticarPremium2_12M, 90001, 100000, 50},
                new Object[] {GWSpoticarPremium2_12M, 100001, 120000, 40},
                new Object[] {GWSpoticarPremium2_24M, 40001, 50000, 100},
                new Object[] {GWSpoticarPremium2_24M, 50001, 60000, 90},
                new Object[] {GWSpoticarPremium2_24M, 60001, 70000, 80},
                new Object[] {GWSpoticarPremium2_24M, 70001, 80000, 70},
                new Object[] {GWSpoticarPremium2_24M, 80001, 90000, 60},
                new Object[] {GWSpoticarPremium2_24M, 90001, 100000, 50},
                new Object[] {GWSpoticarPremium2_24M, 100001, 120000, 40},
                new Object[] {GWSpoticarAdvanced12M, 150001, 200000, 40},
                new Object[] {GWSpoticarAdvanced24M, 120001, 150000, 40},
        };
    }

    @Test(description = "Replacement % information", dataProvider = "prepareCoverageTypes", priority = 1)
    public void test_percentageReplacementMessage(String type, long minMileage, long maxMileage, int reimbursementPercent) {
        basePage.selectCoverageType(type)
                .selectMileageInRange(minMileage, maxMileage);
        portal.atSubmissionPage().inputVehicleMandatoryFields();
        portal.atSubmissionPage().submitVehicleInfo();
        String sparePartsLabel = portal.atSubmissionPage().getSparePartsLabel();
        int actualReimbursementPercent = portal.atSubmissionPage().getReimbursementPercentValue();
        assertEquals(sparePartsLabel, "Spare parts reimbursement % at the date of subscription (this % will evolve with km)",
                "Current spare part reimbursement label is not matched as expected");
        assertEquals(actualReimbursementPercent, reimbursementPercent, "Current spare part reimbursement % is not matched as expected");
    }
}
